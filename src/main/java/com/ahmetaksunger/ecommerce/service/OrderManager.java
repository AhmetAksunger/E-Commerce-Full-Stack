package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.response.OrderCompletedResponse;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartNotFoundException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.PaymentDetailNotFoundExcepition;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.*;
import com.ahmetaksunger.ecommerce.repository.CartItemRepository;
import com.ahmetaksunger.ecommerce.repository.CartRepository;
import com.ahmetaksunger.ecommerce.repository.OrderRepository;
import com.ahmetaksunger.ecommerce.repository.PaymentDetailRepository;
import com.ahmetaksunger.ecommerce.service.rules.CartRules;
import com.ahmetaksunger.ecommerce.service.rules.OrderRules;
import com.ahmetaksunger.ecommerce.service.rules.PaymentDetailRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderManager implements OrderService{

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PaymentDetailRepository paymentDetailRepository;
    private final OrderRules orderRules;
    private final MapperService mapperService;
    private final ProductService productService;

    @Override
    @Transactional
    public OrderCompletedResponse create(long cartId, long paymentDetailId, User loggedInUser) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        PaymentDetail paymentDetail = paymentDetailRepository.findById(paymentDetailId).orElseThrow(PaymentDetailNotFoundExcepition::new);

        //Rules
        orderRules.verifyCartAndPaymentDetailBelongsToUser(cart,paymentDetail,loggedInUser);
        orderRules.checkInsufficientStock(cart); // TODO: Optimistic - Pessimistic lock

        Order order = Order.builder()
                .total(this.calculateTotal(cart))
                .cart(cart)
                .customer((Customer) loggedInUser)
                .paymentDetail(paymentDetail)
                .createdAt(new Date())
                .build();

        Order dbOrder = orderRepository.save(order);

        //TODO: How to link different transactions
        productService.reduceQuantityForBoughtProducts(cart.getCartItems()
                .stream()
                .map(CartItem::getProduct)
                .toList());
        cartItemRepository.deleteAllByCartId(cartId);
        return mapperService.forResponse().map(dbOrder,OrderCompletedResponse.class);
    }

    private BigDecimal calculateTotal(Cart cart){
        BigDecimal total = cart.getCartItems()
                .stream()
                .map(cartItem -> cartItem.getProduct().getPrice())
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}

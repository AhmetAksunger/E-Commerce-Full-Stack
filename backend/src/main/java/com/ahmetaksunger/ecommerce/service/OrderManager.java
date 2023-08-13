package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.response.OrderCompletedResponse;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartNotFoundException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.PaymentDetailNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.*;
import com.ahmetaksunger.ecommerce.repository.*;
import com.ahmetaksunger.ecommerce.service.rules.OrderRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private final SellerRepository sellerRepository;

    @Override
    @Transactional
    public OrderCompletedResponse create(long cartId, long paymentDetailId, User loggedInUser) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        PaymentDetail paymentDetail = paymentDetailRepository.findById(paymentDetailId).orElseThrow(PaymentDetailNotFoundException::new);

        //Rules
        orderRules.verifyCartAndPaymentDetailBelongsToUser(cart,paymentDetail,loggedInUser);
        orderRules.checkInsufficientStock(cart); // TODO: Optimistic - Pessimistic lock

        Order order = Order.builder()
                .total(PriceCalculator.calculateTotal(cart))
                .cart(cart)
                .customer((Customer) loggedInUser)
                .paymentDetail(paymentDetail)
                .createdAt(new Date())
                .build();

        Order dbOrder = orderRepository.save(order);

        // Reducing quantities by 1, for the bought products
        productService.reduceQuantityForBoughtProducts(cart.getCartItems()
                .stream()
                .map(CartItem::getProduct)
                .toList());
        // Resetting the customer cart
        cartItemRepository.deleteAllByCartId(cartId);

        // Incrementing the sellers total revenue
        HashMap<Long,BigDecimal> sellerTotalRevenue = this.calculateRevenuesForSellers(cart);
        sellerTotalRevenue.forEach(
                (sellerId,revenue) -> {
                    Seller seller = sellerRepository.findById(sellerId).orElseThrow();
                    seller.setTotalRevenue(revenue);
                    sellerRepository.save(seller);
                }
        );

        return mapperService.forResponse().map(dbOrder,OrderCompletedResponse.class);
    }

    private HashMap<Long,BigDecimal> calculateRevenuesForSellers(Cart cart){

        HashMap<Long,BigDecimal> sellerIdTotalRevenueMap = new HashMap<>();

        List<Product> boughtProducts = cart.getCartItems()
                .stream()
                .map(CartItem::getProduct)
                .toList();

        boughtProducts.forEach(product -> {
            long sellerId = product.getSeller().getId();
            BigDecimal productPrice = product.getPrice();

            if(sellerIdTotalRevenueMap.containsKey(sellerId)){
                sellerIdTotalRevenueMap.put(sellerId,
                        sellerIdTotalRevenueMap.get(sellerId).add(productPrice));
            }else{
                sellerIdTotalRevenueMap.put(sellerId,productPrice);
            }
        });
        return sellerIdTotalRevenueMap;
    }

}

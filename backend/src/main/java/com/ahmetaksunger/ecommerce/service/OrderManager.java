package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.order.CreateOrderRequest;
import com.ahmetaksunger.ecommerce.dto.response.OrderCompletedResponse;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.AddressNotFoundException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartNotFoundException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.PaymentDetailNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.*;
import com.ahmetaksunger.ecommerce.repository.*;
import com.ahmetaksunger.ecommerce.service.rules.AddressRules;
import com.ahmetaksunger.ecommerce.service.rules.OrderRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderManager implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PaymentDetailRepository paymentDetailRepository;
    private final OrderRules orderRules;
    private final MapperService mapperService;
    private final ProductService productService;
    private final SellerRepository sellerRepository;
    private final AddressRules addressRules;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public OrderCompletedResponse create(CreateOrderRequest createOrderRequest, User loggedInUser) {

        Cart cart = cartRepository.findById(createOrderRequest.getCartId()).orElseThrow(CartNotFoundException::new);
        PaymentDetail paymentDetail = paymentDetailRepository.findById(createOrderRequest.getPaymentDetailId()).orElseThrow(PaymentDetailNotFoundException::new);
        Address address = addressRepository.findById(createOrderRequest.getAddressId()).orElseThrow(AddressNotFoundException::new);

        //Rules
        orderRules.verifyCartAndPaymentDetailBelongsToUser(cart, paymentDetail, loggedInUser);
        orderRules.checkInsufficientStock(cart); // TODO: Optimistic - Pessimistic lock
        addressRules.verifyAddressBelongsToUser(address,loggedInUser, UnauthorizedException.class);

        Order order = Order.builder()
                .total(PriceCalculator.calculateTotal(cart))
                .cart(cart)
                .customer((Customer) loggedInUser)
                .paymentDetail(paymentDetail)
                .address(address)
                .build();

        Order dbOrder = orderRepository.save(order);

        // Reducing quantities by 1, for the bought products
        productService.reduceQuantityForBoughtProducts(cart.getCartItems()
                .stream()
                .map(CartItem::getProduct)
                .toList());
        // Resetting the customer cart
        cartItemRepository.deleteAllByCartId(createOrderRequest.getCartId());

        // Incrementing the seller's total revenue
        HashMap<Long, BigDecimal> sellerTotalRevenue = this.calculateRevenuesForSellers(cart);
        sellerTotalRevenue.forEach(
                (sellerId, revenue) -> {
                    Seller seller = sellerRepository.findById(sellerId).orElseThrow();
                    seller.setTotalRevenue(revenue);
                    sellerRepository.save(seller);
                }
        );

        return mapperService.forResponse().map(dbOrder, OrderCompletedResponse.class);
    }

    /**
     * <p>Takes a cart by a parameter and
     * creates a Product - Quantity map from the cart</p>
     * <p>Then creates a Seller Id - Revenue map for the cart,
     * to see which seller made how much revenue from this order</p>
     *
     * @param cart {@link Cart}
     * @return {@link HashMap<Long,BigDecimal>} Seller Id - Revenue map
     */
    private HashMap<Long, BigDecimal> calculateRevenuesForSellers(Cart cart) {

        HashMap<Long, BigDecimal> sellerIdRevenueMap = new HashMap<>();

        Map<Product, Integer> boughtProductsAndProductQuantities = cart.getCartItems()
                .stream()
                .collect(Collectors.toMap(CartItem::getProduct, CartItem::getQuantity));

        boughtProductsAndProductQuantities.forEach(
                (product, quantity) -> {
                    long sellerId = product.getSeller().getId();
                    BigDecimal totalProductPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));

                    sellerIdRevenueMap.compute(sellerId,
                            (key, value) -> (value == null ? totalProductPrice : value.add(totalProductPrice)));

                });
        return sellerIdRevenueMap;
    }

}

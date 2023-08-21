package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.order.CreateOrderRequest;
import com.ahmetaksunger.ecommerce.dto.response.OrderCompletedResponse;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.EntityOwnershipException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.AddressNotFoundException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartNotFoundException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.PaymentDetailNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.*;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentStatus;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentTransaction;
import com.ahmetaksunger.ecommerce.model.transaction.TransactionType;
import com.ahmetaksunger.ecommerce.repository.*;
import com.ahmetaksunger.ecommerce.service.factory.PaymentTransactionFactory;
import com.ahmetaksunger.ecommerce.service.rules.AddressRules;
import com.ahmetaksunger.ecommerce.service.rules.CartRules;
import com.ahmetaksunger.ecommerce.service.rules.OrderRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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
    private final CartService cartService;
    private final CartRules cartRules;
    private final PaymentTransactionRepository paymentTransactionRepository;


    /**
     * <p> - Verifies that the specified cart, payment detail, and address belong to the customer.</p>
     * <p> - Checks if the products in the cart still have enough stocks</p>
     * <p> - Checks if the cart is empty</p>
     *
     * <p>If all the rules pass:</p>
     * <p>1-) It creates an order and saves it to the database</p>
     * <p>2-) It reduces the bought products' quantities</p>
     * <p>3-) It increments the sellers' total revenues</p>
     * <p>4-) It deactivates the customer's used cart</p>
     * <p>5-) It creates a new cart for the customer</p>
     * <p>Then returns {@link OrderCompletedResponse}</p>
     *
     * @param createOrderRequest {@link CreateOrderRequest}
     * @param loggedInUser       {@link Customer}
     * @return {@link OrderCompletedResponse}
     * @see OrderRules
     * @see AddressRules
     * @see CartService
     * @see ProductService#reduceQuantityForPurchasedProducts(Cart)
     */
    @Override
    @Transactional(noRollbackFor = EntityOwnershipException.class)
    public OrderCompletedResponse create(CreateOrderRequest createOrderRequest, User loggedInUser) {

        final Customer customer = (Customer) loggedInUser;

        final Cart cart = cartRepository.findById(createOrderRequest.getCartId()).orElseThrow(CartNotFoundException::new);
        final PaymentDetail paymentDetail = paymentDetailRepository.findById(createOrderRequest.getPaymentDetailId()).orElseThrow(PaymentDetailNotFoundException::new);
        final Address address = addressRepository.findById(createOrderRequest.getAddressId()).orElseThrow(AddressNotFoundException::new);

        final BigDecimal total = PriceCalculator.calculateTotal(cart);

        //Rules
        try {
            orderRules.verifyCartAndPaymentDetailBelongsToUser(cart, paymentDetail, customer);
            addressRules.verifyAddressBelongsToUser(address, customer, EntityOwnershipException.class);
        } catch (EntityOwnershipException exception) {
            var transaction = PaymentTransactionFactory.create(TransactionType.PURCHASE, PaymentStatus.FAILED,
                    customer, total, paymentDetail, exception.getMessage());
            paymentTransactionRepository.save(transaction);

            throw exception;
        }
        orderRules.checkInsufficientStock(cart); // TODO: Optimistic - Pessimistic lock
        orderRules.checkIfCartIsEmpty(cart);
        cartRules.checkIfCartActive(cart);

        final Order order = Order.builder()
                .total(total)
                .cart(cart)
                .customer(customer)
                .paymentDetail(paymentDetail)
                .address(address)
                .build();

        final Order dbOrder = orderRepository.save(order);

        // Creating a payment transaction for the bought products
        var transaction = PaymentTransactionFactory.create(TransactionType.PURCHASE,PaymentStatus.COMPLETED,
                customer,total,paymentDetail,null);

        paymentTransactionRepository.save(transaction);

        // Reducing quantities by purchased quantities, for the bought products
        productService.reduceQuantityForPurchasedProducts(cart);

        // Incrementing the seller's total revenue
        HashMap<Long, BigDecimal> sellerTotalRevenue = this.calculateRevenuesForSellers(cart);
        sellerTotalRevenue.forEach(
                (sellerId, revenue) -> {
                    Seller seller = sellerRepository.findById(sellerId).orElseThrow();
                    seller.setTotalRevenue(seller.getTotalRevenue().add(revenue));
                    sellerRepository.save(seller);
                }
        );

        // Deactivating the customer's current cart
        cartService.deactivateCart(cart);

        // Creating a new cart for the customer
        final Cart newCart = cartService.create(customer);

        var response = mapperService.forResponse().map(dbOrder, OrderCompletedResponse.class);
        response.setNewCartId(newCart.getId());
        return response;
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

        cart.getCartItems().forEach(cartItem -> {
            var product = cartItem.getProduct();
            Long sellerId = product.getSeller().getId();
            BigDecimal totalProductPrice = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            sellerIdRevenueMap.compute(sellerId,
                    (key, value) -> (value == null ? totalProductPrice : value.add(totalProductPrice)));
        });
        return sellerIdRevenueMap;
    }
}
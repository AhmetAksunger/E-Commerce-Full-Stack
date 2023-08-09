package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.InsufficientProductQuantityException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderRules {

    private final PaymentDetailRules paymentDetailRules;
    private final CartRules cartRules;
    public void verifyCartAndPaymentDetailBelongsToUser(Cart cart, PaymentDetail paymentDetail, User user){

        cartRules.verifyCartBelongsToUser(cart,user, UnauthorizedException.class);
        paymentDetailRules.verifyPaymentDetailBelongsToUser(paymentDetail,user, UnauthorizedException.class);

    }

    public void checkInsufficientStock(Cart cart) {

        List<Product> productsWithInsufficientStock  = cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getQuantity() > cartItem.getProduct().getQuantity())
                .map(CartItem::getProduct)
                .toList();

        if(productsWithInsufficientStock .size() > 0){
            throw new InsufficientProductQuantityException(productsWithInsufficientStock);
        }
    }
}

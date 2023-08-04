package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.model.Cart;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceCalculator {

    public static BigDecimal calculateTotal(Cart cart){
        BigDecimal total = cart.getCartItems()
                .stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}

package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.CartItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartCalculator {

    public static BigDecimal calculateTotal(Cart cart){
        BigDecimal total = cart.getCartItems()
                .stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateTotalForCartItem(CartItem cartItem){
        return cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }

    /**
     * Calculates the total product count, for the given cart
     *
     * @param cart Cart
     * @return Total product count
     */
    public static Integer calculateTotalProductCount(Cart cart) {
        return cart.getCartItems()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}

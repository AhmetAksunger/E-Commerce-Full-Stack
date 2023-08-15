package com.ahmetaksunger.ecommerce.exception;

public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException() {
        super(ExceptionMessages.CART_IS_EMPTY.message());
    }
}

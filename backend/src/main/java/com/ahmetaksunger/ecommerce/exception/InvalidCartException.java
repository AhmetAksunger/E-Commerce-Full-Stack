package com.ahmetaksunger.ecommerce.exception;

public class InvalidCartException extends RuntimeException {
    public InvalidCartException() {
        super(ExceptionMessages.INVALID_CART.message());
    }
}

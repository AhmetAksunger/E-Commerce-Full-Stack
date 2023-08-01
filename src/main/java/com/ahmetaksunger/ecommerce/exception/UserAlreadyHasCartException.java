package com.ahmetaksunger.ecommerce.exception;

public class UserAlreadyHasCartException extends RuntimeException {
    public UserAlreadyHasCartException() {
        super(ExceptionMessages.USER_ALREADY_HAS_CART.message());
    }
}

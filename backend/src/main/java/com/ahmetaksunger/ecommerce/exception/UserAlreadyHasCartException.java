package com.ahmetaksunger.ecommerce.exception;

import java.io.Serial;

public class UserAlreadyHasCartException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4943167071458856201L;

    public UserAlreadyHasCartException() {
        super(ExceptionMessages.USER_ALREADY_HAS_CART.message());
    }
}

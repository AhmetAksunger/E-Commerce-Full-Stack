package com.ahmetaksunger.ecommerce.exception;

import java.io.Serial;

public class InvalidCartException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 8640254490369362181L;

    public InvalidCartException() {
        super(ExceptionMessages.INVALID_CART.message());
    }
}

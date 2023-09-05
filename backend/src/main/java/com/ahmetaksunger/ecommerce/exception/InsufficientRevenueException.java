package com.ahmetaksunger.ecommerce.exception;

import java.io.Serial;

public class InsufficientRevenueException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7562500637435585023L;

    public InsufficientRevenueException() {
        super(ExceptionMessages.INSUFF_REVENUE.message());
    }
}

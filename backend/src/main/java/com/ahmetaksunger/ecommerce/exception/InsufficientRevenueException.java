package com.ahmetaksunger.ecommerce.exception;

public class InsufficientRevenueException extends RuntimeException {
    public InsufficientRevenueException() {
        super(ExceptionMessages.INSUFF_REVENUE.message());
    }
}

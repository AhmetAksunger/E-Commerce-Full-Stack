package com.ahmetaksunger.ecommerce.exception;

public class InsufficientProductQuantityException extends RuntimeException {
    public InsufficientProductQuantityException() {
        super(ExceptionMessages.INSUFF_PRODUCT_QUANTITY.message());
    }
}

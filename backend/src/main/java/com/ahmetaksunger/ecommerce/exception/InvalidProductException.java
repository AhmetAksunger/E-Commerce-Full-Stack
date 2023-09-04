package com.ahmetaksunger.ecommerce.exception;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException(){
        super(ExceptionMessages.INVALID_PRODUCT.message());
    }
}

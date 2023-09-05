package com.ahmetaksunger.ecommerce.exception;

import java.io.Serial;

public class InvalidProductException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -9160794375133461503L;

    public InvalidProductException(){
        super(ExceptionMessages.INVALID_PRODUCT.message());
    }
}

package com.ahmetaksunger.ecommerce.exception.notfound;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class ProductNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = -3511510390713800253L;

    public ProductNotFoundException() {
        super(ExceptionMessages.PRODUCT_NOT_FOUND.message());
    }
}

package com.ahmetaksunger.ecommerce.exception.NotFoundException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException() {
        super(ExceptionMessages.PRODUCT_NOT_FOUND.message());
    }
}

package com.ahmetaksunger.ecommerce.exception.notfound;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException() {
        super(ExceptionMessages.PRODUCT_NOT_FOUND.message());
    }
}

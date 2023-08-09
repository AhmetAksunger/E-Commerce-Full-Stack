package com.ahmetaksunger.ecommerce.exception.NotAllowedException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class ProductUpdateNotAllowedException extends UnauthorizedException{
    public ProductUpdateNotAllowedException() {
        super(ExceptionMessages.PRODUCT_UPDATE_NOT_ALLOWED.message());
    }
}

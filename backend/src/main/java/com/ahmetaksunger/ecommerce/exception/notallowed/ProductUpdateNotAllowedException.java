package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class ProductUpdateNotAllowedException extends UnauthorizedException{
    @Serial
    private static final long serialVersionUID = -9007903504475165969L;

    public ProductUpdateNotAllowedException() {
        super(ExceptionMessages.PRODUCT_UPDATE_NOT_ALLOWED.message());
    }
}

package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class ProductDeletionNotAllowedException extends UnauthorizedException{
    @Serial
    private static final long serialVersionUID = 3057251115727527748L;

    public ProductDeletionNotAllowedException() {
        super(ExceptionMessages.PRODUCT_DELETION_NOT_ALLOWED.message());
    }
}

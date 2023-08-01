package com.ahmetaksunger.ecommerce.exception.NotAllowedException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class ProductDeletionNotAllowedException extends UnauthorizedException{
    public ProductDeletionNotAllowedException() {
        super(ExceptionMessages.PRODUCT_DELETION_NOT_ALLOWED.message());
    }
}

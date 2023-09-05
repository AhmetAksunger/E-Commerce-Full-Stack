package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class CartDeletionNotAllowedException extends UnauthorizedException {
    public CartDeletionNotAllowedException() {
        super(ExceptionMessages.CART_DELETION_NOT_ALLOWED.message());
    }
}

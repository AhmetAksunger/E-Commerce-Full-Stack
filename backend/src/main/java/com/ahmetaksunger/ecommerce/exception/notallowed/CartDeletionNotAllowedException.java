package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class CartDeletionNotAllowedException extends UnauthorizedException {
    @Serial
    private static final long serialVersionUID = 939628297850253374L;

    public CartDeletionNotAllowedException() {
        super(ExceptionMessages.CART_DELETION_NOT_ALLOWED.message());
    }
}

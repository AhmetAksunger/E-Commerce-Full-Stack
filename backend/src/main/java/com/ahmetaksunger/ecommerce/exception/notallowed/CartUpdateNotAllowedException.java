package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class CartUpdateNotAllowedException extends UnauthorizedException {
    @Serial
    private static final long serialVersionUID = 7561738624716980265L;

    public CartUpdateNotAllowedException(){
        super(ExceptionMessages.CART_UPDATE_NOT_ALLOWED.message());
    }
}

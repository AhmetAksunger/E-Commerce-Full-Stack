package com.ahmetaksunger.ecommerce.exception.NotAllowedException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class CartUpdateNotAllowedException extends UnauthorizedException {
    public CartUpdateNotAllowedException(){
        super(ExceptionMessages.CART_UPDATE_NOT_ALLOWED.message());
    }
}

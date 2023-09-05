package com.ahmetaksunger.ecommerce.exception.notfound;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class CartNotFoundException extends NotFoundException{
    public CartNotFoundException() {
        super(ExceptionMessages.CART_NOT_FOUND.message());
    }
}

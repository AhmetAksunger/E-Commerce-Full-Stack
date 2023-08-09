package com.ahmetaksunger.ecommerce.exception.NotFoundException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class CartNotFoundException extends NotFoundException{
    public CartNotFoundException() {
        super(ExceptionMessages.CART_NOT_FOUND.message());
    }
}

package com.ahmetaksunger.ecommerce.exception.notfound;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class CartNotFoundException extends NotFoundException{
    @Serial
    private static final long serialVersionUID = 8380903406809945752L;

    public CartNotFoundException() {
        super(ExceptionMessages.CART_NOT_FOUND.message());
    }
}

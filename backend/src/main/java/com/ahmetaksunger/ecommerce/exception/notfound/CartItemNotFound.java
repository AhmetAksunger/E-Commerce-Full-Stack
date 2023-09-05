package com.ahmetaksunger.ecommerce.exception.notfound;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class CartItemNotFound extends NotFoundException{

    @Serial
    private static final long serialVersionUID = 537993453865744330L;

    public CartItemNotFound() {
        super(ExceptionMessages.CART_ITEM_NOT_FOUND.message());
    }
}

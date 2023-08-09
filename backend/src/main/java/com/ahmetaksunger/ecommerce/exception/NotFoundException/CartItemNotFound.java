package com.ahmetaksunger.ecommerce.exception.NotFoundException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class CartItemNotFound extends NotFoundException{

    public CartItemNotFound() {
        super(ExceptionMessages.CART_ITEM_NOT_FOUND.message());
    }
}

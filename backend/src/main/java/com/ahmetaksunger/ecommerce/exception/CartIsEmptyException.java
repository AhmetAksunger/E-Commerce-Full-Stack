package com.ahmetaksunger.ecommerce.exception;

import java.io.Serial;

public class CartIsEmptyException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2468793598171098233L;

    public CartIsEmptyException() {
        super(ExceptionMessages.CART_IS_EMPTY.message());
    }
}

package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class AddressDeletionNotAllowedException extends UnauthorizedException {
    @Serial
    private static final long serialVersionUID = -6350080169236447241L;

    public AddressDeletionNotAllowedException() {
        super(ExceptionMessages.ADDRESS_DELETION_NOT_ALLOWED.message());
    }
}

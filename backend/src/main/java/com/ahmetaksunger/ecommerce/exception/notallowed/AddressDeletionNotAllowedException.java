package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class AddressDeletionNotAllowedException extends UnauthorizedException {
    public AddressDeletionNotAllowedException() {
        super(ExceptionMessages.ADDRESS_DELETION_NOT_ALLOWED.message());
    }
}

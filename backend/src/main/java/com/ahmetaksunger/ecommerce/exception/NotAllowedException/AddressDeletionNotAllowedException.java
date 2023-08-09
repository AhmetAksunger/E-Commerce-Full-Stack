package com.ahmetaksunger.ecommerce.exception.NotAllowedException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;

public class AddressDeletionNotAllowedException extends UnauthorizedException {
    public AddressDeletionNotAllowedException() {
        super(ExceptionMessages.ADDRESS_DELETION_NOT_ALLOWED.message());
    }
}

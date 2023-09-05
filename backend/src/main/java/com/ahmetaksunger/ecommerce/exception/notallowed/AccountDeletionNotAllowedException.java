package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class AccountDeletionNotAllowedException extends UnauthorizedException {
    @Serial
    private static final long serialVersionUID = 4306244325157797005L;

    public AccountDeletionNotAllowedException(){
        super(ExceptionMessages.ACCOUNT_DELETION_NOT_ALLOWED.message());
    }
}

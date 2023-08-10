package com.ahmetaksunger.ecommerce.exception.NotAllowedException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class AccountDeletionNotAllowedException extends UnauthorizedException {
    public AccountDeletionNotAllowedException(){
        super(ExceptionMessages.ACCOUNT_DELETION_NOT_ALLOWED.message());
    }
}
package com.ahmetaksunger.ecommerce.exception;

public class AccountDeletionNotAllowedException extends UnauthorizedException {
    public AccountDeletionNotAllowedException(){
        super(ExceptionMessages.ACCOUNT_DELETION_NOT_ALLOWED.message());
    }
}

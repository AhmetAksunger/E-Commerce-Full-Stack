package com.ahmetaksunger.ecommerce.exception;

public class AccountDeletionNotAllowedException extends RuntimeException {
    public AccountDeletionNotAllowedException(){
        super(ExceptionMessages.ACCOUNT_DELETION_NOT_ALLOWED.getMessage());
    }
}

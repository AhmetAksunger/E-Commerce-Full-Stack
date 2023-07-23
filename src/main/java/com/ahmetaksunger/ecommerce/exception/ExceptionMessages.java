package com.ahmetaksunger.ecommerce.exception;

public enum ExceptionMessages {

    ACCOUNT_DELETION_NOT_ALLOWED("User is not allowed to delete this account.");

    private String message;

    public String getMessage(){
        return this.message;
    }
    ExceptionMessages(String message) {
        this.message= message;
    }
}

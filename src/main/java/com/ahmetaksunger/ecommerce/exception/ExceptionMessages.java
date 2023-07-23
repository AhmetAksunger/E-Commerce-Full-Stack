package com.ahmetaksunger.ecommerce.exception;

public enum ExceptionMessages {

    ACCOUNT_DELETION_NOT_ALLOWED("User is not allowed to delete this account."),
    UNAUTHORIZED("Unauthorized");

    private String message;

    public String message(){
        return this.message;
    }
    ExceptionMessages(String message) {
        this.message= message;
    }
}

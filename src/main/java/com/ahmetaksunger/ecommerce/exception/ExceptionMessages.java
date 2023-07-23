package com.ahmetaksunger.ecommerce.exception;

public enum ExceptionMessages {
    EMAIL_ALREADY_EXISTS("Email already exists"),
    ;

    private String message;

    public String getMessage(){
        return this.message;
    }
    ExceptionMessages(String message) {
        this.message= message;
    }
}

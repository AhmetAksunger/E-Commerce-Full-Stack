package com.ahmetaksunger.ecommerce.exception;

public class InvalidRequestParamException extends RuntimeException{
    public InvalidRequestParamException(String message){
        super(message);
    }
}

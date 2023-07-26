package com.ahmetaksunger.ecommerce.exception.NotAllowedException;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message){
        super(message);
    }
}

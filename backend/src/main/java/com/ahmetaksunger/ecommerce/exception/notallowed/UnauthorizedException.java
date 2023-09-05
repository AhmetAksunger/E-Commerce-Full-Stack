package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(){
        super(ExceptionMessages.UNAUTHORIZED.message());
    }
    public UnauthorizedException(String message){
        super(message);
    }
}

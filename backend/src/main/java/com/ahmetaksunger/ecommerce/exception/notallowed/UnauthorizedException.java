package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class UnauthorizedException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -1526819906823398532L;

    public UnauthorizedException(){
        super(ExceptionMessages.UNAUTHORIZED.message());
    }
    public UnauthorizedException(String message){
        super(message);
    }
}

package com.ahmetaksunger.ecommerce.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidRequestParamException extends RuntimeException{

    private final List<String> validParams;

    public InvalidRequestParamException(String message, List<String> validParams){
        super(message);
        this.validParams = validParams;
    }
}

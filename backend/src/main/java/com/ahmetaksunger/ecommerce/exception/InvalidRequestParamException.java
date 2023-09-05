package com.ahmetaksunger.ecommerce.exception;

import lombok.Getter;

import java.io.Serial;
import java.util.List;

@Getter
public class InvalidRequestParamException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 3819783693459613582L;
    private final List<String> validParams;

    public InvalidRequestParamException(String message, List<String> validParams){
        super(message);
        this.validParams = validParams;
    }
}

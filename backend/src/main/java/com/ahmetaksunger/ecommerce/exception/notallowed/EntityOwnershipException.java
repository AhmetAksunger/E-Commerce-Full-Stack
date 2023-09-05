package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class EntityOwnershipException extends UnauthorizedException{
    public EntityOwnershipException(){
        super(ExceptionMessages.UNAUTHORIZED.message());
    }
}

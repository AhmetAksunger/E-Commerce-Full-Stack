package com.ahmetaksunger.ecommerce.exception.NotAllowedException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class EntityOwnershipException extends UnauthorizedException{
    public EntityOwnershipException(){
        super(ExceptionMessages.UNAUTHORIZED.message());
    }
}

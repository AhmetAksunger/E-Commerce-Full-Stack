package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class EntityOwnershipException extends UnauthorizedException{
    @Serial
    private static final long serialVersionUID = -1659131930752940954L;

    public EntityOwnershipException(){
        super(ExceptionMessages.UNAUTHORIZED.message());
    }
}

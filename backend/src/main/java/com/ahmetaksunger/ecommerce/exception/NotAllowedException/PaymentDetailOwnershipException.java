package com.ahmetaksunger.ecommerce.exception.NotAllowedException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class PaymentDetailOwnershipException extends UnauthorizedException{
    public PaymentDetailOwnershipException(){
        super(ExceptionMessages.UNAUTHORIZED.message());
    }
}

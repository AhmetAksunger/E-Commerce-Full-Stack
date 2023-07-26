package com.ahmetaksunger.ecommerce.exception.NotAllowedException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;

public class PaymentDetailDeletionNotAllowedException extends UnauthorizedException {
    public PaymentDetailDeletionNotAllowedException() {
        super(ExceptionMessages.PAYMENT_DETAIL_DELETION_NOT_ALLOWED.message());
    }
}

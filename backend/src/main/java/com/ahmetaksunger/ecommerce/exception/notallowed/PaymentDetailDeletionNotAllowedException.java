package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class PaymentDetailDeletionNotAllowedException extends UnauthorizedException {
    public PaymentDetailDeletionNotAllowedException() {
        super(ExceptionMessages.PAYMENT_DETAIL_DELETION_NOT_ALLOWED.message());
    }
}

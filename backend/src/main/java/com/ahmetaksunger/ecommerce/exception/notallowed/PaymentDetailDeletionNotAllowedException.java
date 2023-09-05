package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class PaymentDetailDeletionNotAllowedException extends UnauthorizedException {
    @Serial
    private static final long serialVersionUID = 3857929649130482490L;

    public PaymentDetailDeletionNotAllowedException() {
        super(ExceptionMessages.PAYMENT_DETAIL_DELETION_NOT_ALLOWED.message());
    }
}

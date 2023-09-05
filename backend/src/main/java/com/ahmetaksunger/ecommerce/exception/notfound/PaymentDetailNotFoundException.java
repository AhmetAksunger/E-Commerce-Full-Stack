package com.ahmetaksunger.ecommerce.exception.notfound;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class PaymentDetailNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = 3794466465758948337L;

    public PaymentDetailNotFoundException() {
        super(ExceptionMessages.PAYMENT_DETAIL_NOT_FOUND.message());
    }
}

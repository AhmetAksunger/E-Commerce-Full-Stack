package com.ahmetaksunger.ecommerce.exception.notfound;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class PaymentDetailNotFoundException extends NotFoundException {
    public PaymentDetailNotFoundException() {
        super(ExceptionMessages.PAYMENT_DETAIL_NOT_FOUND.message());
    }
}

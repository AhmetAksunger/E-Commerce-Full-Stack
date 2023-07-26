package com.ahmetaksunger.ecommerce.exception;

import com.ahmetaksunger.ecommerce.exception.NotFoundException.NotFoundException;

public class PaymentDetailNotFoundExcepition extends NotFoundException {
    public PaymentDetailNotFoundExcepition() {
        super(ExceptionMessages.PAYMENT_DETAIL_NOT_FOUND.message());
    }
}

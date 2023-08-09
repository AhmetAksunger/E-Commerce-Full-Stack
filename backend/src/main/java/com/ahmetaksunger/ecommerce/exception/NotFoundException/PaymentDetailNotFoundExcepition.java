package com.ahmetaksunger.ecommerce.exception.NotFoundException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.NotFoundException;

public class PaymentDetailNotFoundExcepition extends NotFoundException {
    public PaymentDetailNotFoundExcepition() {
        super(ExceptionMessages.PAYMENT_DETAIL_NOT_FOUND.message());
    }
}

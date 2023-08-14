package com.ahmetaksunger.ecommerce.exception;

import java.math.BigDecimal;

public class InvalidWithdrawAmountException extends RuntimeException {

    public InvalidWithdrawAmountException(BigDecimal minimumValue) {
        super(ExceptionMessages.INVALID_WITHDRAW_AMOUNT.message() + " Min: " + minimumValue);
    }
}

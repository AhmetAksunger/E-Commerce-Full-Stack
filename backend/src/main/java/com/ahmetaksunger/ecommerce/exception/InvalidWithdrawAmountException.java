package com.ahmetaksunger.ecommerce.exception;

import java.io.Serial;
import java.math.BigDecimal;

public class InvalidWithdrawAmountException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 577323663702062046L;

    public InvalidWithdrawAmountException(BigDecimal minimumValue) {
        super(ExceptionMessages.INVALID_WITHDRAW_AMOUNT.message() + " Min: " + minimumValue);
    }
}

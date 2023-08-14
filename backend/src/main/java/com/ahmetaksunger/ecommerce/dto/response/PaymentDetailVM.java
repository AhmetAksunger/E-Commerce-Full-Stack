package com.ahmetaksunger.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDetailVM {

    private long paymentId;
    private String creditCardNumber;
    private String cvv;
    private String expirationDate;
}

package com.ahmetaksunger.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreatePaymentDetailRequest {

    private String creditCardNumber;
    private String cvv;
    private Date expirationDate;
}

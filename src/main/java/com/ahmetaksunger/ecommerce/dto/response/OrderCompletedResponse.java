package com.ahmetaksunger.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class OrderCompletedResponse {

    private long id;
    private BigDecimal total;
    private CartVM cart;
    private PaymentDetailVM paymentDetail;

}

package com.ahmetaksunger.ecommerce.dto.request.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositRevenueRequest {

    private Long paymentDetailId;
    private BigDecimal depositAmount;

}

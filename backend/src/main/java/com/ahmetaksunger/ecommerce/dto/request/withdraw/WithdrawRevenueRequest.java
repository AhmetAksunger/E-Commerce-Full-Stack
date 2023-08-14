package com.ahmetaksunger.ecommerce.dto.request.withdraw;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawRevenueRequest {

    private Long paymentDetailId;
    private BigDecimal withdrawAmount;

}

package com.ahmetaksunger.ecommerce.dto.request.withdraw;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class WithdrawRevenueRequest {

    private Long paymentDetailId;
    private BigDecimal withdrawAmount;

}

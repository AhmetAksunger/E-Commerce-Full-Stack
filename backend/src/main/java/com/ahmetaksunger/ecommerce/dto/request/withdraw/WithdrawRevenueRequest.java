package com.ahmetaksunger.ecommerce.dto.request.withdraw;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRevenueRequest {

    private Long paymentDetailId;
    private BigDecimal withdrawAmount;

}

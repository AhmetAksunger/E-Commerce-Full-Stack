package com.ahmetaksunger.ecommerce.dto.response;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WithdrawSuccessResponse {
    private BigDecimal amount;
    private SellerVM seller;
}

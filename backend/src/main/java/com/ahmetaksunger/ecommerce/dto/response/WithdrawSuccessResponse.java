package com.ahmetaksunger.ecommerce.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class WithdrawSuccessResponse {
    private BigDecimal amount;
    private SellerVM seller;
}

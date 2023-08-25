package com.ahmetaksunger.ecommerce.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@Builder
public class CartItemVM {

    private Long id;
    private ProductVM product;
    private Integer quantity;
    private BigDecimal total;
}

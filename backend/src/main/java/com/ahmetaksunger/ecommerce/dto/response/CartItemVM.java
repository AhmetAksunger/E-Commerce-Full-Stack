package com.ahmetaksunger.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class CartItemVM {

    private Long id;
    private ProductVM product;
    private Integer quantity;
    private BigDecimal total;
}

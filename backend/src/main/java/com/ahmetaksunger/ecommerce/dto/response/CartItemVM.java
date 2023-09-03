package com.ahmetaksunger.ecommerce.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemVM {

    private Long id;
    private ProductVM product;
    private Integer quantity;
    private BigDecimal total;
}

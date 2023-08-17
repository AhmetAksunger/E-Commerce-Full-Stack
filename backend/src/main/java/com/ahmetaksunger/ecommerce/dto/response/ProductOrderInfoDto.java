package com.ahmetaksunger.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOrderInfoDto {
    private ProductVM product;
    private Long orderCount;
}

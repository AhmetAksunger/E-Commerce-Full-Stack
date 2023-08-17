package com.ahmetaksunger.ecommerce.dto.response;

import com.ahmetaksunger.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderInfo {
    private Product product;
    private Long orderCount;
}

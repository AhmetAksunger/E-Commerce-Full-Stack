package com.ahmetaksunger.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class CartItemVM {

    private long id;
    private long productId;
    private String productName;
    private BigDecimal productPrice;
    private int quantity;

}

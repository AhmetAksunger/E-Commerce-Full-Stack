package com.ahmetaksunger.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemVM {

    private long id;
    private long productId;
    private String productName;
    private int quantity;

}

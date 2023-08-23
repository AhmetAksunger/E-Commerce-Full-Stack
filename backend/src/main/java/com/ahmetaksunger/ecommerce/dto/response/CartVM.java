package com.ahmetaksunger.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartVM {
    private Long id;
    private CustomerVM customer;
    private List<CartItemVM> cartItems;
    private Integer totalProductCount;
    private BigDecimal total;
}

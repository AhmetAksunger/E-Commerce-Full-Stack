package com.ahmetaksunger.ecommerce.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartVM {
    private Long id;
    private CustomerVM customer;
    private List<CartItemVM> cartItems;
    private Integer totalProductCount;
    private BigDecimal total;
}

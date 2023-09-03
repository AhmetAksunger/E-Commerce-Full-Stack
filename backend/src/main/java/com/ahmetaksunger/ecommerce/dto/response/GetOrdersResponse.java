package com.ahmetaksunger.ecommerce.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetOrdersResponse {

    private Long id;
    private BigDecimal total;
    private List<CartItemVM> orderedProducts;
    private AddressVM address;

}

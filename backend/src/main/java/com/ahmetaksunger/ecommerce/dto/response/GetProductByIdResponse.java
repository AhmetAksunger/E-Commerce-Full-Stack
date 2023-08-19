package com.ahmetaksunger.ecommerce.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetProductByIdResponse extends ProductVM{

    private Long orderCount;
}

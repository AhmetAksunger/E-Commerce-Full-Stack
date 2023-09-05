package com.ahmetaksunger.ecommerce.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetProductByIdResponse extends ProductVM{

    private Long orderCount;
}

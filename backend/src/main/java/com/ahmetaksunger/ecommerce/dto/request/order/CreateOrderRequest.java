package com.ahmetaksunger.ecommerce.dto.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {
    @NotNull(message = "Cart Id cannot be null")
    private Long cartId;
    @NotNull(message = "Payment Detail Id cannot be null")
    private Long paymentDetailId;
    @NotNull(message = "Address Id cannot be null")
    private Long addressId;
}

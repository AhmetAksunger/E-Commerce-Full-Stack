package com.ahmetaksunger.ecommerce.dto.request.cartItem;

import com.ahmetaksunger.ecommerce.model.Customer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCartItemRequest {

    @NotNull(message = "Cart ID cannot be null")
    private Long cartId;

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1,message = "Quantity must at least be 1")
    private Integer quantity;

}

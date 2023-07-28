package com.ahmetaksunger.ecommerce.dto.request.product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Name length must be between 3 and 50 characters")
    private String name;
    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @Size(min = 3, max = 255, message = "Description length must be between 3 and 255 characters")
    private String description;
    @Min(value = 1, message = "Price must be greater than or equal to 1")
    private BigDecimal price;
    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private int quantity;
    @URL(message = "Logo must be a valid URL")
    private String logo;

    private List<Integer> categoryIds;

}

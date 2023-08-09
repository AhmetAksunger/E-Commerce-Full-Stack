package com.ahmetaksunger.ecommerce.dto.request.address;

import com.ahmetaksunger.ecommerce.exception.customValidation.ValidCountry;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAddressRequest {

    @NotNull(message = "Address cannot be null.")
    @NotBlank(message = "Address cannot be blank.")
    @Size(min = 5, max = 120, message = "Address must be between 5 and 120 characters.")
    private String address;

    @NotNull(message = "City cannot be null.")
    @NotBlank(message = "City cannot be blank.")
    @Size(min = 1, max = 50, message = "City must be between 1 and 50 characters.")
    private String city;

    @NotNull(message = "Country cannot be null.")
    @NotBlank(message = "Country cannot be blank.")
    @ValidCountry(message = "Invalid country")
    private String country;

    @NotNull(message = "Zip code cannot be null.")
    @NotBlank(message = "Zip code cannot be blank.")
    @Digits(integer = 5, fraction = 0, message = "Zip code must be a numeric value with at most 5 digits.")
    private String zipCode;
}

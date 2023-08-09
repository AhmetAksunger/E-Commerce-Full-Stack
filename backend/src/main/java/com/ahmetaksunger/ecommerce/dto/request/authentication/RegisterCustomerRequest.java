package com.ahmetaksunger.ecommerce.dto.request.authentication;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor

@NoArgsConstructor
@Data
public class RegisterCustomerRequest extends RegisterRequest {

    @NotNull(message = "Full name cannot be null")
    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 3, max = 50, message = "Full name length must be between 3 and 50")
    private String fullName;

    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be blank")
    @Digits(integer = 11,fraction = 0,message = "Phone number must contain only digits")
    private String phoneNumber;

}

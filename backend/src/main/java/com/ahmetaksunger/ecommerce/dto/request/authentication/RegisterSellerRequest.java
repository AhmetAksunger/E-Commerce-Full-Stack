package com.ahmetaksunger.ecommerce.dto.request.authentication;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterSellerRequest extends RegisterRequest {

    @NotNull(message = "Company name cannot be null")
    @NotBlank(message = "Company name cannot be blank")
    @Size(min = 3, max = 30, message = "Company name length must be between 3 and 30")
    private String companyName;

    @NotNull(message = "Contact number cannot be null")
    @NotBlank(message = "Contact number cannot be blank")
    @Digits(integer = 11, fraction = 0, message = "Contact number must contain only digits")
    private String contactNumber;

    @NotNull(message = "Logo cannot be null")
    @NotBlank(message = "Logo cannot be blank")
    @URL(message = "Logo must be a URL")
    private String logo;
}

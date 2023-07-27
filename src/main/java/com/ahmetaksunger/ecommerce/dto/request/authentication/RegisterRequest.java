package com.ahmetaksunger.ecommerce.dto.request.authentication;

import com.ahmetaksunger.ecommerce.exception.customValidation.UniqueEmail;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequest {

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @UniqueEmail
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long.")
    @Pattern(regexp = "^(?=.*\\d).{5,}$", message = "Password must contain at least one digit.")
    private String password;

}

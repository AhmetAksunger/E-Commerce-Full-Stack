package com.ahmetaksunger.ecommerce.dto.request.payment;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CreatePaymentDetailRequest {

    @NotNull(message = "Credit card number must not be null")
    @NotBlank(message = "Credit card number must not be blank")
    @Digits(integer = 16, fraction = 0, message = "Credit card number must be a 16-digit number")
    private String creditCardNumber;

    @Digits(integer = 3, fraction = 0, message = "CVV must be a 3-digit number")
    private String cvv;

    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "Expiration date must be in the format MM/YY")
    private String expirationDate;

    private long billingAddressId;
}

package com.ahmetaksunger.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterCustomerRequest extends RegisterRequest{

    private String fullName;
    private String phoneNumber;
}

package com.ahmetaksunger.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterSellerRequest extends RegisterRequest {

    private String companyName;
    private String contactNumber;
    private String logo;
}

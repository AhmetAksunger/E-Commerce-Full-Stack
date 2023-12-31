package com.ahmetaksunger.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerVM {
    private long sellerId;
    private String companyName;
    private String contactNumber;
    private String logo;
}

package com.ahmetaksunger.ecommerce.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SellerAuthenticationResponse extends AuthenticationResponse{

    private String companyName;
    private String contactNumber;
    private String logo;

}

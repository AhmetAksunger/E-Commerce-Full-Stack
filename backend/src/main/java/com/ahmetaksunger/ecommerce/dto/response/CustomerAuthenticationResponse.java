package com.ahmetaksunger.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAuthenticationResponse extends AuthenticationResponse{
    private String fullName;
    private String phoneNumber;
    private long cartId;
}

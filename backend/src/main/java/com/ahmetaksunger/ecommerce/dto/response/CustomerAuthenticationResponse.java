package com.ahmetaksunger.ecommerce.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerAuthenticationResponse extends AuthenticationResponse{
    private String fullName;
    private String phoneNumber;
    private long cartId;
}

package com.ahmetaksunger.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVM {
    private long id;
    private String fullName;
    private String phoneNumber;
}

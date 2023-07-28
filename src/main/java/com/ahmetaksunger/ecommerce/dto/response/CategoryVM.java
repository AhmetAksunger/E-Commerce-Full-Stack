package com.ahmetaksunger.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryVM {
    private long id;
    private String name;
    private String description;
}

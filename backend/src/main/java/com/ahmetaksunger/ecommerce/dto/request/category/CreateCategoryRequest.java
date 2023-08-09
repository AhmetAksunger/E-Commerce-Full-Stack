package com.ahmetaksunger.ecommerce.dto.request.category;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCategoryRequest {

    private String name;
    private String description;
}

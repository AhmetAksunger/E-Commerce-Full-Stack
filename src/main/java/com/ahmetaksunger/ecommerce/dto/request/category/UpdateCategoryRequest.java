package com.ahmetaksunger.ecommerce.dto.request.category;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCategoryRequest {

    private String name;
    private String description;
}

package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.category.CreateCategoryRequest;
import com.ahmetaksunger.ecommerce.dto.request.category.UpdateCategoryRequest;
import com.ahmetaksunger.ecommerce.dto.response.CategoryVM;

public interface CategoryService {
    CategoryVM create(CreateCategoryRequest createCategoryRequest);

    CategoryVM update(long categoryId,UpdateCategoryRequest updateCategoryRequest);
}

package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.category.CreateCategoryRequest;
import com.ahmetaksunger.ecommerce.dto.request.category.UpdateCategoryRequest;
import com.ahmetaksunger.ecommerce.dto.response.CategoryVM;
import com.ahmetaksunger.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryVM create(CreateCategoryRequest createCategoryRequest);
    CategoryVM update(long categoryId,UpdateCategoryRequest updateCategoryRequest);
    List<CategoryVM> getAllCategories();
    List<Category> getCategoriesByIds(List<Long> categoryIds);
}

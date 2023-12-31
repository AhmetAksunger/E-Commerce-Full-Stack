package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.category.CreateCategoryRequest;
import com.ahmetaksunger.ecommerce.dto.request.category.UpdateCategoryRequest;
import com.ahmetaksunger.ecommerce.dto.response.CategoryVM;
import com.ahmetaksunger.ecommerce.exception.notfound.CategoryNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Category;
import com.ahmetaksunger.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperService mapperService;

    @Override
    public CategoryVM create(CreateCategoryRequest createCategoryRequest) {
        Category category = mapperService.forRequest().map(createCategoryRequest, Category.class);

        return mapperService.forResponse().map(categoryRepository.save(category), CategoryVM.class);
    }

    @Override
    public CategoryVM update(long categoryId, UpdateCategoryRequest updateCategoryRequest) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        if (updateCategoryRequest.getName() != null) {
            category.setName(updateCategoryRequest.getName());
        }
        if (updateCategoryRequest.getDescription() != null) {
            category.setDescription(updateCategoryRequest.getDescription());
        }

        return mapperService.forResponse().map(categoryRepository.save(category), CategoryVM.class);
    }

    @Override
    public List<CategoryVM> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories
                .stream()
                .map(category -> mapperService.forResponse().map(category, CategoryVM.class)).toList();
    }

    @Override
    public List<Category> getCategoriesByIds(List<Long> categoryIds) {

        return categoryIds
                .stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(CategoryNotFoundException::new))
                .toList();
    }

}

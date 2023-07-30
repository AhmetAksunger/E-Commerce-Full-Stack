package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.category.CreateCategoryRequest;
import com.ahmetaksunger.ecommerce.dto.request.category.UpdateCategoryRequest;
import com.ahmetaksunger.ecommerce.dto.response.CategoryVM;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CategoryNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Category;
import com.ahmetaksunger.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final MapperService mapperService;
    @Override
    public CategoryVM create(CreateCategoryRequest createCategoryRequest) {
        Category category = mapperService.forRequest().map(createCategoryRequest, Category.class);
        category.setCreatedAt(new Date());

        return mapperService.forResponse().map(categoryRepository.save(category),CategoryVM.class);
    }

    @Override
    public CategoryVM update(long categoryId,UpdateCategoryRequest updateCategoryRequest) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotFoundException());

        if(updateCategoryRequest.getName() != null){
            category.setName(updateCategoryRequest.getName());
        }
        if(updateCategoryRequest.getDescription() != null){
            category.setDescription(updateCategoryRequest.getDescription());
        }

        category.setUpdatedAt(new Date());
        return mapperService.forResponse().map(categoryRepository.save(category),CategoryVM.class);
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

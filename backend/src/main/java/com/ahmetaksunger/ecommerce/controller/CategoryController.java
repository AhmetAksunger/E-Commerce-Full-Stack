package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.category.CreateCategoryRequest;
import com.ahmetaksunger.ecommerce.dto.request.category.UpdateCategoryRequest;
import com.ahmetaksunger.ecommerce.dto.response.CategoryVM;
import com.ahmetaksunger.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CategoryVM> createCategory(@RequestBody @Validated CreateCategoryRequest createCategoryRequest){
        return new ResponseEntity<>(categoryService.create(createCategoryRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CategoryVM> updateCategory(@RequestBody @Validated UpdateCategoryRequest updateCategoryRequest,
                                                     @PathVariable long categoryId){
        return ResponseEntity.ok(categoryService.update(categoryId,updateCategoryRequest));
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('CUSTOMER','SELLER','ADMIN')")
    public ResponseEntity<List<CategoryVM>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

}
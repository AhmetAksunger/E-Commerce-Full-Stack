package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.product.CreateProductRequest;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductVM> createProduct(@RequestBody @Validated CreateProductRequest createProductRequest,
                                                   @CurrentUser User loggedInUser){
        return new ResponseEntity<>(productService.create(createProductRequest,loggedInUser), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}/add")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductVM> addCategoriesByIdsToProduct(@PathVariable long productId,
                                                                 @RequestParam(name = "categoryIds",required = true)
                                                                 List<Long> categoryIds,
                                                                 @CurrentUser User loggedInUser){
        return ResponseEntity.ok(productService.addCategoriesByIdsToProduct(productId,categoryIds,loggedInUser));
    }

    @PutMapping("/{productId}/remove")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductVM> removeCategoriesByIdsFromProduct(@PathVariable long productId,
                                                                      @RequestParam(name = "categoryIds",required = true)
                                                                      List<Long> categoryIds,
                                                                      @CurrentUser User loggedInUser){
        return ResponseEntity.ok(productService.removeCategoriesByIdsFromProduct(productId,categoryIds,loggedInUser));
    }

}

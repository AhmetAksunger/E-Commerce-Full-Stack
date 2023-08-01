package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.product.CreateProductRequest;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('SELLER','CUSTOMER')")
    public ResponseEntity<Page<ProductVM>> getProducts(@RequestParam(name = "sort",required = false,defaultValue = "asc")
                                                       String sort,
                                                       @RequestParam(name = "order",required = false,defaultValue = "createdAt")
                                                       String order,
                                                       @RequestParam(name = "categoryIds",required = false)
                                                       List<Long> categoryIds,
                                                       @RequestParam(name = "minPrice",required = false,defaultValue = "0")
                                                       BigDecimal minPrice,
                                                       @RequestParam(name = "maxPrice",required = false,defaultValue = ""+Integer.MAX_VALUE)
                                                       BigDecimal maxPrice,
                                                       @RequestParam(name = "page",required = false,defaultValue = "0")
                                                       int page,
                                                       @RequestParam(name = "size",required = false,defaultValue = "5")
                                                       int size){

        return ResponseEntity.ok(productService.getProducts(sort,order,categoryIds,minPrice,maxPrice,page,size));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('SELLER')")
    public void deleteProduct(@PathVariable long productId, @CurrentUser User loggedInUser){
        productService.delete(productId,loggedInUser);
    }
}

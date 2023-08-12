package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.product.CreateProductRequest;
import com.ahmetaksunger.ecommerce.dto.request.product.UpdateProductRequest;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @PostMapping("/{productId}/categories")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductVM> addCategoriesByIdsToProduct(@PathVariable long productId,
                                                                 @RequestParam(name = "categoryIds",required = true)
                                                                 List<Long> categoryIds,
                                                                 @CurrentUser User loggedInUser){
        return ResponseEntity.ok(productService.addCategoriesByIdsToProduct(productId,categoryIds,loggedInUser));
    }

    @DeleteMapping("/{productId}/categories")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductVM> removeCategoriesByIdsFromProduct(@PathVariable long productId,
                                                                      @RequestParam(name = "categoryIds",required = true)
                                                                      List<Long> categoryIds,
                                                                      @CurrentUser User loggedInUser){
        return ResponseEntity.ok(productService.removeCategoriesByIdsFromProduct(productId,categoryIds,loggedInUser));
    }


    /**
     * Retrieves paginated models of products based on the specified criteria.
     * {@link com.ahmetaksunger.ecommerce.service.ProductManager#getProducts(String, String, String, List, BigDecimal, BigDecimal, Integer, Integer)}
     *
     * @param sort        sort products-> [asc,desc]
     * @param order       order products -> [createdAt,updatedAt,name,price]
     * @param search      search term -> searches by product name, category name and company name
     * @param categoryIds filter products by category ids
     * @param minPrice    minimum price for products
     * @param maxPrice    maximum price for products
     * @param page        page number
     * @param size        size number
     * @return Response Entity with paginated ProductVM(Product View Model)
     */
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('SELLER','CUSTOMER')")

    public ResponseEntity<Page<ProductVM>> getProducts(@RequestParam(name = "sort",required = false,defaultValue = "asc")
                                                       String sort,
                                                       @RequestParam(name = "order",required = false,defaultValue = "createdAt")
                                                       String order,
                                                       @RequestParam(name = "search",required = false)
                                                       String search,
                                                       @RequestParam(name = "categoryIds",required = false)
                                                       List<Long> categoryIds,
                                                       @RequestParam(name = "minPrice",required = false)
                                                       BigDecimal minPrice,
                                                       @RequestParam(name = "maxPrice",required = false)
                                                       BigDecimal maxPrice,
                                                       @RequestParam(name = "page",required = false,defaultValue = "0")
                                                       Integer page,
                                                       @RequestParam(name = "size",required = false,defaultValue = "5")
                                                       Integer size){

        return ResponseEntity.ok(productService.getProducts(sort,order,search,categoryIds,minPrice,maxPrice,page,size));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('SELLER')")
    public void deleteProduct(@PathVariable long productId, @CurrentUser User loggedInUser){
        productService.delete(productId,loggedInUser);
    }

    /**
     *
     * Retrieves a model of a product by its id.
     * This endpoint allows both customers and sellers to retrieve product information based on its id.
     *{@link com.ahmetaksunger.ecommerce.service.ProductManager#getProductById(Long)}
     *
     * @param productId Product id
     * @return A Response entity with ProductVM (Product View Model) with the 200 Status Code.
     */
    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('SELLER','CUSTOMER')")
    public ResponseEntity<ProductVM> getProductById(@PathVariable Long productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    /**
     * 
     * Retrieves a paginated model of products based on the specified seller id.
     * This endpoint allows both customers and sellers to retrieve products of specified seller
     * {@link com.ahmetaksunger.ecommerce.service.ProductManager#getProductsBySellerId(Long, Integer, Integer)}
     *
     * @param sellerId Seller Id
     * @return A Response entity with Paginated ProductVM (Product View Model)
     */
    @GetMapping("/seller/{sellerId}")
    @PreAuthorize("hasAnyAuthority('SELLER','CUSTOMER')")
    public ResponseEntity<Page<ProductVM>> getProductsBySellerId(@PathVariable Long sellerId,
                                                                 @RequestParam(name = "page",defaultValue = "0")
                                                                 Integer page,
                                                                 @RequestParam(name = "size",defaultValue = "5")
                                                                 Integer size){
        return ResponseEntity.ok(productService.getProductsBySellerId(sellerId,page,size));
    }

    /**
     * Retrieves the updated ProductVM
     * This controller allows seller to update their own products
     *
     * @param productId Product Id
     * @param updateProductRequest Update Product Request DTO {@link UpdateProductRequest}
     * @param loggedInUser Logged in user
     * @return Response entity with ProductVM
     */
    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('SELLER')")
    public ResponseEntity<ProductVM> updateProduct(@PathVariable Long productId,
                                                   @RequestBody UpdateProductRequest updateProductRequest,
                                                   @CurrentUser User loggedInUser){
        return ResponseEntity.ok(productService.updateProduct(productId,updateProductRequest,loggedInUser));
    }

}

package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.product.CreateProductRequest;
import com.ahmetaksunger.ecommerce.dto.request.product.ProductListRequest;
import com.ahmetaksunger.ecommerce.dto.request.product.UpdateProductRequest;
import com.ahmetaksunger.ecommerce.dto.response.GetProductByIdResponse;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.ProductManager;
import com.ahmetaksunger.ecommerce.service.ProductService;
import com.ahmetaksunger.ecommerce.util.ECommerceResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ECommerceResponse<ProductVM> createProduct(@RequestBody @Validated CreateProductRequest createProductRequest,
                                                   @CurrentUser User loggedInUser){
        return ECommerceResponse.createdOf(productService.create(createProductRequest,loggedInUser));
    }

    @PostMapping("/{productId}/categories")
    @PreAuthorize("hasAuthority('SELLER')")
    public ECommerceResponse<ProductVM> addCategoriesByIdsToProduct(@PathVariable long productId,
                                                                 @RequestParam(name = "categoryIds",required = true)
                                                                 List<Long> categoryIds,
                                                                 @CurrentUser User loggedInUser){
        return ECommerceResponse.successOf(productService.addCategoriesByIdsToProduct(productId,categoryIds,loggedInUser));
    }

    @DeleteMapping("/{productId}/categories")
    @PreAuthorize("hasAuthority('SELLER')")
    public ECommerceResponse<ProductVM> removeCategoriesByIdsFromProduct(@PathVariable long productId,
                                                                      @RequestParam(name = "categoryIds",required = true)
                                                                      List<Long> categoryIds,
                                                                      @CurrentUser User loggedInUser){
        return ECommerceResponse.successOf(productService.removeCategoriesByIdsFromProduct(productId,categoryIds,loggedInUser));
    }


    /**
     * Retrieves paginated models of products based on the {@link ProductListRequest}.
     *
     * @return Response Entity with paginated ProductVM(Product View Model)
     */
    @PostMapping("/get")
    @PreAuthorize("hasAnyAuthority('SELLER','CUSTOMER')")

    public ECommerceResponse<Page<ProductVM>> getProducts(@RequestBody @Valid ProductListRequest listRequest){

        return ECommerceResponse.successOf(productService.getProducts(listRequest));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('SELLER')")
    public ECommerceResponse<Void> deleteProduct(@PathVariable long productId, @CurrentUser User loggedInUser){
        productService.delete(productId,loggedInUser);
        return ECommerceResponse.SUCCESS;
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
    public ECommerceResponse<GetProductByIdResponse> getProductById(@PathVariable Long productId){
        return ECommerceResponse.successOf(productService.getProductById(productId));
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
    public ECommerceResponse<Page<GetProductByIdResponse>> getProductsBySellerId(@PathVariable Long sellerId,
                                                                              @RequestParam(name = "page",defaultValue = "0")
                                                                 Integer page,
                                                                              @RequestParam(name = "size",defaultValue = "5")
                                                                 Integer size){
        return ECommerceResponse.successOf(productService.getProductsBySellerId(sellerId,page,size));
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
    public ECommerceResponse<ProductVM> updateProduct(@PathVariable Long productId,
                                                   @RequestBody UpdateProductRequest updateProductRequest,
                                                   @CurrentUser User loggedInUser){
        return ECommerceResponse.successOf(productService.updateProduct(productId,updateProductRequest,loggedInUser));
    }

    /**
     * Retrieves the top ten most ordered products from the {@link ProductManager#getTop10MostOrderedProducts()}
     * And returns a {@link ResponseEntity<List<ProductVM>}
     * @return Returns a {@link ResponseEntity<List<ProductVM>}
     */
    @GetMapping("/most-ordered")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ECommerceResponse<List<ProductVM>> getMostOrderedProducts(){
        return ECommerceResponse.successOf(productService.getTop10MostOrderedProducts());
    }

}

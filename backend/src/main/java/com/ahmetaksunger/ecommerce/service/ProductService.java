package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.product.CreateProductRequest;
import com.ahmetaksunger.ecommerce.dto.request.product.ProductListRequest;
import com.ahmetaksunger.ecommerce.dto.request.product.UpdateProductRequest;
import com.ahmetaksunger.ecommerce.dto.response.GetProductByIdResponse;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductVM create(CreateProductRequest createProductRequest, User loggedInUser);

    ProductVM addCategoriesByIdsToProduct(long productId, List<Long> categoryIds, User loggedInUser);

    ProductVM removeCategoriesByIdsFromProduct(long productId, List<Long> categoryIds, User loggedInUser);

    Page<ProductVM> getProducts(ProductListRequest productListRequest);

    void delete(long productId, User loggedInUser);

    void reduceQuantityForPurchasedProducts(Cart cart);

    GetProductByIdResponse getProductById(Long productId);

    Page<GetProductByIdResponse> getProductsBySellerId(Long sellerId, Integer page, Integer size);

    ProductVM updateProduct(Long productId, UpdateProductRequest updateProductRequest, User loggedInUser);

    List<ProductVM> getTop10MostOrderedProducts();
}

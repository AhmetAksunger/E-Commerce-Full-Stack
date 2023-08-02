package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.product.CreateProductRequest;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductVM create(CreateProductRequest createProductRequest, User loggedInUser);
    ProductVM addCategoriesByIdsToProduct(long productId, List<Long> categoryIds, User loggedInUser);
    ProductVM removeCategoriesByIdsFromProduct(long productId, List<Long> categoryIds,User loggedInUser);
    Page<ProductVM> getProducts(String sort, String order, List<Long> category, BigDecimal minPrice,
                                BigDecimal maxPrice,
                                int page, int size);

    void delete(long productId, User loggedInUser);

    void reduceQuantityForBoughtProducts(List<Product> boughtProducts);
}

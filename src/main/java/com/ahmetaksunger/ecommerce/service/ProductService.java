package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.product.CreateProductRequest;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.model.User;

import java.util.List;

public interface ProductService {
    ProductVM create(CreateProductRequest createProductRequest, User loggedInUser);
    ProductVM addCategoriesByIdsToProduct(long productId, List<Long> categoryIds, User loggedInUser);
    ProductVM removeCategoriesByIdsFromProduct(long productId, List<Long> categoryIds,User loggedInUser);
}

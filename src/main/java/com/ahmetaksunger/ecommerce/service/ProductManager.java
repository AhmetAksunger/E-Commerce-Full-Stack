package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.authentication.RegisterSellerRequest;
import com.ahmetaksunger.ecommerce.dto.request.product.CreateProductRequest;
import com.ahmetaksunger.ecommerce.dto.response.CategoryVM;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.dto.response.SellerVM;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Category;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductManager implements ProductService{

    private final ProductRepository productRepository;
    private final MapperService mapperService;
    private final CategoryService categoryService;
    @Override
    public ProductVM create(CreateProductRequest createProductRequest, User loggedInUser) {

        Product product = mapperService.forRequest().map(createProductRequest, Product.class);
        product.setCreatedAt(new Date());
        product.setSeller((Seller) loggedInUser);
        List<Category> categories = categoryService.getCategoriesByIds(createProductRequest.getCategoryIds());
        product.setCategories(categories);
        var response = mapperService.forResponse().map(productRepository.save(product),ProductVM.class);
        response.setSeller(mapperService.forResponse().map(product.getSeller(), SellerVM.class));
        return response;
    }

}

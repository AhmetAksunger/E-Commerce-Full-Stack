package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.product.CreateProductRequest;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.dto.response.SellerVM;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.ProductNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Category;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.ProductRepository;
import com.ahmetaksunger.ecommerce.service.rules.ProductRules;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductManager implements ProductService{

    private final ProductRepository productRepository;
    private final MapperService mapperService;
    private final CategoryService categoryService;
    private final ProductRules productRules;
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

    @Override
    public ProductVM addCategoriesByIdsToProduct(long productId, List<Long> categoryIds, User loggedInUser) {

        //Rules
        productRules.checkIfCanUpdate(productId,loggedInUser);

        Product product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException());
        List<Category> dbCategories = product.getCategories();
        List<Category> categoriesToBeAdded = categoryService.getCategoriesByIds(categoryIds);
        dbCategories.addAll(
                categoriesToBeAdded.stream()
                        .filter(category -> !dbCategories.contains(category))
                        .toList()
        );
        product.setCategories(dbCategories);
        product.setUpdatedAt(new Date());
        return mapperService.forResponse().map(productRepository.save(product),ProductVM.class);
    }

    @Override
    public ProductVM removeCategoriesByIdsFromProduct(long productId, List<Long> categoryIds, User loggedInUser) {

        //Rules
        productRules.checkIfCanUpdate(productId,loggedInUser);

        Product product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException());
        List<Category> dbCategories = product.getCategories();
        List<Category> categoriesToBeRemoved = categoryService.getCategoriesByIds(categoryIds);
        dbCategories.removeAll(
                categoriesToBeRemoved.stream()
                        .filter(category -> dbCategories.contains(category)).
                        toList()
        );
        product.setCategories(dbCategories);
        product.setUpdatedAt(new Date());
        return mapperService.forResponse().map(productRepository.save(product),ProductVM.class);
    }

    @Override
    public Page<ProductVM> getProducts(String sort, String order,
                                       List<Long> categoryIds, BigDecimal minPrice,
                                       BigDecimal maxPrice, int page, int size) {

        //Rules
        productRules.checkIfSortParamIsValid(sort);
        productRules.checkIfOrderParamIsValid(order);

        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.valueOf(sort.toUpperCase()),order));
        Page<Product> products = productRepository.findByCategories_IdInAndPriceBetween(categoryIds,minPrice,maxPrice,pageable);
        return products.map(product -> mapperService.forResponse().map(product,ProductVM.class));
    }

}

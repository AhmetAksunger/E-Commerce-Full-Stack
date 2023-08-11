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
import com.ahmetaksunger.ecommerce.spesification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
                        .filter(dbCategories::contains).
                        toList()
        );
        product.setCategories(dbCategories);
        product.setUpdatedAt(new Date());
        return mapperService.forResponse().map(productRepository.save(product),ProductVM.class);
    }

    /**
     * Retrieves paginated list of products based on the specified filters.
     *
     * @param sort        A field to sort, valid values are: [asc, desc].
     *                    See {@link ProductRules#checkIfSortParamIsValid(String)}
     * @param order       A field to order, valid values are: [name,price,createdAt,updatedAt]
     *                    See {@link ProductRules#checkIfOrderParamIsValid(String)}
     * @param search      A field to search name,category or company name
     * @param categoryIds List of category IDs
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @param page        Page number
     * @param size        Element amount on each page
     * @return
     */
    @Override
    public Page<ProductVM> getProducts(String sort, String order,
                                       String search, List<Long> categoryIds, BigDecimal minPrice,
                                       BigDecimal maxPrice, Integer page, Integer size) {

        //Rules
        productRules.checkIfSortParamIsValid(sort);
        productRules.checkIfOrderParamIsValid(order);

        Specification<Product> specification = Specification.where(null);

        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.valueOf(sort.toUpperCase()),order));

        if(search != null && !search.isEmpty()){
            specification = specification.and(ProductSpecification.searchByKeyword(search));
        }

        if(categoryIds != null && !categoryIds.isEmpty()) {
            specification = specification.and(ProductSpecification.withCategoryIds(categoryIds));
        }
        if(minPrice != null){
            specification = specification.and(ProductSpecification.withPriceGreaterThanOrEqualTo(minPrice));
        }
        if(maxPrice != null){
            specification = specification.and(ProductSpecification.withPriceLessThanOrEqualTo(maxPrice));
        }

        return productRepository.findAll(specification,pageable)
                .map(product -> mapperService.forResponse().map(product,ProductVM.class));
    }

    @Override
    public void delete(long productId, User loggedInUser) {

        //Rules
        productRules.checkIfCanDelete(productId,loggedInUser);

        productRepository.deleteById(productId);
    }

    @Override
    public void reduceQuantityForBoughtProducts(List<Product> boughtProducts){
        boughtProducts.forEach(product -> {
            product.setQuantity(product.getQuantity()-1);
            productRepository.save(product);
        });
    }

    /**
     * Retrieves a product from the database, based on the id specified.
     * Maps the product to the dto "ProductVM"
     *
     * @param productId Product id.
     * @return ProductVM
     */
    @Override
    public ProductVM getProductById(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        return mapperService.forResponse().map(product,ProductVM.class);
    }

}

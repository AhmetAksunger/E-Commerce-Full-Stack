package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.converter.ProductVMConverter;
import com.ahmetaksunger.ecommerce.dto.request.product.CreateProductRequest;
import com.ahmetaksunger.ecommerce.dto.request.product.UpdateProductRequest;
import com.ahmetaksunger.ecommerce.dto.response.*;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.ProductNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.*;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductManager implements ProductService {

    private final ProductRepository productRepository;
    private final MapperService mapperService;
    private final ProductVMConverter productVMConverter;
    private final CategoryService categoryService;
    private final ProductRules productRules;

    @Override
    public ProductVM create(CreateProductRequest createProductRequest, User loggedInUser) {

        Product product = mapperService.forRequest().map(createProductRequest, Product.class);
        product.setSeller((Seller) loggedInUser);
        List<Category> categories = categoryService.getCategoriesByIds(createProductRequest.getCategoryIds());
        product.setCategories(categories);

        return productVMConverter.convert(productRepository.save(product));
    }

    @Override
    public ProductVM addCategoriesByIdsToProduct(long productId, List<Long> categoryIds, User loggedInUser) {

        //Rules
        productRules.checkIfCanUpdate(productId, loggedInUser);

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException());
        List<Category> dbCategories = product.getCategories();
        List<Category> categoriesToBeAdded = categoryService.getCategoriesByIds(categoryIds);
        dbCategories.addAll(
                categoriesToBeAdded.stream()
                        .filter(category -> !dbCategories.contains(category))
                        .toList()
        );
        product.setCategories(dbCategories);
        return productVMConverter.convert(productRepository.save(product));
    }

    @Override
    public ProductVM removeCategoriesByIdsFromProduct(long productId, List<Long> categoryIds, User loggedInUser) {

        //Rules
        productRules.checkIfCanUpdate(productId, loggedInUser);

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException());
        List<Category> dbCategories = product.getCategories();
        List<Category> categoriesToBeRemoved = categoryService.getCategoriesByIds(categoryIds);
        dbCategories.removeAll(
                categoriesToBeRemoved.stream()
                        .filter(dbCategories::contains).
                        toList()
        );
        product.setCategories(dbCategories);
        return productVMConverter.convert(productRepository.save(product));
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
     * @param minPrice    Minimum price
     * @param maxPrice    Maximum price
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

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(sort.toUpperCase()), order));

        if (search != null && !search.isEmpty()) {
            specification = specification.and(ProductSpecification.searchByKeyword(search));
        }

        if (categoryIds != null && !categoryIds.isEmpty()) {
            specification = specification.and(ProductSpecification.withCategoryIds(categoryIds));
        }
        if (minPrice != null) {
            specification = specification.and(ProductSpecification.withPriceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(ProductSpecification.withPriceLessThanOrEqualTo(maxPrice));
        }

        return productRepository.findAll(specification, pageable)
                .map(productVMConverter::convert);
    }

    @Override
    public void delete(long productId, User loggedInUser) {

        //Rules
        productRules.checkIfCanDelete(productId, loggedInUser);

        productRepository.deleteById(productId);
    }


    /**
     * Updates the product quantities by decrementing the quantities specified in the cart items
     *
     * @param cart {@link Cart}
     */
    @Override
    public void reduceQuantityForPurchasedProducts(Cart cart) {
        cart.getCartItems().forEach(cartItem -> {
            var product = cartItem.getProduct();
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
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
    public GetProductByIdResponse getProductById(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        GetProductByIdResponse response = mapperService.forResponse().map(product, GetProductByIdResponse.class);
        var orderCount = productRepository.getOrderCountByProductId(productId);
        response.setOrderCount(orderCount != null ? orderCount : 0L);
        return response;
    }

    /**
     * Retrieves products by seller id from the database
     *
     * @param sellerId Seller Id
     * @param page     Page number
     * @param size     Element amount on each page
     * @return Paginated ProductVM
     */
    @Override
    public Page<GetProductByIdResponse> getProductsBySellerId(Long sellerId, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return productRepository.findBySellerId(sellerId, pageable)
                .map(product -> {
                    GetProductByIdResponse response = mapperService.forResponse().map(product, GetProductByIdResponse.class);
                    var orderCount = productRepository.getOrderCountByProductId(product.getId());
                    response.setOrderCount(orderCount != null ? orderCount : 0L);
                    return response;
                });
    }

    /**
     * Retrieves the product with the specified id.
     * Verifies that the product belongs to the logged-in seller.
     * Updates the product based on the updateProductRequest, and persists it to the database
     *
     * @param productId            Product Id
     * @param updateProductRequest Update Product Request
     * @param loggedInUser         Logged In User
     * @return ProductVM
     */
    @Override
    public ProductVM updateProduct(Long productId, UpdateProductRequest updateProductRequest, User loggedInUser) {

        //Rules
        productRules.checkIfCanUpdate(productId, loggedInUser);

        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        if (updateProductRequest.getName() != null) {
            product.setName(updateProductRequest.getName());
        }
        if (updateProductRequest.getDescription() != null) {
            product.setDescription(updateProductRequest.getDescription());
        }
        if (updateProductRequest.getPrice() != null) {
            product.setPrice(updateProductRequest.getPrice());
        }
        if (updateProductRequest.getQuantity() != null) {
            product.setQuantity(updateProductRequest.getQuantity());
        }
        if (updateProductRequest.getLogo() != null) {
            product.setLogo(updateProductRequest.getLogo());
        }

        return productVMConverter.convert(productRepository.save(product));
    }

    /**
     * Retrieves the top ten most ordered products from the {@link ProductRepository#getTop10MostOrderedProducts()}
     * Maps them to a list of {@link ProductVM}, and returns.
     *
     * @return {@link List<ProductVM>}
     */
    @Override
    public List<ProductVM> getTop10MostOrderedProducts() {
        List<ProductOrderInfo> productOrderInfos = productRepository.getTop10MostOrderedProducts();

        return productOrderInfos.stream()
                .map(productVMConverter::convert).collect(Collectors.toList());
    }

}

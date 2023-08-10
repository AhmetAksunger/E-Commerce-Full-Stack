package com.ahmetaksunger.ecommerce.spesification;


import com.ahmetaksunger.ecommerce.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class ProductSpecification {


    /**
     * Creates a specification that filters products based on a search for product name
     *
     * @param name product name
     * @return Specification
     */
    public static Specification<Product> withNameLike(String name){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"),"%" + name + "%");
    }

    /**
     * Creates a specification that filters products with
     * a price greater than or equal to the specified price.
     *
     * @param price The minimum price value.
     * @return Specification
     */
    public static Specification<Product> withPriceGreaterThanOrEqualTo(BigDecimal price){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"),price);
    }

    /**
     * Creates a specification that filters products
     * with a price less than or equal to the specified price.
     *
     * @param price The maximum price value.
     * @return Specification
     */
    public static Specification<Product> withPriceLessThanOrEqualTo(BigDecimal price){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"),price);
    }

    /**
     * Creates a specification that filters products based on a list of category ids
     *
     * @param categoryIds The category ids
     * @return the specification
     */
    public static Specification<Product> withCategoryIds(List<Long> categoryIds){
        return (root, query, criteriaBuilder) ->
                root.join("categories").get("id").in(categoryIds);
    }

}

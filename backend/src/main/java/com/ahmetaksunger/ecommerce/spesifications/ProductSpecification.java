package com.ahmetaksunger.ecommerce.spesifications;


import com.ahmetaksunger.ecommerce.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> withNameLike(String name){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"),"%" + name + "%");
    }

    public static Specification<Product> withPriceGreaterThanOrEqualTo(BigDecimal price){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"),price);
    }

    public static Specification<Product> withPriceLessThanOrEqualTo(BigDecimal price){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"),price);
    }

    public static Specification<Product> withCategoryIds(List<Long> categoryIds){
        return (root, query, criteriaBuilder) ->
                root.join("categories").get("id").in(categoryIds);
    }

}

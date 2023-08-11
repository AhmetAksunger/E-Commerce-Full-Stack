package com.ahmetaksunger.ecommerce.spesification;


import com.ahmetaksunger.ecommerce.model.Category;
import com.ahmetaksunger.ecommerce.model.Product;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class ProductSpecification {


    /**
     * Creates a specification that filters products based on the search keyword.
     * It searches for the keyword on product name, category name and company name
     *
     * @param searchTerm
     * @return Specification
     */
    public static Specification<Product> searchByKeyword(String searchTerm){
        return (root, query, criteriaBuilder) -> {

            var categoryJoin = root.join("categories");
            Expression<String> categoryName = criteriaBuilder.lower(categoryJoin.get("name"));

            var sellerJoin = root.join("seller");
            Expression<String> companyName = criteriaBuilder.lower(sellerJoin.get("companyName"));

            Expression<String> productName = criteriaBuilder.lower(root.get("name"));

            var searchTermLower = searchTerm.toLowerCase();
            return criteriaBuilder.or(
                    criteriaBuilder.like(productName,"%" + searchTermLower + "%"),
                    criteriaBuilder.like(categoryName,"%" + searchTermLower + "%"),
                    criteriaBuilder.like(companyName,"%" + searchTermLower + "%")
            );
        };
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

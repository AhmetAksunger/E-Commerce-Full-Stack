package com.ahmetaksunger.ecommerce.dto.request.product;

import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.spesification.ProductSpecification;
import com.ahmetaksunger.ecommerce.util.ECommerceFiltering;
import com.ahmetaksunger.ecommerce.util.ECommercePagingRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductListRequest extends ECommercePagingRequest implements ECommerceFiltering<Product> {

    @NotNull
    private Filter filter;

    @Getter
    @Setter
    public static class Filter {
        private String search;
        private List<Long> categoryIds;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
    }

    @Override
    public Specification<Product> toSpecification() {

        Specification<Product> specification = ProductSpecification.withActiveStatus();

        if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            specification = specification.and(ProductSpecification.searchByKeyword(filter.getSearch()));
        }

        if (filter.getCategoryIds() != null && !filter.getCategoryIds().isEmpty()) {
            specification = specification.and(ProductSpecification.withCategoryIds(filter.getCategoryIds()));
        }
        if (filter.getMinPrice() != null) {
            specification = specification.and(ProductSpecification.withPriceGreaterThanOrEqualTo(filter.getMinPrice()));
        }
        if (filter.getMaxPrice() != null) {
            specification = specification.and(ProductSpecification.withPriceLessThanOrEqualTo(filter.getMaxPrice()));
        }

        return specification;
    }
}

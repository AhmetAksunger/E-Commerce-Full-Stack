package com.ahmetaksunger.ecommerce.util;

import org.springframework.data.jpa.domain.Specification;

public interface ECommerceFiltering<T> {
    Specification<T> toSpecification();
}

package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByCategories_IdInAndPriceBetween(List<Long> categoryIds,BigDecimal minPrice,BigDecimal maxPrice,Pageable pageable);
}

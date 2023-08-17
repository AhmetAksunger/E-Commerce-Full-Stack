package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.dto.response.ProductOrderInfo;
import com.ahmetaksunger.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByCategories_IdInAndPriceBetween(List<Long> categoryIds,BigDecimal minPrice,BigDecimal maxPrice,Pageable pageable);
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<Product> findBySellerId(Long sellerId,Pageable pageable);

    @Query("SELECT new com.ahmetaksunger.ecommerce.dto.response.ProductOrderInfo(c.product,count(o.id)) " +
            "FROM Order o JOIN CartItem c ON o.cart = c.cart GROUP BY c.product " +
            "ORDER BY count(o.id) DESC " +
            "LIMIT 10")
    List<ProductOrderInfo> getTop10MostOrderedProducts();
}

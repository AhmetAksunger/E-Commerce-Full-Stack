package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.dto.response.ProductOrderInfo;
import com.ahmetaksunger.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p WHERE p.seller.id=:sellerId AND p.status='ACTIVE'")
    Page<Product> findActiveProductsBySellerId(Long sellerId, Pageable pageable);

    @Query("SELECT new com.ahmetaksunger.ecommerce.dto.response.ProductOrderInfo(c.product,count(o.id)) " +
            "FROM Order o JOIN CartItem c ON o.cart = c.cart " +
            "WHERE c.product.status='ACTIVE' " +
            "GROUP BY c.product " +
            "ORDER BY count(o.id) DESC " +
            "LIMIT 10")
    List<ProductOrderInfo> getTop10MostOrderedActiveProducts();

    @Query("SELECT count(o.id) FROM Order o JOIN CartItem c ON o.cart = c.cart " +
            "WHERE c.product.id = :id GROUP BY c.product")
    Long getOrderCountByProductId(Long id);
}

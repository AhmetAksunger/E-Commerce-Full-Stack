package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Integer countByCustomerId(long customerId);

    Optional<Cart> findByCustomerId(long customerId);

    @Query("select c from Cart c where c.customer.id = ?1 and c.status='ACTIVE'")
    Optional<Cart> findActiveCartsByCustomerId(Long customerId);
}

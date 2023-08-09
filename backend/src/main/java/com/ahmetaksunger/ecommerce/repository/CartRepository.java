package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Integer countByCustomerId(long customerId);
    Optional<Cart> findByCustomerId(long customerId);

}

package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Integer countByCustomerId(long customerId);
}

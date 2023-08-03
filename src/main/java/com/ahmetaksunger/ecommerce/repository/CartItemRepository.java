package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(long cartId);
}

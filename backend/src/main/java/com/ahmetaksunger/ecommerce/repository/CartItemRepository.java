package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long cartId);

    Optional<CartItem> findByCartIdAndProductId(Long cartId,Long productId);
}

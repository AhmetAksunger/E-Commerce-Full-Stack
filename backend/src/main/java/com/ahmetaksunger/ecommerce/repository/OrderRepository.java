package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> getOrdersByCustomerId(Long customerId, Pageable pageable);

}

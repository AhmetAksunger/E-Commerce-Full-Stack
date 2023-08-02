package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}

package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}

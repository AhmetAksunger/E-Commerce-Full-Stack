package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Long> {
}

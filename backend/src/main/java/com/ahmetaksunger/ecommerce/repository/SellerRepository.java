package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    Optional<Seller> findByEmail(String email);

}

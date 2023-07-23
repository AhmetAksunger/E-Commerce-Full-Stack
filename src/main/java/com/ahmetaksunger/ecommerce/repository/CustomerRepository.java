package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}

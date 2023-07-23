package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}

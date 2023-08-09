package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Address;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> getByUserId(long id);
}

package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query("SELECT a FROM Address a WHERE a.user.id=:id AND a.status='ACTIVE'")
    List<Address> getActiveAddressByUserId(long id);
}

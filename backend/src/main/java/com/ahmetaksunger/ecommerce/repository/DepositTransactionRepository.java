package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.transaction.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Long> {
}
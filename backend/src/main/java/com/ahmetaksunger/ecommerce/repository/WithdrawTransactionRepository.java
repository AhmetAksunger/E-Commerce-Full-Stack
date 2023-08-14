package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.transaction.WithdrawTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawTransactionRepository extends JpaRepository<WithdrawTransaction, Long> {
}
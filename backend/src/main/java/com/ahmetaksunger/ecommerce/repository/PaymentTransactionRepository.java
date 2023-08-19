package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.transaction.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}
package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail,Long> {

    @Query("SELECT p FROM PaymentDetail p WHERE p.user.id=:userId AND p.status='ACTIVE'")
    List<PaymentDetail> getActivePaymentDetailsByUserId(long userId);
}

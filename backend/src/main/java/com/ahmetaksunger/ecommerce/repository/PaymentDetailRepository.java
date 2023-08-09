package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail,Long> {

    List<PaymentDetail> getByUserId(long userId);
}

package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.model.Seller;

import java.math.BigDecimal;

public interface SellerService {
    void updateTotalRevenue(Seller seller, BigDecimal amount, boolean isIncrement);

}

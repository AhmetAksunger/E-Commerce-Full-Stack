package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.withdraw.WithdrawRevenueRequest;
import com.ahmetaksunger.ecommerce.dto.response.WithdrawSuccessResponse;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.User;

import java.math.BigDecimal;

public interface SellerService {
    void updateTotalRevenue(Seller seller, BigDecimal amount, boolean isIncrement);

    WithdrawSuccessResponse withdraw(WithdrawRevenueRequest withdrawRevenueRequest, User loggedInUser);
}

package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.InsufficientRevenueException;
import com.ahmetaksunger.ecommerce.exception.InvalidWithdrawAmountException;
import com.ahmetaksunger.ecommerce.model.Seller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WithdrawRules {

    @Value("min-withdraw-limit")
    private BigDecimal minWithdrawLimit;
    public void checkIfSellerHasEnoughRevenueToWithdraw(Seller seller, BigDecimal amount){
        if(seller.getTotalRevenue().compareTo(amount) < 0){
            throw new InsufficientRevenueException();
        }
    }

    public void checkIfWithdrawAmountValid(BigDecimal amount){
        if(amount.compareTo(minWithdrawLimit) < 0){
            throw new InvalidWithdrawAmountException(minWithdrawLimit);
        }
    }

}

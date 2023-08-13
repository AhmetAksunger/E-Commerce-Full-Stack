package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SellerManager implements SellerService{

    private final SellerRepository sellerRepository;
    @Override
    public void updateTotalRevenue(Seller seller, BigDecimal amount, boolean isIncrement) {
        if(isIncrement){
            seller.incrementTotalRevenue(amount);
        }else{
            seller.decrementTotalRevenue(amount);
        }
    }
}

package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.InsufficientRevenueException;
import com.ahmetaksunger.ecommerce.exception.InvalidWithdrawAmountException;
import com.ahmetaksunger.ecommerce.model.Seller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

class WithdrawRulesTest {

    private WithdrawRules withdrawRules;
    private BigDecimal minWithDrawLimit = BigDecimal.valueOf(120);
    @BeforeEach
    void setUp() {
        withdrawRules = new WithdrawRules(minWithDrawLimit);
    }

    @DisplayName("When when_checkIfSellerHasEnoughRevenueToWithdraw_calledWithSellerRevenueLessThanAmount" +
            "method is called with a seller that has less revenue than the entered amount")
    @Test
    void when_checkIfSellerHasEnoughRevenueToWithdraw_calledWithSellerRevenueLessThanAmount() {

        final BigDecimal sellerRevenue = BigDecimal.valueOf(120);
        final BigDecimal requestAmount = BigDecimal.valueOf(130);
        final Seller seller = Mockito.mock(Seller.class);
        Mockito.when(seller.getTotalRevenue()).thenReturn(sellerRevenue);

        Assertions.assertThrows(InsufficientRevenueException.class,
                () -> withdrawRules.checkIfSellerHasEnoughRevenueToWithdraw(seller,requestAmount));

    }

    @DisplayName("When when_checkIfSellerHasEnoughRevenueToWithdraw_calledWithSellerRevenueLessThanAmount" +
            "method is called with a seller that has less revenue than the entered amount")
    @Test
    void when_checkIfWithdrawAmountValid_calledWithTinyWithdrawAmount() {

        final BigDecimal requestAmount = BigDecimal.valueOf(0.1);

        Assertions.assertThrows(InvalidWithdrawAmountException.class,
                () -> withdrawRules.checkIfWithdrawAmountValid(requestAmount));

    }
}
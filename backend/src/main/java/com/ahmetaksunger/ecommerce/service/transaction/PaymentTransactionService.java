package com.ahmetaksunger.ecommerce.service.transaction;

import com.ahmetaksunger.ecommerce.dto.request.withdraw.WithdrawRevenueRequest;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentTransaction;

import java.math.BigDecimal;

public interface PaymentTransactionService {
    PaymentTransaction createTransactionForPurchaseOperations(Customer customer, PaymentDetail paymentDetail, Product boughtProduct);

    PaymentTransaction createTransactionForWithdrawOperations(Seller seller, PaymentDetail paymentDetail, BigDecimal withdrawAmount);
}

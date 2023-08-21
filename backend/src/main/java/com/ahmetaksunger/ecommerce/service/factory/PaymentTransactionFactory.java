package com.ahmetaksunger.ecommerce.service.factory;

import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentStatus;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentTransaction;
import com.ahmetaksunger.ecommerce.model.transaction.TransactionType;

import java.math.BigDecimal;

public class PaymentTransactionFactory {

    /**
     * Returns a new instance of {@link PaymentTransaction} based on the specified {@link TransactionType}
     *
     * @param transactionType the {@link TransactionType}
     * @param status the {@link PaymentStatus}
     * @param user the {@link User} (did cast either to the {@link Customer} or {@link Seller})
     * @param amount Amount
     * @param paymentDetail the {@link PaymentDetail}
     * @param exceptionMessage Exception message
     *
     * @return Returns a new instance of {@link PaymentTransaction} based on the specified {@link TransactionType}
     */
    public static PaymentTransaction create(TransactionType transactionType, PaymentStatus status,
                       User user, BigDecimal amount, PaymentDetail paymentDetail, String exceptionMessage) {

        PaymentTransaction paymentTransaction;
        if (transactionType.equals(TransactionType.WITHDRAW)) {
            paymentTransaction = new PaymentTransaction((Seller) user, amount, paymentDetail, transactionType, status, exceptionMessage);
        } else if (transactionType.equals(TransactionType.PURCHASE)) {
            paymentTransaction = new PaymentTransaction((Customer) user, amount, paymentDetail, transactionType, status, exceptionMessage);
        } else{
            paymentTransaction = new PaymentTransaction((Customer) user, amount, paymentDetail, transactionType, status, exceptionMessage);
        }

        return paymentTransaction;
    }
}

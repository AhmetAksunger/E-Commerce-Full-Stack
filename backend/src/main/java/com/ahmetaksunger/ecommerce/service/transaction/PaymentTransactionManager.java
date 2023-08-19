package com.ahmetaksunger.ecommerce.service.transaction;

import com.ahmetaksunger.ecommerce.dto.request.withdraw.WithdrawRevenueRequest;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentStatus;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentTransaction;
import com.ahmetaksunger.ecommerce.model.transaction.TransactionType;
import com.ahmetaksunger.ecommerce.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentTransactionManager implements PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;

    /**
     * Saves a payment transaction, marked with PURCHASE, to the {@link PaymentTransaction} table.
     *
     * @param customer The customer who has ordered
     * @param paymentDetail The payment detail that the customer used
     * @param boughtProduct The product that the customer purchased
     * @return Returns the saved {@link PaymentTransaction}
     */
    @Override
    public PaymentTransaction createTransactionForPurchaseOperations(Customer customer,
                                                                     PaymentDetail paymentDetail,
                                                                     Product boughtProduct) {
        PaymentTransaction transaction = PaymentTransaction.builder()
                .customer(customer)
                .seller(boughtProduct.getSeller())
                .amount(boughtProduct.getPrice())
                .paymentDetail(paymentDetail)
                .transactionType(TransactionType.PURCHASE)
                .status(PaymentStatus.COMPLETED)
                .build();

        return paymentTransactionRepository.save(transaction);
    }

    /**
     * Saves a payment transaction, marked with WITHDRAW, to the {@link PaymentTransaction} table.
     *
     * @param seller The Seller who has withdrawn
     * @param paymentDetail The payment detail that the seller used
     * @param withdrawAmount The withdrawn amount
     * @return Returns the saved {@link PaymentTransaction}
     */
    @Override
    public PaymentTransaction createTransactionForWithdrawOperations(Seller seller,
                                                                     PaymentDetail paymentDetail,
                                                                     BigDecimal withdrawAmount) {
        PaymentTransaction transaction = PaymentTransaction.builder()
                .customer(null)
                .seller(seller)
                .amount(withdrawAmount)
                .paymentDetail(paymentDetail)
                .transactionType(TransactionType.WITHDRAW)
                .status(PaymentStatus.COMPLETED)
                .build();

        return paymentTransactionRepository.save(transaction);
    }
}

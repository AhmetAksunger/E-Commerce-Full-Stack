package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.withdraw.WithdrawRevenueRequest;
import com.ahmetaksunger.ecommerce.dto.response.WithdrawSuccessResponse;
import com.ahmetaksunger.ecommerce.exception.InsufficientRevenueException;
import com.ahmetaksunger.ecommerce.exception.notallowed.EntityOwnershipException;
import com.ahmetaksunger.ecommerce.exception.notfound.PaymentDetailNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentStatus;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentTransaction;
import com.ahmetaksunger.ecommerce.model.transaction.TransactionType;
import com.ahmetaksunger.ecommerce.repository.PaymentDetailRepository;
import com.ahmetaksunger.ecommerce.repository.PaymentTransactionRepository;
import com.ahmetaksunger.ecommerce.repository.SellerRepository;
import com.ahmetaksunger.ecommerce.service.factory.PaymentTransactionFactory;
import com.ahmetaksunger.ecommerce.service.rules.PaymentDetailRules;
import com.ahmetaksunger.ecommerce.service.rules.WithdrawRules;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SellerManager implements SellerService {

    private final SellerRepository sellerRepository;
    private final PaymentDetailRepository paymentDetailRepository;
    private final PaymentDetailRules paymentDetailRules;
    private final WithdrawRules withdrawRules;
    private final MapperService mapperService;
    private final PaymentTransactionRepository paymentTransactionRepository;

    /**
     * Updates the seller's total revenue by the amount and increment/decrement specified.
     *
     * @param seller      {@link Seller}
     * @param amount      Amount to increment/decrement by
     * @param isIncrement increment when is true, decrement when is false
     */
    @Override
    public void updateTotalRevenue(Seller seller, BigDecimal amount, boolean isIncrement) {
        if (isIncrement) {
            seller.incrementTotalRevenue(amount);
        } else {
            seller.decrementTotalRevenue(amount);
        }

        sellerRepository.save(seller);
    }

    /**
     * A method for {@link Seller}s to withdraw money to their bank accounts.
     * <p> - Checks if the payment detail entered belongs to the seller.</p>
     * <p> - Checks if the withdrawal amount is valid (Specified on the application.properties).</p>
     * <p> - Checks if the seller has enough revenue to withdraw.</p>
     * Then decrements the seller's revenue by the withdrawn amount
     * At the end, creates a {@link PaymentTransaction} to log the withdrawal operation.
     *
     * @param withdrawRevenueRequest {@link WithdrawRevenueRequest}
     * @param loggedInUser           {@link Seller}
     * @return {@link WithdrawSuccessResponse}
     * @see WithdrawRules
     * @see #updateTotalRevenue(Seller, BigDecimal, boolean)
     */
    @Transactional(dontRollbackOn = {InsufficientRevenueException.class, EntityOwnershipException.class})
    @Override
    public WithdrawSuccessResponse withdraw(WithdrawRevenueRequest withdrawRevenueRequest, User loggedInUser) {

        final Seller seller = (Seller) loggedInUser;
        final PaymentDetail paymentDetail = paymentDetailRepository.findById(withdrawRevenueRequest.getPaymentDetailId())
                .orElseThrow(PaymentDetailNotFoundException::new);

        //Rules
        withdrawRules.checkIfWithdrawAmountValid(withdrawRevenueRequest.getWithdrawAmount());
        try {
            paymentDetailRules.verifyEntityBelongsToUser(paymentDetail, seller);
            withdrawRules.checkIfSellerHasEnoughRevenueToWithdraw(seller, withdrawRevenueRequest.getWithdrawAmount());
        } catch (InsufficientRevenueException | EntityOwnershipException exception) {

            var transaction = PaymentTransactionFactory.create(TransactionType.WITHDRAW,
                    PaymentStatus.FAILED,
                    seller, withdrawRevenueRequest.getWithdrawAmount(),
                    paymentDetail, exception.getMessage());

            paymentTransactionRepository.save(transaction);

            throw exception;
        }

        // Simulating payment operations

        // Decrement the withdrawn amount from the seller's revenue
        this.updateTotalRevenue(seller, withdrawRevenueRequest.getWithdrawAmount(), false);

        //Withdraw Transaction
        var transaction = PaymentTransactionFactory.create(TransactionType.WITHDRAW,
                PaymentStatus.COMPLETED, seller,
                withdrawRevenueRequest.getWithdrawAmount(),
                paymentDetail, null);

        return mapperService.forResponse()
                .map(paymentTransactionRepository.save(transaction), WithdrawSuccessResponse.class);
    }
}

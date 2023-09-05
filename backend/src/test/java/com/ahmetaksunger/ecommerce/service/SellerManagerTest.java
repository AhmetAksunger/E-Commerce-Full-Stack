package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.withdraw.WithdrawRevenueRequest;
import com.ahmetaksunger.ecommerce.dto.response.SellerVM;
import com.ahmetaksunger.ecommerce.dto.response.WithdrawSuccessResponse;
import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;
import com.ahmetaksunger.ecommerce.exception.InsufficientRevenueException;
import com.ahmetaksunger.ecommerce.exception.notallowed.EntityOwnershipException;
import com.ahmetaksunger.ecommerce.exception.notfound.PaymentDetailNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperManager;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentStatus;
import com.ahmetaksunger.ecommerce.model.transaction.PaymentTransaction;
import com.ahmetaksunger.ecommerce.model.transaction.TransactionType;
import com.ahmetaksunger.ecommerce.repository.PaymentDetailRepository;
import com.ahmetaksunger.ecommerce.repository.PaymentTransactionRepository;
import com.ahmetaksunger.ecommerce.repository.SellerRepository;
import com.ahmetaksunger.ecommerce.service.rules.PaymentDetailRules;
import com.ahmetaksunger.ecommerce.service.rules.WithdrawRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

class SellerManagerTest {

    private SellerManager sellerManager;
    private SellerRepository sellerRepository;
    private PaymentDetailRepository paymentDetailRepository;
    private PaymentDetailRules paymentDetailRules;
    private WithdrawRules withdrawRules;
    private PaymentTransactionRepository paymentTransactionRepository;
    private MapperService mapperService = new MapperManager(new ModelMapper());
    ;

    @BeforeEach
    void setUp() {
        sellerRepository = Mockito.mock(SellerRepository.class);
        paymentDetailRepository = Mockito.mock(PaymentDetailRepository.class);
        paymentDetailRules = Mockito.mock(PaymentDetailRules.class);
        withdrawRules = Mockito.mock(WithdrawRules.class);
        paymentTransactionRepository = Mockito.mock(PaymentTransactionRepository.class);
        sellerManager = new SellerManager(sellerRepository, paymentDetailRepository, paymentDetailRules, withdrawRules, mapperService, paymentTransactionRepository);
    }

    @DisplayName("When withdraw method is called with a valid request, " +
            "it should return a valid withdraw success response")
    @Test
    void whenWithdrawCalledWithValidRequest_itShouldReturnValidWithdrawSuccessResponse() {

        Seller seller = Seller.builder()
                .id(1L)
                .email("email")
                .password("password")
                .companyName("company")
                .contactNumber("contactNumber")
                .logo("logo")
                .build();

        WithdrawRevenueRequest request = WithdrawRevenueRequest.builder()
                .withdrawAmount(BigDecimal.valueOf(120))
                .paymentDetailId(1L)
                .build();

        PaymentDetail paymentDetail = PaymentDetail.builder()
                .creditCardNumber("1234123412341234")
                .cvv("123")
                .expirationDate("03/26")
                .user(seller)
                .build();

        PaymentTransaction transaction = PaymentTransaction.builder()
                .seller(seller)
                .amount(request.getWithdrawAmount())
                .paymentDetail(paymentDetail)
                .transactionType(TransactionType.WITHDRAW)
                .status(PaymentStatus.COMPLETED)
                .build();

        WithdrawSuccessResponse successResponse = WithdrawSuccessResponse.builder()
                .seller(SellerVM.builder()
                        .sellerId(seller.getId())
                        .companyName(seller.getCompanyName())
                        .contactNumber(seller.getContactNumber())
                        .logo(seller.getLogo())
                        .build())
                .amount(request.getWithdrawAmount())
                .build();

        Mockito.when(paymentDetailRepository.findById(1L)).thenReturn(Optional.of(paymentDetail));
        Mockito.when(paymentTransactionRepository.save(transaction)).thenReturn(transaction);
        WithdrawSuccessResponse result = sellerManager.withdraw(request, seller);

        Mockito.when(paymentDetailRules.verifyEntityBelongsToUser(paymentDetail,seller)).thenReturn(paymentDetailRules);
        Mockito.doNothing().when(withdrawRules).checkIfWithdrawAmountValid(request.getWithdrawAmount());
        Mockito.doNothing().when(withdrawRules).checkIfSellerHasEnoughRevenueToWithdraw(seller, request.getWithdrawAmount());

        Assertions.assertEquals(result, successResponse);
        Mockito.verify(paymentDetailRules).verifyEntityBelongsToUser(paymentDetail, seller);
        Mockito.verify(withdrawRules).checkIfSellerHasEnoughRevenueToWithdraw(seller, request.getWithdrawAmount());
        Mockito.verify(withdrawRules).checkIfWithdrawAmountValid(request.getWithdrawAmount());
        Mockito.verify(paymentDetailRepository).findById(1L);

    }

    @DisplayName("When withdraw is called with a non existing payment detail id," +
            "it should throw PaymentDetailNotFoundException")
    @Test
    void whenWithdrawCalledWithNonExistingPaymentDetailId_itShouldThrowPaymentDetailNotFoundException() {

        WithdrawRevenueRequest request = WithdrawRevenueRequest.builder()
                .withdrawAmount(BigDecimal.valueOf(120))
                .paymentDetailId(1L)
                .build();

        Mockito.when(paymentDetailRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(PaymentDetailNotFoundException.class, () -> sellerManager.withdraw(request, new Seller()));
        Mockito.verifyNoInteractions(withdrawRules);
        Mockito.verifyNoInteractions(paymentTransactionRepository);
    }

    @DisplayName("When the seller doesnt have enough revenue, it should throw InsufficientRevenueException" +
            " and save the failed payment transaction")
    @Test
    void whenSellerDoesntHaveEnoughRevenue_itShouldThrowInsufficientRevenueException_andSaveFailedPaymentTransaction() {

        WithdrawRevenueRequest request = WithdrawRevenueRequest.builder()
                .withdrawAmount(BigDecimal.valueOf(120))
                .paymentDetailId(1L)
                .build();

        Seller seller = Mockito.mock(Seller.class);
        PaymentDetail paymentDetail = Mockito.mock(PaymentDetail.class);

        final PaymentTransaction transaction = PaymentTransaction.builder()
                .seller(seller)
                .amount(request.getWithdrawAmount())
                .paymentDetail(paymentDetail)
                .transactionType(TransactionType.WITHDRAW)
                .status(PaymentStatus.FAILED)
                .failureReason(ExceptionMessages.INSUFF_REVENUE.message())
                .build();

        Mockito.when(paymentDetailRepository.findById(request.getPaymentDetailId())).thenReturn(Optional.of(paymentDetail));
        Mockito.doNothing().when(withdrawRules).checkIfWithdrawAmountValid(request.getWithdrawAmount());

        Mockito.doThrow(new InsufficientRevenueException()).doNothing().
                when(withdrawRules).checkIfSellerHasEnoughRevenueToWithdraw(seller, request.getWithdrawAmount());

        Assertions.assertThrows(InsufficientRevenueException.class, () -> sellerManager.withdraw(request, seller));

        Mockito.verify(paymentTransactionRepository, Mockito.times(1)).save(transaction);
    }

    @DisplayName("When the payment detail doesnt belong to the seller, it should throw PaymentDetailOwnershipException" +
            " and save the failed payment transaction")
    @Test
    void whenPaymentDetailDoesntBelongSeller_itShouldThrowPaymentDetailOwnershipException_andSaveFailedPaymentTransaction() {

        WithdrawRevenueRequest request = WithdrawRevenueRequest.builder()
                .withdrawAmount(BigDecimal.valueOf(120))
                .paymentDetailId(1L)
                .build();

        Seller seller = Mockito.mock(Seller.class);
        PaymentDetail paymentDetail = Mockito.mock(PaymentDetail.class);

        final PaymentTransaction transaction = PaymentTransaction.builder()
                .seller(seller)
                .amount(request.getWithdrawAmount())
                .paymentDetail(paymentDetail)
                .transactionType(TransactionType.WITHDRAW)
                .status(PaymentStatus.FAILED)
                .failureReason(ExceptionMessages.UNAUTHORIZED.message())
                .build();

        Mockito.when(paymentDetailRepository.findById(request.getPaymentDetailId())).thenReturn(Optional.of(paymentDetail));
        Mockito.doNothing().when(withdrawRules).checkIfWithdrawAmountValid(request.getWithdrawAmount());
        Mockito.doThrow(new EntityOwnershipException())
                .when(paymentDetailRules).verifyEntityBelongsToUser(paymentDetail,seller);

        Assertions.assertThrows(EntityOwnershipException.class,
                () -> sellerManager.withdraw(request, seller));
        Mockito.verify(paymentTransactionRepository, Mockito.times(1)).save(transaction);
    }

}
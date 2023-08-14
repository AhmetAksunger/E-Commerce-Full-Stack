package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.withdraw.WithdrawRevenueRequest;
import com.ahmetaksunger.ecommerce.dto.response.SellerVM;
import com.ahmetaksunger.ecommerce.dto.response.WithdrawSuccessResponse;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.PaymentDetailNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperManager;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.transaction.WithdrawTransaction;
import com.ahmetaksunger.ecommerce.repository.PaymentDetailRepository;
import com.ahmetaksunger.ecommerce.repository.SellerRepository;
import com.ahmetaksunger.ecommerce.repository.WithdrawTransactionRepository;
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
    private WithdrawTransactionRepository withdrawTransactionRepository;
    private MapperService mapperService = new MapperManager(new ModelMapper());;

    @BeforeEach
    void setUp() {
        sellerRepository = Mockito.mock(SellerRepository.class);
        paymentDetailRepository = Mockito.mock(PaymentDetailRepository.class);
        paymentDetailRules = Mockito.mock(PaymentDetailRules.class);
        withdrawRules = Mockito.mock(WithdrawRules.class);
        withdrawTransactionRepository = Mockito.mock(WithdrawTransactionRepository.class);
        sellerManager = new SellerManager(sellerRepository, paymentDetailRepository, paymentDetailRules, withdrawRules, withdrawTransactionRepository, mapperService);
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

        WithdrawTransaction transaction = WithdrawTransaction.builder()
                .seller(seller)
                .amount(request.getWithdrawAmount())
                .paymentDetail(paymentDetail)
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
        Mockito.when(withdrawTransactionRepository.save(transaction)).thenReturn(transaction);
        WithdrawSuccessResponse result = sellerManager.withdraw(request, seller);

        Assertions.assertEquals(result, successResponse);
        Mockito.verify(paymentDetailRules).verifyPaymentDetailBelongsToUser(paymentDetail, seller, UnauthorizedException.class);
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

        Assertions.assertThrows(PaymentDetailNotFoundException.class,() -> sellerManager.withdraw(request,new Seller()));
        Mockito.verifyNoInteractions(withdrawRules);
        Mockito.verifyNoInteractions(withdrawTransactionRepository);
    }

}
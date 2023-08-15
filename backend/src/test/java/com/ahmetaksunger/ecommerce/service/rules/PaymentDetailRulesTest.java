package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.PaymentDetailDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.Address;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.AddressRepository;
import com.ahmetaksunger.ecommerce.repository.PaymentDetailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDetailRulesTest {

    private PaymentDetailRules paymentDetailRules;
    private PaymentDetailRepository paymentDetailRepository;
    @BeforeEach
    void setUp() {
        paymentDetailRepository = Mockito.mock(PaymentDetailRepository.class);
        paymentDetailRules = new PaymentDetailRules(paymentDetailRepository);
    }

    @DisplayName("When verifyPaymentDetailBelongsToUser is called with a payment detail that doesn't belong to user," +
            "it should throw an Unauthorized exception")
    @Test
    void when_verifyPaymentDetailBelongsToUser_calledWithPaymentDetailThatDoesntBelongToUser_itShouldThrowUnauthorizedException() {

        User user1 = User.builder()
                .id(1L)
                .email("test")
                .password("password")
                .build();

        User user2 = User.builder()
                .id(2L)
                .email("test")
                .password("password")
                .build();

        PaymentDetail paymentDetail = PaymentDetail.builder()
                .id(1L)
                .user(user1)
                .build();

        Assertions.assertThrows(UnauthorizedException.class,() -> paymentDetailRules.verifyPaymentDetailBelongsToUser(paymentDetail,user2, UnauthorizedException.class));

    }

    @Test
    void whenCheckIfCanDeleteCalledWithUserThatCantDelete_itShouldThrowPaymentDetailDeletionNotAllowedException(){
        User user1 = User.builder()
              .id(1L)
              .email("test")
              .password("password")
              .build();

        User user2 = User.builder()
              .id(2L)
              .email("test")
              .password("password")
              .build();

        PaymentDetail paymentDetail = PaymentDetail.builder()
              .id(1L)
              .user(user1)
              .build();

        Mockito.when(paymentDetailRepository.findById(1L)).thenReturn(Optional.of(paymentDetail));

        Assertions.assertThrows(PaymentDetailDeletionNotAllowedException.class,() -> paymentDetailRules.checkIfCanDelete(paymentDetail.getId(),user2));
    }

}
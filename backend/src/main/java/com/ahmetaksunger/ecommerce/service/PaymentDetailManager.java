package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.payment.CreatePaymentDetailRequest;
import com.ahmetaksunger.ecommerce.dto.response.PaymentDetailVM;
import com.ahmetaksunger.ecommerce.exception.notfound.PaymentDetailNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.EntityStatus;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.PaymentDetailRepository;
import com.ahmetaksunger.ecommerce.service.rules.BaseRules;
import com.ahmetaksunger.ecommerce.service.rules.PaymentDetailRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentDetailManager implements PaymentDetailService {

    private final MapperService mapperService;
    private final PaymentDetailRepository paymentDetailRepository;
    private final PaymentDetailRules paymentDetailRules;

    @Override
    public PaymentDetailVM create(CreatePaymentDetailRequest createPaymentDetailRequest, User user) {

        PaymentDetail paymentDetail = mapperService.forRequest().map(createPaymentDetailRequest, PaymentDetail.class);
        paymentDetail.setUser(user);

        return mapperService.forResponse().map(paymentDetailRepository.save(paymentDetail), PaymentDetailVM.class);
    }

    @Override
    public void delete(long paymentDetailId, User user) {

        PaymentDetail paymentDetail = paymentDetailRepository
                .findById(paymentDetailId).orElseThrow(PaymentDetailNotFoundException::new);

        // Rules
        paymentDetailRules.checkIfCanDelete(paymentDetail, user);

        paymentDetail.setStatus(EntityStatus.INACTIVE);
        paymentDetailRepository.save(paymentDetail);
    }

    @Override
    public List<PaymentDetailVM> getPaymentDetailsByUserId(long userId, User loggedInUser) {
        //Rules
        BaseRules.checkIfIdsNotMatch(userId, loggedInUser);

        List<PaymentDetail> paymentDetails = paymentDetailRepository.getActivePaymentDetailsByUserId(userId);

        return paymentDetails.stream()
                .map(paymentDetail -> mapperService.forResponse()
                        .map(paymentDetail, PaymentDetailVM.class))
                .toList();
    }
}

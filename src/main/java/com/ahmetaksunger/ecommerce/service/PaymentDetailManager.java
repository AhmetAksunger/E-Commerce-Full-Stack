package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.payment.CreatePaymentDetailRequest;
import com.ahmetaksunger.ecommerce.dto.response.PaymentDetailVM;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.AddressNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.model.UserType;
import com.ahmetaksunger.ecommerce.repository.AddressRepository;
import com.ahmetaksunger.ecommerce.repository.PaymentDetailRepository;
import com.ahmetaksunger.ecommerce.service.rules.GeneralRules;
import com.ahmetaksunger.ecommerce.service.rules.PaymentDetailRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentDetailManager implements PaymentDetailService{

    private final MapperService mapperService;
    private final PaymentDetailRepository paymentDetailRepository;
    private final PaymentDetailRules paymentDetailRules;
    private final AddressRepository addressRepository;
    @Override
    public PaymentDetailVM create(CreatePaymentDetailRequest createPaymentDetailRequest, User user) {

        PaymentDetail paymentDetail = mapperService.forRequest().map(createPaymentDetailRequest, PaymentDetail.class);
        if(user.getUserType().equals(UserType.SELLER)){
            //Rules
            paymentDetailRules.checkIfAddressBelongsToUser(createPaymentDetailRequest.getBillingAddressId(),user);
            paymentDetail.setAddress(addressRepository.findById(createPaymentDetailRequest.getBillingAddressId()).orElseThrow(AddressNotFoundException::new));
        }else{
            paymentDetail.setAddress(null);
            paymentDetail.setUser(user);
        }
        return mapperService.forResponse().map(paymentDetailRepository.save(paymentDetail),PaymentDetailVM.class);
    }

    @Override
    public void delete(long paymentDetailId,User user) {
        // Rules
        paymentDetailRules.checkIfCanDelete(paymentDetailId,user);

        paymentDetailRepository.deleteById(paymentDetailId);
    }

    @Override
    public List<PaymentDetailVM> getPaymentDetailsByUserId(long userId,User loggedInUser) {
        //Rules
        GeneralRules.checkIfIdsMatch(userId,loggedInUser);

        List<PaymentDetail> paymentDetails = paymentDetailRepository.getByUserId(userId);

        return paymentDetails.stream()
                .map(paymentDetail -> mapperService.forResponse()
                        .map(paymentDetail,PaymentDetailVM.class))
                .toList();
    }
}

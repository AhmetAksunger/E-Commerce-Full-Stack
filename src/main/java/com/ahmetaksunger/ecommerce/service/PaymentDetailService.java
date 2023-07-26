package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.payment.CreatePaymentDetailRequest;
import com.ahmetaksunger.ecommerce.dto.response.PaymentDetailVM;
import com.ahmetaksunger.ecommerce.model.User;

public interface PaymentDetailService {
    PaymentDetailVM create(CreatePaymentDetailRequest createPaymentDetailRequest, User user);

}

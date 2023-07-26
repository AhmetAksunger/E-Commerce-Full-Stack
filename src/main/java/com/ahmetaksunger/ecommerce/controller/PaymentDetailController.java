package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.CreatePaymentDetailRequest;
import com.ahmetaksunger.ecommerce.dto.response.PaymentDetailVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.PaymentDetailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment-details")
@SecurityRequirement(name = "bearerAuth")
public class PaymentDetailController {

    private final PaymentDetailService paymentDetailService;

    @PostMapping
    public ResponseEntity<PaymentDetailVM> create(@RequestBody CreatePaymentDetailRequest createPaymentDetailRequest, @CurrentUser User user){
        return ResponseEntity.ok(paymentDetailService.create(createPaymentDetailRequest,user));
    }
}

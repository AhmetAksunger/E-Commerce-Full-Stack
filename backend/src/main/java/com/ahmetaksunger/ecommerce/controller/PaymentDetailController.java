package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.payment.CreatePaymentDetailRequest;
import com.ahmetaksunger.ecommerce.dto.response.PaymentDetailVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.PaymentDetailService;
import com.ahmetaksunger.ecommerce.util.ECommerceResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment-details")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyAuthority('CUSTOMER','SELLER')")
public class PaymentDetailController {

    private final PaymentDetailService paymentDetailService;

    @PostMapping
    public ECommerceResponse<PaymentDetailVM> createPaymentDetail(@RequestBody @Validated CreatePaymentDetailRequest createPaymentDetailRequest, @CurrentUser User user){
        return ECommerceResponse.createdOf(paymentDetailService.create(createPaymentDetailRequest,user));
    }

    @DeleteMapping("/{paymentDetailId}")
    public ECommerceResponse<Void> deletePaymentDetail(@PathVariable long paymentDetailId, @CurrentUser User user){
        paymentDetailService.delete(paymentDetailId,user);
        return ECommerceResponse.SUCCESS;
    }

    @GetMapping()
    public ECommerceResponse<List<PaymentDetailVM>> getPaymentDetailsByUserId(@RequestParam(name = "userId",required = true) long userId,
                                                         @CurrentUser User loggedInUser){
        return ECommerceResponse.successOf(paymentDetailService.getPaymentDetailsByUserId(userId,loggedInUser));
    }

}

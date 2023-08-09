package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.payment.CreatePaymentDetailRequest;
import com.ahmetaksunger.ecommerce.dto.response.PaymentDetailVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.PaymentDetailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PaymentDetailVM> createPaymentDetail(@RequestBody @Validated CreatePaymentDetailRequest createPaymentDetailRequest, @CurrentUser User user){
        return new ResponseEntity<>(paymentDetailService.create(createPaymentDetailRequest,user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{paymentDetailId}")
    public void deletePaymentDetail(@PathVariable long paymentDetailId, @CurrentUser User user){
        paymentDetailService.delete(paymentDetailId,user);
    }

    @GetMapping()
    public ResponseEntity<List<PaymentDetailVM>> getPaymentDetailsByUserId(@RequestParam(name = "userId",required = true) long userId,
                                                         @CurrentUser User loggedInUser){
        return ResponseEntity.ok(paymentDetailService.getPaymentDetailsByUserId(userId,loggedInUser));
    }

}

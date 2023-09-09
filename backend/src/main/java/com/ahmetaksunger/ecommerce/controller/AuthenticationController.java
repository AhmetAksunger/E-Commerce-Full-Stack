package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.authentication.AuthenticationRequest;
import com.ahmetaksunger.ecommerce.dto.request.authentication.RegisterCustomerRequest;
import com.ahmetaksunger.ecommerce.dto.request.authentication.RegisterSellerRequest;
import com.ahmetaksunger.ecommerce.dto.response.AuthenticationResponse;
import com.ahmetaksunger.ecommerce.service.AuthenticationService;
import com.ahmetaksunger.ecommerce.util.ECommerceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ECommerceResponse<AuthenticationResponse> authenticate(@RequestBody @Validated AuthenticationRequest request){
        return ECommerceResponse.successOf(authenticationService.authenticate(request));
    }


    @PostMapping("/register/customer")
    public ECommerceResponse<AuthenticationResponse> registerCustomer(@RequestBody @Validated RegisterCustomerRequest request){
        return ECommerceResponse.createdOf(authenticationService.register(request));
    }

    @PostMapping("/register/seller")
    public ECommerceResponse<AuthenticationResponse> registerSeller(@RequestBody @Validated RegisterSellerRequest request){
        return ECommerceResponse.createdOf(authenticationService.register(request));
    }

}

package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.authentication.AuthenticationRequest;
import com.ahmetaksunger.ecommerce.dto.request.authentication.RegisterCustomerRequest;
import com.ahmetaksunger.ecommerce.dto.request.authentication.RegisterSellerRequest;
import com.ahmetaksunger.ecommerce.dto.response.AuthenticationResponse;
import com.ahmetaksunger.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Validated AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


    @PostMapping("/register/customer")
    public ResponseEntity<AuthenticationResponse> registerCustomer(@RequestBody @Validated RegisterCustomerRequest request){
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/register/seller")
    public ResponseEntity<AuthenticationResponse> registerSeller(@RequestBody @Validated RegisterSellerRequest request){
        return new ResponseEntity<>(authenticationService.register(request),HttpStatus.CREATED);
    }

}

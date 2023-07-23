package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.AuthenticationRequest;
import com.ahmetaksunger.ecommerce.dto.request.RegisterCustomerRequest;
import com.ahmetaksunger.ecommerce.dto.request.RegisterSellerRequest;
import com.ahmetaksunger.ecommerce.dto.response.AuthenticationResponse;
import com.ahmetaksunger.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register/customer")
    public ResponseEntity<AuthenticationResponse> registerCustomer(@RequestBody RegisterCustomerRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/register/seller")
    public ResponseEntity<AuthenticationResponse> registerSeller(@RequestBody RegisterSellerRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

}

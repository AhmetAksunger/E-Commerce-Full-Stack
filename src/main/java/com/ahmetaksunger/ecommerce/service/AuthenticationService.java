package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.AuthenticationRequest;
import com.ahmetaksunger.ecommerce.dto.request.RegisterCustomerRequest;
import com.ahmetaksunger.ecommerce.dto.request.RegisterRequest;
import com.ahmetaksunger.ecommerce.dto.request.RegisterSellerRequest;
import com.ahmetaksunger.ecommerce.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    AuthenticationResponse register(RegisterRequest registerRequest);
}

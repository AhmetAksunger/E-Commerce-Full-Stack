package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.authentication.AuthenticationRequest;
import com.ahmetaksunger.ecommerce.dto.request.authentication.RegisterRequest;
import com.ahmetaksunger.ecommerce.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    AuthenticationResponse register(RegisterRequest registerRequest);
}

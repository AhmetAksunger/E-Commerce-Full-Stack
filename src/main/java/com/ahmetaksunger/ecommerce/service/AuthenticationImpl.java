package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.AuthenticationRequest;
import com.ahmetaksunger.ecommerce.dto.request.RegisterCustomerRequest;
import com.ahmetaksunger.ecommerce.dto.request.RegisterRequest;
import com.ahmetaksunger.ecommerce.dto.request.RegisterSellerRequest;
import com.ahmetaksunger.ecommerce.dto.response.AuthenticationResponse;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.CustomerRepository;
import com.ahmetaksunger.ecommerce.repository.SellerRepository;
import com.ahmetaksunger.ecommerce.repository.UserRepository;
import com.ahmetaksunger.ecommerce.security.JwtService;
import com.ahmetaksunger.ecommerce.service.rules.AuthenticationRules;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class AuthenticationImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationRules authenticationRules;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        Claims claims = null;
        if(user.isCustomer()){
            Customer customer = customerRepository.findById(user.getId()).orElseThrow(()->new RuntimeException("handle this"));
            claims = generateClaims(customer);
        }else{
            Seller seller = sellerRepository.findById(user.getId()).orElseThrow(()->new RuntimeException("handle this"));
            claims = generateClaims(seller);
        }
        var jwt = jwtService.generateToken(claims,user);
        return AuthenticationResponse.builder().jwt(jwt).build();
    }

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {

        String jwt= null;
        if(registerRequest instanceof RegisterCustomerRequest){
            jwt = this.registerCustomer((RegisterCustomerRequest) registerRequest);
        }

        if(registerRequest instanceof RegisterSellerRequest){
            jwt = this.registerSeller((RegisterSellerRequest) registerRequest);
        }
        return AuthenticationResponse.builder().jwt(jwt).build();
    }

    private String registerSeller(RegisterSellerRequest registerSellerRequest) {
        Seller seller = new Seller(registerSellerRequest.getEmail(),
                passwordEncoder.encode(registerSellerRequest.getPassword()),
                new Date(),null, registerSellerRequest.getCompanyName(),
                registerSellerRequest.getContactNumber(),registerSellerRequest.getLogo());

        sellerRepository.save(seller);

        return jwtService.generateToken(generateClaims(seller),seller);
    }

    private String registerCustomer(RegisterCustomerRequest registerCustomerRequest){
        Customer customer = new Customer(registerCustomerRequest.getEmail(),
                passwordEncoder.encode(registerCustomerRequest.getPassword()),
                new Date(),null, registerCustomerRequest.getFullName(),
                registerCustomerRequest.getPhoneNumber());

        customerRepository.save(customer);


        return jwtService.generateToken(generateClaims(customer),customer);
    }

    private Claims generateClaims(Customer customer){
        Claims claims = Jwts.claims();
        claims.put("isCustomer",customer.isCustomer());
        claims.put("email",customer.getEmail());
        claims.put("fullName",customer.getFullName());

        return claims;
    }

    private Claims generateClaims(Seller seller){
        Claims claims = Jwts.claims();
        claims.put("isCustomer",seller.isCustomer());
        claims.put("email",seller.getEmail());
        claims.put("companyName",seller.getCompanyName());

        return claims;
    }
}

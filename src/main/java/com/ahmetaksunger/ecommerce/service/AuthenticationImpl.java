package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.authentication.AuthenticationRequest;
import com.ahmetaksunger.ecommerce.dto.request.authentication.RegisterCustomerRequest;
import com.ahmetaksunger.ecommerce.dto.request.authentication.RegisterRequest;
import com.ahmetaksunger.ecommerce.dto.request.authentication.RegisterSellerRequest;
import com.ahmetaksunger.ecommerce.dto.response.AuthenticationResponse;
import com.ahmetaksunger.ecommerce.dto.response.CustomerAuthenticationResponse;
import com.ahmetaksunger.ecommerce.dto.response.SellerAuthenticationResponse;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.UserType;
import com.ahmetaksunger.ecommerce.repository.CustomerRepository;
import com.ahmetaksunger.ecommerce.repository.SellerRepository;
import com.ahmetaksunger.ecommerce.repository.UserRepository;
import com.ahmetaksunger.ecommerce.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
    private final MapperService mapperService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        var jwt = jwtService.generateToken(user);
        if(user.getUserType().equals(UserType.CUSTOMER)){
            Customer customer = customerRepository.findById(user.getId()).orElseThrow();
            var response = mapperService.forResponse().map(customer,CustomerAuthenticationResponse.class);
            response.setJwt(jwt);
            return response;
        }else if(user.getUserType().equals(UserType.SELLER)){
            Seller seller = sellerRepository.findById(user.getId()).orElseThrow();
            return SellerAuthenticationResponse
                    .builder()
                    .companyName(seller.getCompanyName())
                    .contactNumber(seller.getContactNumber())
                    .logo(seller.getLogo())
                    .userType(seller.getUserType().name())
                    .jwt(jwt)
                    .build();
        }
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
        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }

    private String registerSeller(RegisterSellerRequest registerSellerRequest) {
        Seller seller = Seller.builder()
                        .email(registerSellerRequest.getEmail())
                                .password(passwordEncoder.encode(registerSellerRequest.getPassword()))
                                        .createdAt(new Date())
                                                .companyName(registerSellerRequest.getCompanyName())
                                                        .contactNumber(registerSellerRequest.getContactNumber())
                                                                .logo(registerSellerRequest.getLogo())
                                                                        .userType(UserType.SELLER)
                                                                                .build();
        sellerRepository.save(seller);

        return jwtService.generateToken(seller);
    }

    private String registerCustomer(RegisterCustomerRequest registerCustomerRequest){

        Customer customer = Customer.builder()
                        .email(registerCustomerRequest.getEmail())
                                .password(passwordEncoder.encode(registerCustomerRequest.getPassword()))
                                        .createdAt(new Date())
                                                .fullName(registerCustomerRequest.getFullName())
                                                        .phoneNumber(registerCustomerRequest.getPhoneNumber())
                                                                .userType(UserType.CUSTOMER)
                                                                        .build();
        Cart cart = Cart.builder()
                        .createdAt(new Date())
                                .customer(customer)
                                        .build();
        customer.setCart(cart);
        customerRepository.save(customer);
        return jwtService.generateToken(customer);
    }

}

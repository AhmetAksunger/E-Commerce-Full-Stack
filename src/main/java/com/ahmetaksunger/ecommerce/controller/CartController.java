package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<CartVM> createCart(@CurrentUser User loggedInUser){
        var c = cartService.create(loggedInUser);
        System.err.println(c.toString());
        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }
}

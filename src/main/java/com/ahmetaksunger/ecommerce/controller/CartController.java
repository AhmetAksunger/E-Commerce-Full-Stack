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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<CartVM> createCart(@CurrentUser User loggedInUser){
        return new ResponseEntity<>(cartService.create(loggedInUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public void deleteCart(@PathVariable long cartId, @CurrentUser User loggedInUser){
        cartService.delete(cartId,loggedInUser);
    }
}

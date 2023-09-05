package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @DeleteMapping("/{cartId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public void deleteCart(@PathVariable long cartId, @CurrentUser User loggedInUser) {
        cartService.delete(cartId, loggedInUser);
    }

    @GetMapping("/users/{customerId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<CartVM> getCartByCustomerId(@PathVariable long customerId, @CurrentUser User loggedInUser) {
        return ResponseEntity.ok(cartService.getCartByCustomerId(customerId, loggedInUser));
    }

}

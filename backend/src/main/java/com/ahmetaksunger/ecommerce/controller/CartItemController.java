package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.CartItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cartItems")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('CUSTOMER')")
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping()
    public ResponseEntity<CartVM> createCartItem(@RequestParam(name = "cartId",required = true) long cartId,
                                                    @RequestParam(name = "productId", required = true) long productId,
                                                    @RequestParam(name = "quantity",required = false,defaultValue = "0") int quantity,
                                                    @CurrentUser User loggedInUser){
        return new ResponseEntity<>(cartItemService.create(cartId,productId,quantity,loggedInUser),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<CartVM> deleteCartItem(@PathVariable long cartItemId, @CurrentUser User loggedInUser){
        return ResponseEntity.ok(cartItemService.delete(cartItemId,loggedInUser));
    }
}

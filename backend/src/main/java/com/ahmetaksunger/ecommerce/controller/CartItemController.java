package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.cartItem.CreateCartItemRequest;
import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.CartItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart-items")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('CUSTOMER')")
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartVM> createCartItem(@RequestBody @Valid CreateCartItemRequest createCartItemRequest,
                                                 @CurrentUser User loggedInUser) {

        return new ResponseEntity<>(cartItemService.create(createCartItemRequest,loggedInUser),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<CartVM> deleteCartItem(@PathVariable long cartItemId, @CurrentUser User loggedInUser) {
        return ResponseEntity.ok(cartItemService.delete(cartItemId, loggedInUser));
    }

    /**
     * Delets all the items in the cart, with the method {@link CartItemService#deleteAllByCartId(Long, User)}
     *
     * @param cartId the cart id
     * @param loggedInUser the logged-in user
     */
    @DeleteMapping("/cart/{cartId}/clear")
    public void clearCart(@PathVariable Long cartId, @CurrentUser User loggedInUser) {
        cartItemService.deleteAllByCartId(cartId, loggedInUser);
    }
}

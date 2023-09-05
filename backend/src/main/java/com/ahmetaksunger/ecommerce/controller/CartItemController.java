package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.cartitem.CreateCartItemRequest;
import com.ahmetaksunger.ecommerce.dto.request.cartitem.UpdateCartItemRequest;
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
     * Deletes all the items in the cart, with the method {@link CartItemService#deleteAllByCartId(Long, User)}
     *
     * @param cartId the cart id
     * @param loggedInUser the logged-in user
     */
    @DeleteMapping("/cart/{cartId}/clear")
    public void clearCart(@PathVariable Long cartId, @CurrentUser User loggedInUser) {
        cartItemService.deleteAllByCartId(cartId, loggedInUser);
    }

    /**
     * Updates the specified cart item
     *
     * @param cartItemId Cart item id
     * @param updateCartItemRequest {@link UpdateCartItemRequest}
     * @param loggedInUser Logged-in suer
     * @return Response Entity of CartVM
     */
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartVM> updateCartItem(@PathVariable Long cartItemId,
                                                 @RequestBody @Valid UpdateCartItemRequest updateCartItemRequest,
                                                 @CurrentUser User loggedInUser) {
        return ResponseEntity.ok(cartItemService.update(cartItemId, updateCartItemRequest, loggedInUser));
    }
}

package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.cartitem.CreateCartItemRequest;
import com.ahmetaksunger.ecommerce.dto.request.cartitem.UpdateCartItemRequest;
import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.model.User;

public interface CartItemService {
    CartVM create(CreateCartItemRequest createCartItemRequest,User loggedInUser);

    CartVM delete(long cartItemId, User loggedInUser);

    void deleteAllByCartId(Long cartId, User loggedInUser);

    CartVM update(Long cartItemId, UpdateCartItemRequest updateCartItemRequest, User loggedInUser);
}

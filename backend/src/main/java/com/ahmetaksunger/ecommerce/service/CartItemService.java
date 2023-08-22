package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.cartItem.CreateCartItemRequest;
import com.ahmetaksunger.ecommerce.dto.response.CartItemVM;
import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.model.User;

public interface CartItemService {
    CartVM create(CreateCartItemRequest createCartItemRequest,User loggedInUser);

    CartVM delete(long cartItemId, User loggedInUser);

    void deleteAllByCartId(Long cartId, User loggedInUser);
}

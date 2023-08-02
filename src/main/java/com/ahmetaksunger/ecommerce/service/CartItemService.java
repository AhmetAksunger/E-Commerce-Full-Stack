package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.response.CartItemVM;
import com.ahmetaksunger.ecommerce.model.User;

public interface CartItemService {
    CartItemVM create(long cartId, long productId, int quantity, User loggedInUser);
}

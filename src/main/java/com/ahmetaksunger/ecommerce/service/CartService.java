package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.model.User;

public interface CartService {
    CartVM create(User user);

    void delete(long cartId, User loggedInUser);
}

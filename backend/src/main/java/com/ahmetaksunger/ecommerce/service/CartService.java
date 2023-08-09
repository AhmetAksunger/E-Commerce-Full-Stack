package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.User;

import java.util.List;


public interface CartService {
    Cart create(User user);

    void delete(long cartId, User loggedInUser);

    Cart findByCustomerId(long id);

    CartVM getCartByCustomerId(long customerId,User loggedInUser);
}

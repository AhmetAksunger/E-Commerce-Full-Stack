package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.User;


public interface CartService {
    Cart create(User user);

    void delete(long cartId, User loggedInUser);

    Cart findByCustomerId(long id);
}

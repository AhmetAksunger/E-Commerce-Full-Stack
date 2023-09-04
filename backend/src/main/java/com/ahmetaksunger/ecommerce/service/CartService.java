package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.User;


public interface CartService {
    Cart create(User user);

    void delete(long cartId, User loggedInUser);

    CartVM getCartByCustomerId(long customerId,User loggedInUser);

    void activateCart(Cart cart);

    void deactivateCart(Cart cart);

}

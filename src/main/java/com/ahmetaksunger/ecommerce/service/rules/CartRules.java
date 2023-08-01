package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.CartDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartNotFoundException;
import com.ahmetaksunger.ecommerce.exception.UserAlreadyHasCartException;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartRules {

    private final CartRepository cartRepository;
    public void checkIfUserAlreadyHasACart(User user){
        if(cartRepository.countByCustomerId(user.getId()) > 0){
            throw new UserAlreadyHasCartException();
        }
    }

    public void checkIfCanDelete(long cartId,User user){
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        if(cart.getCustomer().getId() != user.getId()){
            throw new CartDeletionNotAllowedException();
        }
    }
}

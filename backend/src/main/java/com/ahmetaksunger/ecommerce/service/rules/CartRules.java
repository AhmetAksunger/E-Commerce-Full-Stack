package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.InsufficientProductQuantityException;
import com.ahmetaksunger.ecommerce.exception.InvalidCartException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.CartDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartNotFoundException;
import com.ahmetaksunger.ecommerce.exception.UserAlreadyHasCartException;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.CartStatus;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartRules {

    private final CartRepository cartRepository;

    @Deprecated
    public void checkIfUserAlreadyHasACart(User user){
        if(cartRepository.countByCustomerId(user.getId()) > 0){
            throw new UserAlreadyHasCartException();
        }
    }

    public void checkIfCanDelete(long cartId,User user){
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        this.verifyCartBelongsToUser(cart,user,CartDeletionNotAllowedException.class);
    }

    public void verifyCartBelongsToUser(Cart cart, User user,
                                            Class<? extends UnauthorizedException> exceptionClass){
        if(cart.getCustomer().getId() != user.getId()) {
            try {
                throw exceptionClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new UnauthorizedException(e.getMessage());
            }
        }
    }

    public void checkIfQuantityIsValid(int quantity, Product product){
        if(quantity > product.getQuantity()){
            throw new InsufficientProductQuantityException(product);
        }
    }

    public void checkIfCartActive(Cart cart){
        if(cart.getStatus().equals(CartStatus.INACTIVE)){
            throw new InvalidCartException();
        }
    }
}

package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.UserAlreadyHasCartException;
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
}

package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.CartRepository;
import com.ahmetaksunger.ecommerce.service.rules.CartRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CartManager implements CartService{

    private final CartRepository cartRepository;
    private final CartRules cartRules;
    private final MapperService mapperService;
    @Override
    public CartVM create(User user) {

        //Rules
        cartRules.checkIfUserAlreadyHasACart(user);

        Cart cart = Cart.builder()
                .customer((Customer) user)
                .createdAt(new Date())
                .build();

        return mapperService.forResponse().map(cartRepository.save(cart),CartVM.class);
    }
}

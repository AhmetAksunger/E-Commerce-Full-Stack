package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.response.CartItemVM;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartNotFoundException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.ProductNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.*;
import com.ahmetaksunger.ecommerce.repository.CartItemRepository;
import com.ahmetaksunger.ecommerce.repository.CartRepository;
import com.ahmetaksunger.ecommerce.repository.ProductRepository;
import com.ahmetaksunger.ecommerce.service.rules.CartRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CartItemManager implements CartItemService{

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRules cartRules;
    private final MapperService mapperService;
    @Override
    public CartItemVM create(long cartId, long productId, int quantity, User loggedInUser) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        //Rules
        cartRules.verifyCartBelongsToUser(cart,loggedInUser, UnauthorizedException.class);
        cartRules.checkIfQuantityIsValid(quantity,product);

        CartItem cartItem = CartItem
                .builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .createdAt(new Date())
                .build();
        return mapperService.forResponse().map(cartItemRepository.save(cartItem),CartItemVM.class);
    }

}

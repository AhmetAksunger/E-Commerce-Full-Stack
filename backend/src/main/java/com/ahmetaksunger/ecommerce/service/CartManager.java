package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.CartStatus;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.CartRepository;
import com.ahmetaksunger.ecommerce.service.rules.CartRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartManager implements CartService {

    private final CartRepository cartRepository;
    private final CartRules cartRules;
    private final MapperService mapperService;

    @Override
    public Cart create(User user) {

        Cart cart = Cart.builder()
                .customer((Customer) user)
                .build();

        return cartRepository.save(cart);
    }

    @Override
    public void delete(long cartId, User loggedInUser) {

        //Rules
        cartRules.checkIfCanDelete(cartId, loggedInUser);

        cartRepository.deleteById(cartId);
    }

    @Override
    public Cart findByCustomerId(long id) {
        return cartRepository.findByCustomerId(id).orElseThrow(CartNotFoundException::new);
    }

    @Override
    public CartVM getCartByCustomerId(long customerId, User loggedInUser) {

        Cart cart = cartRepository.findByCustomerId(customerId).orElseThrow(CartNotFoundException::new);

        //Rules
        cartRules.verifyCartBelongsToUser(cart, loggedInUser, UnauthorizedException.class);

        var response = mapperService.forResponse().map(cart, CartVM.class);
        response.setTotal(PriceCalculator.calculateTotal(cart));
        return response;
    }

    /**
     *  Activates the cart by setting the {@link CartStatus} ACTIVE
     *
     * @param cart Cart
     */
    @Override
    public void activateCart(Cart cart) {
        cart.setStatus(CartStatus.ACTIVE);
        cartRepository.save(cart);
    }

    /**
     * Deactivates the cart by setting the {@link CartStatus} INACTIVE
     * @param cart Cart
     */
    @Override
    public void deactivateCart(Cart cart) {
        cart.setStatus(CartStatus.INACTIVE);
        cartRepository.save(cart);
    }

}

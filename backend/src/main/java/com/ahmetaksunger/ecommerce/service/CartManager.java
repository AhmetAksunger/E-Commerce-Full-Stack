package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.converter.CartVMConverter;
import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartNotFoundException;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.*;
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
    private final CartVMConverter cartVMConverter;

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
    public CartVM getCartByCustomerId(long customerId, User loggedInUser) {

        Cart cart = cartRepository.findActiveCartsByCustomerId(customerId).orElseThrow(CartNotFoundException::new);

        //Rules
        cartRules.verifyCartBelongsToUser(cart, loggedInUser, UnauthorizedException.class);

        return cartVMConverter.convert(cart);
    }

    /**
     * Activates the cart by setting the {@link CartStatus} ACTIVE
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
     *
     * @param cart Cart
     */
    @Override
    public void deactivateCart(Cart cart) {
        cart.setStatus(CartStatus.INACTIVE);
        cartRepository.save(cart);
    }

    /**
     * Calculates the total product count, for the given cart
     *
     * @param cart Cart
     * @return Total product count
     */
    @Override
    public Integer calculateTotalProductCount(Cart cart) {
        return cart.getCartItems()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

}

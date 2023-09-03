package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.converter.CartVMConverter;
import com.ahmetaksunger.ecommerce.dto.request.cartItem.CreateCartItemRequest;
import com.ahmetaksunger.ecommerce.dto.request.cartItem.UpdateCartItemRequest;
import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.CartDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartItemNotFound;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.CartNotFoundException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.ProductNotFoundException;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.CartItem;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.CartItemRepository;
import com.ahmetaksunger.ecommerce.repository.CartRepository;
import com.ahmetaksunger.ecommerce.repository.ProductRepository;
import com.ahmetaksunger.ecommerce.service.rules.BaseRules;
import com.ahmetaksunger.ecommerce.service.rules.CartRules;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemManager implements CartItemService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRules cartRules;
    private final CartVMConverter cartVMConverter;

    @Override
    public CartVM create(final CreateCartItemRequest createCartItemRequest, User loggedInUser) {

        Cart cart = cartRepository.findById(createCartItemRequest.getCartId()).orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(createCartItemRequest.getProductId()).orElseThrow(ProductNotFoundException::new);

        //Rules
        BaseRules.checkIfIdsNotMatch(createCartItemRequest.getCartId(), loggedInUser);
        cartRules
                .checkIfQuantityIsValid(createCartItemRequest.getQuantity(), product)
                .checkIfCartActive(cart);

        Optional<CartItem> optionalCartItem = cartItemRepository
                .findByCartIdAndProductId(createCartItemRequest.getCartId(), createCartItemRequest.getProductId());

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();

            cartRules.checkIfQuantityIsValid(createCartItemRequest.getQuantity() + cartItem.getQuantity(), product);

            cartItem.setQuantity(cartItem.getQuantity() + createCartItemRequest.getQuantity());
            return cartVMConverter.convert(cartItemRepository.save(cartItem).getCart());
        }

        CartItem cartItem = CartItem
                .builder()
                .cart(cart)
                .product(product)
                .quantity(createCartItemRequest.getQuantity())
                .build();

        CartItem dbCartItem = cartItemRepository.save(cartItem);

        return cartVMConverter.convert(dbCartItem.getCart());
    }

    @Override
    public CartVM delete(long cartItemId, User loggedInUser) {

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(CartItemNotFound::new);

        //Rules
        cartRules.checkIfCanDelete(cartItem.getCart(), loggedInUser);

        cartItemRepository.deleteById(cartItemId);

        return cartVMConverter.convert(cartItem.getCart());
    }

    /**
     * Deletes all the items in the cart
     *
     * @param cartId       The cart id
     * @param loggedInUser The logged-in user
     */
    @Override
    @Transactional
    public void deleteAllByCartId(final Long cartId, final User loggedInUser) {

        //Rules
        cartRules.checkIfCanDelete(cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new), loggedInUser);

        cartItemRepository.deleteAllByCartId(cartId);
    }

    /**
     * Updates the cart-item with the given {@link UpdateCartItemRequest}
     *
     * @param cartItemId            cart item id
     * @param updateCartItemRequest {@link UpdateCartItemRequest}
     * @param loggedInUser          Logged-in user
     * @return {@link CartVM}
     */
    @Override
    public CartVM update(final Long cartItemId, final UpdateCartItemRequest updateCartItemRequest, final User loggedInUser) {

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(CartItemNotFound::new);

        //Rules
        cartRules.checkIfCanUpdate(cartItem.getCart(), loggedInUser)
                .checkIfQuantityIsValid(updateCartItemRequest.getQuantity(), cartItem.getProduct())
                .checkIfCartActive(cartItem.getCart());

        cartItem.setQuantity(updateCartItemRequest.getQuantity());

        CartItem updatedCartItem = cartItemRepository.save(cartItem);

        return cartVMConverter.convert(updatedCartItem.getCart());
    }
}

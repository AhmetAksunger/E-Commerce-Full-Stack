package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.InsufficientProductQuantityException;
import com.ahmetaksunger.ecommerce.exception.InvalidCartException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.CartDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.CartUpdateNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.EntityOwnershipException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.CartStatus;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartRules extends BaseRules<Cart> {

    /**
     * Checks if the quantity specified in the cart for a product is valid
     *
     * @param quantity THe quantity specified in the cart for a product
     * @param product  The product
     * @return this
     */
    public CartRules checkIfQuantityIsValid(Integer quantity, Product product) {
        if (quantity > product.getQuantity()) {
            throw new InsufficientProductQuantityException(product);
        }
        return this;
    }

    /**
     * Checks if the cart is active
     *
     * @param cart The cart
     * @return this
     */
    public CartRules checkIfCartActive(Cart cart) {
        if (cart.getStatus().equals(CartStatus.INACTIVE)) {
            throw new InvalidCartException();
        }
        return this;
    }

    /**
     * Throws an {@link CartUpdateNotAllowedException} if the entity id and user id
     * don't match.
     *
     * @param entity The entity
     * @param user   The user
     * @return this
     */
    @Override
    public CartRules checkIfCanUpdate(Cart entity, User user) {
        verifyEntityBelongsToUser(entity, user, CartUpdateNotAllowedException.class);
        return this;
    }

    /**
     * Throws an {@link CartDeletionNotAllowedException} if the entity id and user id
     * don't match.
     *
     * @param entity The entity
     * @param user   The user
     * @return this
     */
    @Override
    public CartRules checkIfCanDelete(Cart entity, User user) {
        verifyEntityBelongsToUser(entity, user, CartDeletionNotAllowedException.class);
        return this;
    }

    /**
     * Checks if the cart belongs to user,
     * if not throws the specified exception class
     *
     * @param entity         The entity
     * @param user           The user
     * @param exceptionClass The exception class to be thrown
     */
    @SneakyThrows
    @Override
    protected BaseRules<Cart> verifyEntityBelongsToUser(Cart entity, User user, Class<? extends UnauthorizedException> exceptionClass) {
        if (entity.getCustomer().getId() != user.getId()) {
            throw exceptionClass.getDeclaredConstructor().newInstance();
        }
        return this;
    }

    /**
     * Checks if the cart belongs to user,
     * if not throws the {@link EntityOwnershipException}
     *
     * @param entity THe entity
     * @param user   The user
     */
    @Override
    public BaseRules<Cart> verifyEntityBelongsToUser(Cart entity, User user) {
        if (entity.getCustomer().getId() != user.getId()) {
            throw new EntityOwnershipException();
        }
        return this;
    }
}

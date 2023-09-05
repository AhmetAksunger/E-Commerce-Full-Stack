package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.CartIsEmptyException;
import com.ahmetaksunger.ecommerce.exception.InsufficientProductQuantityException;
import com.ahmetaksunger.ecommerce.exception.notallowed.EntityOwnershipException;
import com.ahmetaksunger.ecommerce.exception.notallowed.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRules extends BaseRules<Order> {

    /**
     * Checks if any of the products in the cart
     * has invalid quantity.
     * If so throws {@link InsufficientProductQuantityException}
     *
     * @param cart The cart
     * @return this
     */
    public OrderRules checkInsufficientStock(Cart cart) {

        List<Product> productsWithInsufficientStock = cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getQuantity() > cartItem.getProduct().getQuantity())
                .map(CartItem::getProduct)
                .toList();

        if (!productsWithInsufficientStock.isEmpty()) {
            throw new InsufficientProductQuantityException(productsWithInsufficientStock);
        }

        return this;
    }

    /**
     * Checks if the cart is empty,
     * if so throws {@link CartIsEmptyException}
     *
     * @param cart The cart
     * @return this
     */
    public OrderRules checkIfCartIsEmpty(Cart cart) {
        if (cart.getCartItems().isEmpty()) {
            throw new CartIsEmptyException();
        }

        return this;
    }

    /**
     * Throws an {@link UnauthorizedException} if the customer id and user id don't match
     * (Since there's no usage of this method for the order entity,
     * there's no specific exception class to throw)
     *
     * @param entity The entity
     * @param user   The entity
     * @return this
     */
    @Override
    public BaseRules<Order> checkIfCanUpdate(Order entity, User user) {
        verifyEntityBelongsToUser(entity,user,UnauthorizedException.class);
        return this;
    }

    /**
     * Throws an {@link UnauthorizedException} if the customer id and user id don't match
     * (Since there's no usage of this method for the order entity, there's no specific exception class to throw)
     *
     * @param entity The entity
     * @param user   The entity
     * @return this
     */
    @Override
    public BaseRules<Order> checkIfCanDelete(Order entity, User user) {
        verifyEntityBelongsToUser(entity,user,UnauthorizedException.class);
        return this;
    }

    /**
     * Checks if the order belongs to user,
     * if not throws the specified exception class
     *
     * @param entity         The entity
     * @param user           The user
     * @param exceptionClass The exception class to be thrown
     */
    @SneakyThrows
    @Override
    protected BaseRules<Order> verifyEntityBelongsToUser(Order entity, User user, Class<? extends UnauthorizedException> exceptionClass) {
        if (entity.getCustomer().getId() != user.getId()) {
            throw exceptionClass.getDeclaredConstructor().newInstance();
        }
        return this;
    }

    /**
     * Checks if the cart belongs to user,
     * if not throws the {@link EntityOwnershipException}
     *
     * @param entity The entity
     * @param user   The user
     */
    @Override
    public BaseRules<Order> verifyEntityBelongsToUser(Order entity, User user) {
        if (entity.getCustomer().getId() != user.getId()) {
            throw new EntityOwnershipException();
        }
        return this;
    }
}

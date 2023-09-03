package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.InvalidRequestParamException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.EntityOwnershipException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.ProductDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.ProductUpdateNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRules extends BaseRules<Product> {

    private final List<String> validSortParams = List.of("asc", "desc");
    private final List<String> validOrderParams = List.of("name", "price", "createdAt", "updatedAt");

    /**
     * <p>Checks if the sort parameter given is valid</p>
     *
     * @param sort The given sort param
     * @see ProductRules#validSortParams
     */
    public ProductRules checkIfSortParamIsValid(String sort) {
        if (!this.validSortParams.contains(sort.toLowerCase())) {
            throw new InvalidRequestParamException("Invalid sort parameter",this.validSortParams);
        }
        return this;
    }

    /**
     * Checks if the given order parameter is valid
     *
     * @param order THe given order param
     * @see ProductRules#validOrderParams
     */
    public ProductRules checkIfOrderParamIsValid(String order) {
        if (!this.validOrderParams.contains(order)) {
            throw new InvalidRequestParamException("Invalid order parameter.", this.validOrderParams);
        }
        return this;
    }

    @Override
    public ProductRules checkIfCanUpdate(Product entity, User user) {
        verifyEntityBelongsToUser(entity, user, ProductUpdateNotAllowedException.class);
        return this;
    }

    @Override
    public ProductRules checkIfCanDelete(Product entity, User user) {
        verifyEntityBelongsToUser(entity, user, ProductDeletionNotAllowedException.class);
        return this;
    }

    /**
     * Checks if the product belongs to user,
     * if not throws the specified exception class
     *
     * @param entity         The entity
     * @param user           The user
     * @param exceptionClass The exception class to be thrown
     */
    @SneakyThrows
    @Override
    protected ProductRules verifyEntityBelongsToUser(Product entity, User user, Class<? extends UnauthorizedException> exceptionClass) {
        if (entity.getSeller().getId() != user.getId()) {
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
    public ProductRules verifyEntityBelongsToUser(Product entity, User user) {
        if (entity.getSeller().getId() != user.getId()) {
            throw new EntityOwnershipException();
        }
        return this;
    }
}

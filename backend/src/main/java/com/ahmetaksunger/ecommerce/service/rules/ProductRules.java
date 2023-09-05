package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.InvalidProductException;
import com.ahmetaksunger.ecommerce.exception.InvalidRequestParamException;
import com.ahmetaksunger.ecommerce.exception.notallowed.EntityOwnershipException;
import com.ahmetaksunger.ecommerce.exception.notallowed.ProductDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.notallowed.ProductUpdateNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.notallowed.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.EntityStatus;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.util.ECommerceSortingRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRules extends BaseRules<Product> {

    private final List<String> validOrderParams = List.of("name", "price", "createdAt", "updatedAt");

    /**
     * Checks if product is {@link EntityStatus#INACTIVE}.
     * If so, throws {@link InvalidProductException}
     * @param product THe product
     * @return this
     */
    public ProductRules checkIfProductActive(Product product){
        if (product.getStatus().equals(EntityStatus.INACTIVE)){
            throw new InvalidProductException();
        }
        return this;
    }

    /**
     *
     * <p>If sorting is null, returns the current instance</p>
     * <p>Else, converts the sort field of sorting to nullable optional
     * by using {@link Optional#ofNullable(Object)}</p>
     *
     * <p>Then checks if optional sort is in valid order params, if not throws {@link InvalidRequestParamException}</p>
     * @param sorting Optional Sorting
     * @return this
     */
    public ProductRules checkIfSortParamIsValid(Optional<ECommerceSortingRequest.Sorting> sorting) {

        if (sorting.isEmpty()) {
            return this;
        }

        Optional<String> optionalSort = Optional.ofNullable(sorting.get().getSort());

        optionalSort
                .filter(s -> !this.validOrderParams.contains(s))
                .ifPresent(s -> {
                    throw new InvalidRequestParamException("Invalid sort parameter", this.validOrderParams);
                });

        return this;
    }

    /**
     * Checks if the given order parameter is valid
     *
     * @param order THe given order param
     * @see ProductRules#validOrderParams
     */
    @Deprecated
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

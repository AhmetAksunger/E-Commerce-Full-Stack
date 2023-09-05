package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.notallowed.AddressDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.notallowed.AddressUpdateNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.notallowed.EntityOwnershipException;
import com.ahmetaksunger.ecommerce.exception.notallowed.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.Address;
import com.ahmetaksunger.ecommerce.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressRules extends BaseRules<Address> {

    /**
     * Throws an {@link AddressUpdateNotAllowedException} if the entity id and user id
     * don't match.
     *
     * @param address The entity
     * @param user    The user
     * @return this
     */
    @Override
    public AddressRules checkIfCanUpdate(Address address, User user) {
        verifyEntityBelongsToUser(address, user, AddressUpdateNotAllowedException.class);
        return this;
    }

    /**
     * Throws an {@link AddressDeletionNotAllowedException} if the entity id and user id
     * don't match.
     *
     * @param address The entity
     * @param user    The user
     * @return this
     */
    @Override
    public AddressRules checkIfCanDelete(Address address, User user) {
        verifyEntityBelongsToUser(address, user, AddressDeletionNotAllowedException.class);
        return this;
    }

    /**
     * Checks if Address belongs to user,
     * if not throws the specified exception class
     *
     * @param entity         The entity
     * @param user           The user
     * @param exceptionClass The exception class to be thrown
     */
    @SneakyThrows
    @Override
    protected AddressRules verifyEntityBelongsToUser(Address entity, User user, Class<? extends UnauthorizedException> exceptionClass) {
        if (entity.getUser().getId() != user.getId()) {
            throw exceptionClass.getDeclaredConstructor().newInstance();
        }
        return this;
    }

    /**
     * Checks if the Address belongs to user,
     * if not throws {@link EntityOwnershipException}
     *
     * @param entity THe entity
     * @param user   The user
     */
    @Override
    public AddressRules verifyEntityBelongsToUser(Address entity, User user) {
        if (entity.getUser().getId() != user.getId()) {
            throw new EntityOwnershipException();
        }
        return this;
    }

}

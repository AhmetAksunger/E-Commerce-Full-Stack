package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.AddressDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.AddressUpdateNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.EntityOwnershipException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.Address;
import com.ahmetaksunger.ecommerce.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressRules extends BaseRules<Address> {

    public AddressRules checkIfCanUpdate(Address address, User user) {
        verifyEntityBelongsToUser(address, user, AddressUpdateNotAllowedException.class);
        return this;
    }

    public AddressRules checkIfCanDelete(Address address, User user) {
        verifyEntityBelongsToUser(address, user, AddressDeletionNotAllowedException.class);
        return this;
    }

    /**
     * Checks if Address belongs to user,
     * if not throws the specified exception class
     * @param entity         The entity
     * @param user           The user
     * @param exceptionClass The exception class to be thrown
     */
    @SneakyThrows
    @Override
    protected void verifyEntityBelongsToUser(Address entity, User user, Class<? extends UnauthorizedException> exceptionClass) {
        if (entity.getUser().getId() != user.getId()) {
            throw exceptionClass.getDeclaredConstructor().newInstance();
        }
    }

    /**
     * Checks if the Address belongs to user,
     * if not throws {@link EntityOwnershipException}
     * @param entity THe entity
     * @param user The user
     */
    @Override
    public void verifyEntityBelongsToUser(Address entity, User user) {
        if (entity.getUser().getId() != user.getId()) {
            throw new EntityOwnershipException();
        }
    }

}

package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.AddressDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.AddressUpdateNotAllowedException;
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

    @SneakyThrows
    @Override
    protected void verifyEntityBelongsToUser(Address entity, User user, Class<? extends UnauthorizedException> exceptionClass) {
        if (entity.getUser().getId() != user.getId()) {
            throw exceptionClass.getDeclaredConstructor().newInstance();
        }
    }

}

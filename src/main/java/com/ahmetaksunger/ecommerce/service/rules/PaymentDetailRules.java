package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.AddressNotFoundException;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentDetailRules {

    private final AddressRepository addressRepository;
    public void checkIfAddressBelongsToUser(long addressId, User user){
        var address = addressRepository.findById(addressId).orElseThrow(()->new AddressNotFoundException());
        if(address.getUser().getId() != user.getId()){
            throw new UnauthorizedException("Address doesn't belong to user");
        }
    }
}

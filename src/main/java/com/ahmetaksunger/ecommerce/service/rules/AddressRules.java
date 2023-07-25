package com.ahmetaksunger.ecommerce.service.rules;

import org.springframework.stereotype.Component;

import com.ahmetaksunger.ecommerce.exception.AddressNotFoundException;
import com.ahmetaksunger.ecommerce.exception.AddressUpdateNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;
import com.ahmetaksunger.ecommerce.exception.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.Address;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressRules {

	private final AddressRepository addressRepository;
	
    public void checkIfIdsMatch(long id, User user){
        if(id != user.getId()){
            throw new UnauthorizedException(ExceptionMessages.UNAUTHORIZED.message());
        }
    }
        
    public void checkIfCanUpdate(long addressId,User user) {
    	
    	Address address = addressRepository.findById(addressId).orElseThrow(()-> new AddressNotFoundException());
    	if(address.getUser().getId() != user.getId()) {
    		throw new AddressUpdateNotAllowedException();
    	}
    }

}

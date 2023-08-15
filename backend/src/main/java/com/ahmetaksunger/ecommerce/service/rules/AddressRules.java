package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.AddressDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.AddressNotFoundException;
import org.springframework.stereotype.Component;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.AddressUpdateNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.Address;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressRules {

	private final AddressRepository addressRepository;
        
    public void checkIfCanUpdate(long addressId,User user) {

    	Address address = addressRepository.findById(addressId).orElseThrow(()-> new AddressNotFoundException());
    	this.verifyAddressBelongsToUser(address,user,AddressUpdateNotAllowedException.class);
    }

	public void checkIfCanDelete(long addressId, User user){

		Address address = addressRepository.findById(addressId).orElseThrow(()-> new AddressNotFoundException());
		this.verifyAddressBelongsToUser(address,user, AddressDeletionNotAllowedException.class);
	}
	// TODO: One user can have at most 3 addresses
	public void verifyAddressBelongsToUser(Address address, User user,
											Class<? extends UnauthorizedException> exceptionClass){
		if(address.getUser().getId() != user.getId()) {
			try {
				throw exceptionClass.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new UnauthorizedException(e.getMessage());
			}
		}
	}

}

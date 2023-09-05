package com.ahmetaksunger.ecommerce.exception.notfound;


import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class AddressNotFoundException extends NotFoundException {

	public AddressNotFoundException() {
		super(ExceptionMessages.ADDRESS_NOT_FOUND.message());
	}
	
}

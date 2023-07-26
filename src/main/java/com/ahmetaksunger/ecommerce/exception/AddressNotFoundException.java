package com.ahmetaksunger.ecommerce.exception;


public class AddressNotFoundException extends NotFoundException{

	public AddressNotFoundException() {
		super(ExceptionMessages.ADDRESS_NOT_FOUND.message());
	}
	
}

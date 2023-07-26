package com.ahmetaksunger.ecommerce.exception;

public class AddressUpdateNotAllowedException extends UnauthorizedException {
	public AddressUpdateNotAllowedException() {
		super(ExceptionMessages.ADDRESS_UPDATE_NOT_ALLOWED.message());
	}
}

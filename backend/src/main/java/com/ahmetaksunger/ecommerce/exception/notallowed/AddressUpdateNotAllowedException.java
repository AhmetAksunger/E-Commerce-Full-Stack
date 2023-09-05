package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class AddressUpdateNotAllowedException extends UnauthorizedException {
	public AddressUpdateNotAllowedException() {
		super(ExceptionMessages.ADDRESS_UPDATE_NOT_ALLOWED.message());
	}
}

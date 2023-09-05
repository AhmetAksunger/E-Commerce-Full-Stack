package com.ahmetaksunger.ecommerce.exception.notallowed;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class AddressUpdateNotAllowedException extends UnauthorizedException {
	@Serial
	private static final long serialVersionUID = 6218771383469005169L;

	public AddressUpdateNotAllowedException() {
		super(ExceptionMessages.ADDRESS_UPDATE_NOT_ALLOWED.message());
	}
}

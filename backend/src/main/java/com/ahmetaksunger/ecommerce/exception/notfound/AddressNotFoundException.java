package com.ahmetaksunger.ecommerce.exception.notfound;


import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class AddressNotFoundException extends NotFoundException {

	@Serial
	private static final long serialVersionUID = -869444000446876951L;

	public AddressNotFoundException() {
		super(ExceptionMessages.ADDRESS_NOT_FOUND.message());
	}
	
}

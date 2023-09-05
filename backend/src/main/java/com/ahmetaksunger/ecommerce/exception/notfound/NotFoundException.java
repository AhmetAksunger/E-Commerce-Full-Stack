package com.ahmetaksunger.ecommerce.exception.notfound;

import java.io.Serial;

public class NotFoundException extends RuntimeException{
	@Serial
	private static final long serialVersionUID = -6807257306469115579L;

	public NotFoundException(String message){
		super(message);
	}
}

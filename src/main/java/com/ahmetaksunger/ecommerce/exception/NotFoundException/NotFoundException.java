package com.ahmetaksunger.ecommerce.exception.NotFoundException;

public class NotFoundException extends RuntimeException{
	public NotFoundException(String message){
		super(message);
	}
}

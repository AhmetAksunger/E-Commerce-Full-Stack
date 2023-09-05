package com.ahmetaksunger.ecommerce.exception.notfound;

public class NotFoundException extends RuntimeException{
	public NotFoundException(String message){
		super(message);
	}
}

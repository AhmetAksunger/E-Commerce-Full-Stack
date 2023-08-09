package com.ahmetaksunger.ecommerce.exception.NotFoundException;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(){
        super(ExceptionMessages.CATEGORY_NOT_FOUND.message());
    }
}

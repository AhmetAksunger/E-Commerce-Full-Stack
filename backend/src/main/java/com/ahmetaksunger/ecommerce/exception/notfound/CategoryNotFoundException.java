package com.ahmetaksunger.ecommerce.exception.notfound;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(){
        super(ExceptionMessages.CATEGORY_NOT_FOUND.message());
    }
}

package com.ahmetaksunger.ecommerce.exception.notfound;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;

import java.io.Serial;

public class CategoryNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = 2660822724748166028L;

    public CategoryNotFoundException(){
        super(ExceptionMessages.CATEGORY_NOT_FOUND.message());
    }
}

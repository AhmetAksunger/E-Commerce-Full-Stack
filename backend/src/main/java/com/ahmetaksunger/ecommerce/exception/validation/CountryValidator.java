package com.ahmetaksunger.ecommerce.exception.validation;

import com.ahmetaksunger.ecommerce.model.Country;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CountryValidator implements ConstraintValidator<ValidCountry, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null){
            return true;
        }

        for (Country country:Country.values()) {
            if(value.equals(country.name()) || value.equals(country.value())){
                return true;
            }
        }
        return false;
    }
}

package com.ahmetaksunger.ecommerce.exception.customValidation;

import com.ahmetaksunger.ecommerce.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(userRepository.existsByEmail(email)){
            return false;
        }
        return true;
    }
}
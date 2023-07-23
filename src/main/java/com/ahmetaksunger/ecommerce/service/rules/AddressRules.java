package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;
import com.ahmetaksunger.ecommerce.exception.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.Country;
import com.ahmetaksunger.ecommerce.model.User;
import org.springframework.stereotype.Component;

@Component
public class AddressRules {

    public void checkAuthorization(long id, User user){
        if(id != user.getId()){
            throw new UnauthorizedException(ExceptionMessages.UNAUTHORIZED.message());
        }
    }

}

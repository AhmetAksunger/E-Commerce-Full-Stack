package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.ExceptionMessages;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.User;

public class GeneralRules {
    public static void checkIfIdsMatch(long id, User user){
        if(id != user.getId()){
            throw new UnauthorizedException(ExceptionMessages.UNAUTHORIZED.message());
        }
    }
}

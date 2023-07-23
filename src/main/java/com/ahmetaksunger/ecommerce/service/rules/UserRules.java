package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.AccountDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRules {

    public void canDeleteAccount(long userIdToBeDeleted, User user){
        if(userIdToBeDeleted != user.getUserId()){
            throw new AccountDeletionNotAllowedException();
        }
    };

}

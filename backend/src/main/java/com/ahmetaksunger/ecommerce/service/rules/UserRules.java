package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.notallowed.AccountDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRules {

    public void checkIfCanDelete(long userIdToBeDeleted, User user){
        if(userIdToBeDeleted != user.getId()){
            throw new AccountDeletionNotAllowedException();
        }
    };

}

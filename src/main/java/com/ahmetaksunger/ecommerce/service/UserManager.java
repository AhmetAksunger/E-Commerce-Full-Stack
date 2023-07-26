package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.UserRepository;
import com.ahmetaksunger.ecommerce.service.rules.UserRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final UserRules userRules;
    @Override
    public void delete(long userIdToBeDeleted, User user) {

        //Rules
        userRules.checkIfCanDelete(userIdToBeDeleted,user);

        userRepository.deleteById(userIdToBeDeleted);
    }
}

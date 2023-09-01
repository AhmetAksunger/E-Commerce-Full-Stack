package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.User;
import lombok.SneakyThrows;

public abstract class BaseRules<T> {

    public abstract BaseRules<T> checkIfCanUpdate(T entity, User user);

    public abstract BaseRules<T> checkIfCanDelete(T entity, User user);

    /**
     * Checks if the entity belongs to user
     *
     * @param entity         The entity
     * @param user           The user
     * @param exceptionClass The exception class to be thrown
     */
    protected abstract void verifyEntityBelongsToUser(T entity, User user,
                                                      Class<? extends UnauthorizedException> exceptionClass);

    /**
     * Checks if the entity belongs to user
     *
     * @param entity THe entity
     * @param user The user
     */
    public abstract void verifyEntityBelongsToUser(T entity, User user);
    /**
     * Checks if ids match, if not throws Unauthorized Exception
     * @param id The id
     * @param user The user
     */
    public static void checkIfIdsNotMatch(Long id, User user) {
        if(user.getId() != id){
            throw new UnauthorizedException();
        }
    }
}

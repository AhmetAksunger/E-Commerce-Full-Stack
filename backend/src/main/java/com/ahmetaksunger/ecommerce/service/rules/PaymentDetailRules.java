package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.EntityOwnershipException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.PaymentDetailDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.User;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class PaymentDetailRules extends BaseRules<PaymentDetail> {

    @Override
    public BaseRules<PaymentDetail> checkIfCanUpdate(PaymentDetail entity, User user) {
        return null;
    }

    /**
     * Throws a {@link PaymentDetailDeletionNotAllowedException} if
     * the entity doesn't belong to user
     *
     * @param entity The entity
     * @param user   The user
     * @return this
     */
    @Override
    public BaseRules<PaymentDetail> checkIfCanDelete(PaymentDetail entity, User user) {
        verifyEntityBelongsToUser(entity, user, PaymentDetailDeletionNotAllowedException.class);
        return this;
    }

    /**
     * Checks if the payment detail belongs to user,
     * if not throws the specified exception class
     *
     * @param entity         The entity
     * @param user           The user
     * @param exceptionClass The exception class to be thrown
     */
    @SneakyThrows
    @Override
    protected BaseRules<PaymentDetail> verifyEntityBelongsToUser(PaymentDetail entity, User user, Class<? extends UnauthorizedException> exceptionClass) {
        if (entity.getUser().getId() != user.getId()) {
            throw exceptionClass.getDeclaredConstructor().newInstance();
        }
        return this;
    }

    /**
     * Checks if the cart belongs to user,
     * if not throws the {@link EntityOwnershipException}
     *
     * @param entity THe entity
     * @param user   The user
     */
    @Override
    public BaseRules<PaymentDetail> verifyEntityBelongsToUser(PaymentDetail entity, User user) {
        if (entity.getUser().getId() != user.getId()) {
            throw new EntityOwnershipException();
        }
        return this;
    }
}

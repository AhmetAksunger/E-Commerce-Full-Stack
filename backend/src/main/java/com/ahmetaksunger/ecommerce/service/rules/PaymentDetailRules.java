package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.PaymentDetailDeletionNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.PaymentDetailNotFoundException;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.PaymentDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentDetailRules {

    private final PaymentDetailRepository paymentDetailRepository;

    public void checkIfCanDelete(long paymentDetailId, User user) {
        var paymentDetail = paymentDetailRepository.findById(paymentDetailId).orElseThrow(() -> new PaymentDetailNotFoundException());
        this.verifyPaymentDetailBelongsToUser(paymentDetail, user, PaymentDetailDeletionNotAllowedException.class);
    }

    public void verifyPaymentDetailBelongsToUser(PaymentDetail paymentDetail, User user,
                                                 Class<? extends UnauthorizedException> exceptionClass) {
        if (paymentDetail.getUser().getId() != user.getId()) {
            try {
                throw exceptionClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new UnauthorizedException(e.getMessage());
            }
        }
    }

}

package com.ahmetaksunger.ecommerce.model.transaction;

import com.ahmetaksunger.ecommerce.model.BaseEntity;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.Seller;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.math.BigDecimal;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class PaymentTransaction extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -8101522232709045911L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Seller seller;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @ManyToOne
    private PaymentDetail paymentDetail;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String failureReason;

    public PaymentTransaction(Customer customer, BigDecimal amount, PaymentDetail paymentDetail, TransactionType transactionType, PaymentStatus status, String failureReason) {
        this.customer = customer;
        this.amount = amount;
        this.paymentDetail = paymentDetail;
        this.transactionType = transactionType;
        this.status = status;
        this.failureReason = failureReason;
    }

    public PaymentTransaction(Seller seller, BigDecimal amount, PaymentDetail paymentDetail, TransactionType transactionType, PaymentStatus status, String failureReason) {
        this.seller = seller;
        this.amount = amount;
        this.paymentDetail = paymentDetail;
        this.transactionType = transactionType;
        this.status = status;
        this.failureReason = failureReason;
    }
}
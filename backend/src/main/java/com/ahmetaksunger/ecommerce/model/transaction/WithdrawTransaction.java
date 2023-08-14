package com.ahmetaksunger.ecommerce.model.transaction;

import com.ahmetaksunger.ecommerce.model.BaseEntity;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.Seller;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "withdraw_transactions")
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class WithdrawTransaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private Seller seller;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @ManyToOne
    private PaymentDetail paymentDetail;

    @PrePersist
    public void beforePersist() {
        this.setCreatedAt(new Date());
    }
}

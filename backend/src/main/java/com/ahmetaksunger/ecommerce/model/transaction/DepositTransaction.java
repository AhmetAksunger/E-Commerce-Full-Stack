package com.ahmetaksunger.ecommerce.model.transaction;

import com.ahmetaksunger.ecommerce.model.BaseEntity;
import com.ahmetaksunger.ecommerce.model.PaymentDetail;
import com.ahmetaksunger.ecommerce.model.Seller;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "deposit_transaction")
@Getter
@Setter
public class DepositTransaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    private Seller seller;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @OneToOne
    private PaymentDetail paymentDetail;

    @PrePersist
    public void beforePersist() {
        this.setCreatedAt(new Date());
    }
}

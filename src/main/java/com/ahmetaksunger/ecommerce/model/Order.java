package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "orders")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total",nullable = false)
    private BigDecimal total;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Cart cart;

    @OneToOne
    private PaymentDetail paymentDetail;


}

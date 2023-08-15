package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "orders")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total",nullable = false)
    private BigDecimal total;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Address address;

    @OneToOne
    private Cart cart;

    @OneToOne
    private PaymentDetail paymentDetail;


}

package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.math.BigDecimal;

@Table(name = "orders")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Order extends BaseEntity{

    @Serial
    private static final long serialVersionUID = -4367702537737015032L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total",nullable = false)
    private BigDecimal total;

    @ManyToOne
    private Customer customer;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Address address;

    @OneToOne
    private Cart cart;

    @ManyToOne
    private PaymentDetail paymentDetail;


}

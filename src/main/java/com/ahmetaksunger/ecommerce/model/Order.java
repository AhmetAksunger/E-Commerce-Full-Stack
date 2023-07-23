package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "orders")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total",nullable = false)
    private BigDecimal total;
    @Column(name = "created_at",nullable = false)
    private Date createdAt;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Cart cart;

    @OneToOne
    private PaymentDetail paymentDetail;


}

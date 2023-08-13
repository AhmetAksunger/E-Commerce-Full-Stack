package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Table(name = "payment_details")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@SuperBuilder
public class PaymentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private long id;

    @Column(name = "credit_cart_number",nullable = false)
    private String creditCardNumber;

    @Column(name = "cvv",nullable = false)
    private String cvv;

    @Column(name = "expiration_date",nullable = false)
    private String expirationDate;

    @ManyToOne()
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH,optional = true)
    private Address address;

}

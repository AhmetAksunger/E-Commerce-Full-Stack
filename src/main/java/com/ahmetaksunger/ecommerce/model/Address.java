package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "addresses")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private long addressId;
    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "city",nullable = false)
    private String city;
    @Column(name = "country",nullable = false)
    private String country;
    @Column(name = "zip_code",nullable = true)
    private String zipCode;

    @ManyToOne()
    private Customer customer;

    @OneToOne
    private Seller seller;

    @OneToOne(mappedBy = "address")
    private PaymentDetail paymentDetail;
}

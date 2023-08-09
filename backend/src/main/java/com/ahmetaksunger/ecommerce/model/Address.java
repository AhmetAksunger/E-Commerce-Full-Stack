package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "addresses")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private long id;
    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "city",nullable = false)
    private String city;
    @Column(name = "country",nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;
    @Column(name = "zip_code",nullable = true)
    private String zipCode;

    @OneToMany(mappedBy = "address",cascade = CascadeType.REMOVE)
    private List<PaymentDetail> paymentDetails;
    @ManyToOne()
    private User user;
}

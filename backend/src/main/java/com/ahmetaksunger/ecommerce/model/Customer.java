package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Table(name = "customers")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Customer extends User{

    @Column(name = "full_name",nullable = false)
    private String fullName;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.PERSIST
    )
    private List<Cart> carts;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

}

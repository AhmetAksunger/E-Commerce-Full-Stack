package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.util.List;

@Table(name = "customers")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Customer extends User{

    @Serial
    private static final long serialVersionUID = 1612862304596667831L;

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

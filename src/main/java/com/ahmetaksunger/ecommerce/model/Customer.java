package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.List;

@Table(name = "customers")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User{

    @Column(name = "full_name",nullable = false)
    private String fullName;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
    public Customer(String email, String password,
                    Date createdAt, Date updatedAt,
                    String fullName, String phoneNumber){
        super(email,password,createdAt,updatedAt,true);
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }
}

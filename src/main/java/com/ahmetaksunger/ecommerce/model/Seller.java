package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Table(name = "sellers")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends User{

    @Column(name = "company_name",nullable = false)
    private String companyName;
    @Column(name = "contact_number",nullable = false)
    private String contactNumber;
    @Column(name = "logo",nullable = true)
    private String logo;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    public Seller(String email, String password,
                    Date createdAt, Date updatedAt,
                    String companyName, String contactNumber, String logo){
        super(email,password,createdAt,updatedAt,false);
        this.companyName = companyName;
        this.contactNumber = contactNumber;
        this.logo = logo;
    }

}

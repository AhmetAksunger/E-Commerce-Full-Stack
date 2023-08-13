package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "sellers")
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Seller extends User{

    @Column(name = "company_name",nullable = false)
    private String companyName;
    @Column(name = "contact_number",nullable = false)
    private String contactNumber;
    @Column(name = "logo",nullable = true)
    private String logo;
    @Column(name = "total_revenue",nullable = true)
    @Builder.Default
    private BigDecimal totalRevenue = BigDecimal.ZERO;
    @OneToMany(mappedBy = "seller",cascade = CascadeType.REMOVE)
    private List<Product> products;

}

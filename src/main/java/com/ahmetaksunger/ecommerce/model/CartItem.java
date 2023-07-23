package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "cart_items")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Cart cart;

    @OneToOne
    private Product product;

    @Column(name = "quantity",nullable = false)
    private int quantity;
}

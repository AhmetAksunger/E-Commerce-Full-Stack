package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "carts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cartId;

    @OneToOne
    private Customer customer;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;
}

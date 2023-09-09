package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Table(name = "cart_items")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@SuperBuilder
public class CartItem extends BaseEntity{

    @Serial
    private static final long serialVersionUID = 67218930675073768L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Cart cart;

    @ManyToOne
    private Product product;

    @Column(name = "quantity",nullable = false)
    private int quantity;
}

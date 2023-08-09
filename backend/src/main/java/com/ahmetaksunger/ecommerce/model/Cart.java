package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "carts")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@SuperBuilder
public class Cart extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Customer customer;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.REMOVE)
    private List<CartItem> cartItems;
}

package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Product extends BaseEntity{

    @Serial
    private static final long serialVersionUID = 1714468052410224921L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "logo",nullable = false)
    private String logo;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EntityStatus status = EntityStatus.ACTIVE;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Seller seller;

    @ManyToMany
    private List<Category> categories;

}

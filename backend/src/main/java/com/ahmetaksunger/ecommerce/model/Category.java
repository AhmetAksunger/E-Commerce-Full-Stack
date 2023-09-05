package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Entity
@Table(name = "categories")
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
public class Category extends BaseEntity{

    @Serial
    private static final long serialVersionUID = -1857151839022954536L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;

}

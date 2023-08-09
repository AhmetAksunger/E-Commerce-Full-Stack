package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@MappedSuperclass
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity {
    
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

}

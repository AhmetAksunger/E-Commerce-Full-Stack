package com.ahmetaksunger.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userId;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "created_at",nullable = false)
    private Date createdAt;
    @Column(name = "updated_at",nullable = true)
    private Date updatedAt;
    @Column(name = "isCustomer")
    private boolean isCustomer;

    @OneToMany(mappedBy = "user")
    private List<PaymentDetail> paymentDetails;
    public User(String email, String password, Date createdAt, Date updatedAt,boolean isCustomer) {
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isCustomer = isCustomer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

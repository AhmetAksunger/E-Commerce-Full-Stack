package com.ahmetaksunger.ecommerce.model;

import java.io.Serial;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "users")
@SuperBuilder
public class User extends BaseEntity implements UserDetails {

    @Serial
    private static final long serialVersionUID = 843675875996341745L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "userType")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
    
    @OneToMany(mappedBy = "user")
    private List<PaymentDetail> paymentDetails;
    public User(String email, String password, Date createdAt, Date updatedAt,UserType userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userType.name()));
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

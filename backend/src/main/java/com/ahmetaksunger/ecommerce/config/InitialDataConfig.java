package com.ahmetaksunger.ecommerce.config;

import com.ahmetaksunger.ecommerce.model.*;
import com.ahmetaksunger.ecommerce.repository.CustomerRepository;
import com.ahmetaksunger.ecommerce.repository.ProductRepository;
import com.ahmetaksunger.ecommerce.repository.SellerRepository;
import com.ahmetaksunger.ecommerce.repository.UserRepository;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class InitialDataConfig {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    /**
     *
     * The default method used for creating and saving default data into the database
     * at the beginning of the project, where instances of the {@link User} and {@link Seller}
     * entity objects are created and persisted as default data.
     *
     */
    @Bean
    @Transactional
    CommandLineRunner initialData(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                // Creating Default Admin User
                final User defaultAdminUserEntity = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("test123"))
                        .userType(UserType.ADMIN)
                        .createdAt(new Date())
                        .build();

                userRepository.save(defaultAdminUserEntity);
            }
        };
    }
}

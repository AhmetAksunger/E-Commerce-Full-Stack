package com.ahmetaksunger.ecommerce.config;

import com.ahmetaksunger.ecommerce.model.*;
import com.ahmetaksunger.ecommerce.repository.*;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class InitialDataConfig {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final PaymentDetailRepository paymentDetailRepository;
    private final CategoryRepository categoryRepository;

    private final Faker faker = new Faker();

            /*
                 Creating 20 default customers
                 Each customer will have
                    1 address
                    1 payment detail
                    1 cart
                 */
    /**
     * Creates specified amount of {@link Customer}s.
     * Each customer has 1 {@link Address}, 1 {@link PaymentDetail} and 1 {@link Cart}
     *
     * @param amount amount of customers
     */
    private void createCustomers(int amount){
        for (int i = 0; i < amount; i++) {

            final String phoneNumber = faker.phoneNumber().cellPhone()
                    .replace("-", "")
                    .replace(" ", "")
                    .replace(".", "")
                    .replace("(", "")
                    .replace(")", "");

            final Customer customer = Customer.builder()
                    .email(faker.internet().emailAddress())
                    .password(passwordEncoder.encode("test123"))
                    .userType(UserType.CUSTOMER)
                    .fullName(faker.name().fullName())
                    .phoneNumber(phoneNumber)
                    .createdAt(new Date())
                    .build();

            final Cart cart = Cart.builder()
                    .createdAt(new Date())
                    .customer(customer)
                    .build();

            customer.setCart(cart);
            customerRepository.save(customer);

            final Address address = Address.builder()
                    .address(faker.address().fullAddress())
                    .city(faker.address().city())
                    .country(Country.TURKEY)
                    .zipCode(faker.address().zipCode())
                    .user(customer)
                    .createdAt(new Date())
                    .build();

            addressRepository.save(address);

            final PaymentDetail paymentDetail = PaymentDetail.builder()
                    .creditCardNumber(faker.business().creditCardNumber())
                    .cvv("123")
                    .expirationDate(faker.business().creditCardExpiry())
                    .user(customer)
                    .build();

            paymentDetailRepository.save(paymentDetail);
        }
    }

    /**
     * Creates specified amount of @{@link Category}s
     * @param amount amount of categories
     */
    private void createCategories(int amount) {
        for (int i = 0; i < 20; i++) {

            final Category category = Category.builder()
                    .name(faker.commerce().department())
                    .description(faker.lorem().paragraph(2))
                    .createdAt(new Date())
                    .build();

            categoryRepository.save(category);
        }
    }

    @Bean
    @Transactional
    CommandLineRunner initialData() {
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

                // Creating Default Customers
                createCustomers(20);

                // Creating categories
                createCategories(20);


            }
        };
    }
}

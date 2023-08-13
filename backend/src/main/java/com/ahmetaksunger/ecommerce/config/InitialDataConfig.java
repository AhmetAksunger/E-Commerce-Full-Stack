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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * Creates specified amount of {@link Customer}s.
     * Each customer has 1 {@link Address}, 1 {@link PaymentDetail} and 1 {@link Cart}
     *
     * @param amount amount of customers
     */
    private void createCustomers(int amount) {
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
     *
     * @param amount amount of categories
     */
    private void createCategories(int amount) {
        for (int i = 0; i < amount; i++) {

            final Category category = Category.builder()
                    .name(faker.commerce().department())
                    .description(faker.lorem().paragraph(2))
                    .createdAt(new Date())
                    .build();

            categoryRepository.save(category);
        }
    }

    /**
     * Creates specified amount of {@link Seller}s.
     * Each seller has 10 {@link PaymentDetail},10 {@link Product}s and
     * each product has 2 randomly picked {@link Category}s
     *
     * @param amount number of sellers
     */
    private void createSellers(int amount) {

        List<Category> dbCategories = categoryRepository.findAll();

        for (int i = 0; i < amount; i++) {
            final String contactNumber = faker.phoneNumber().cellPhone()
                    .replace("-", "")
                    .replace(" ", "")
                    .replace(".", "")
                    .replace("(", "")
                    .replace(")", "");

            final Seller seller = Seller.builder()
                    .email(faker.internet().emailAddress())
                    .password(passwordEncoder.encode("test123"))
                    .userType(UserType.SELLER)
                    .companyName(faker.company().name())
                    .contactNumber(contactNumber)
                    .logo(faker.company().logo())
                    .createdAt(new Date())
                    .build();

            sellerRepository.save(seller);

            final PaymentDetail paymentDetail = PaymentDetail.builder()
                    .creditCardNumber(faker.business().creditCardNumber())
                    .cvv("123")
                    .expirationDate(faker.business().creditCardExpiry())
                    .user(seller)
                    .build();

            paymentDetailRepository.save(paymentDetail);

            for (int k = 0; k < 10; k++) {
                //randomly pick two categories
                List<Category> productCategories = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    productCategories.add(dbCategories.get(faker.number().numberBetween(0, dbCategories.size() - 1)));
                }

                final Product product = Product.builder()
                        .name(faker.commerce().productName())
                        .description(faker.lorem().paragraph(2))
                        .price(new BigDecimal(faker.commerce().price().replace(",", ".")))
                        .quantity(faker.number().numberBetween(1, 20))
                        .logo(faker.company().logo())
                        .seller(seller)
                        .categories(productCategories)
                        .createdAt(new Date())
                        .build();

                productRepository.save(product);
            }
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

                // Creating Sellers
                createSellers(10);
            }
        };
    }
}

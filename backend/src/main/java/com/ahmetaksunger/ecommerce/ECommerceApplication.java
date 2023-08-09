package com.ahmetaksunger.ecommerce;

import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.model.UserType;
import com.ahmetaksunger.ecommerce.repository.CustomerRepository;
import com.ahmetaksunger.ecommerce.repository.SellerRepository;
import com.ahmetaksunger.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner test(){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				if(!userRepository.existsByEmail("admin")){
					User user = new User();
					user.setEmail("admin");
					user.setPassword(passwordEncoder.encode("test123"));
					user.setUserType(UserType.ADMIN);
					user.setCreatedAt(new Date());

					userRepository.save(user);
				}
			}
		};
	}
}

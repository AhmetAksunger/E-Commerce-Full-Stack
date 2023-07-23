package com.ahmetaksunger.ecommerce;

import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.CustomerRepository;
import com.ahmetaksunger.ecommerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Bean
	CommandLineRunner test(){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				/*
				Seller user1 = new Seller("asdasdasd","password",new Date(),new Date(),"ksjdflkdsf","1923810923","dsıfjslkdf");
				sellerRepository.save(user1);

				Customer customer = new Customer("asdasdasd","password",new Date(),new Date(),"ahmedi hane","123123");
				customerRepository.save(customer);
*/


			}
		};
	}
}

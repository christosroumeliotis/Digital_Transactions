package com.Bank.DigitalBankSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DigitalBankSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankSystemApplication.class, args);
		System.out.println("We are ready for development!");
	}
}

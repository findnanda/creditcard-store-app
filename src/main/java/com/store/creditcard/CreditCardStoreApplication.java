package com.store.creditcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;

@ComponentScan(basePackages = "com.store.creditcard.*")
@EnableMongoRepositories
@SpringBootApplication
@ActiveProfiles({ "local" })
@EnableAutoConfiguration
public class CreditCardStoreApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CreditCardStoreApplication.class);
		app.setLogStartupInfo(true);
		app.run(args);
	}
}

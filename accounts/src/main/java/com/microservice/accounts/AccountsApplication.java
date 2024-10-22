package com.microservice.accounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Bank microservice REST API Documentation",
				description = "Banking System microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Prakash Anandakumar",
						email = "prakash.anandakumar@acentura.com",
						url = "https://start.spring.io/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://start.spring.io/"
				)
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}

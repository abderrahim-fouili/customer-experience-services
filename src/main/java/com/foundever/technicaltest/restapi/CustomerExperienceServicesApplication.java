package com.foundever.technicaltest.restapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "FOUNDEVER CUSTOMER EXPERIENCE SERVICES : DEFAULT SERVER URL")})
@SpringBootApplication
public class CustomerExperienceServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerExperienceServicesApplication.class, args);
	}

}

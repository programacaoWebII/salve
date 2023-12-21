package com.salve.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Salve", version = "1", description = "Salve"))
@SpringBootApplication
public class SalveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalveApplication.class, args);
	}

}

package com.tp.guarderia_central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GuarderiaCentralApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuarderiaCentralApplication.class, args);
	}

}

package com.guarderia.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GuarderiaCentralApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuarderiaCentralApplication.class, args);
        System.out.println("==============================================");
        System.out.println("Aplicación Guarderia Central iniciada!");
        System.out.println("URL: http://localhost:8080/guarderia-central");
        System.out.println("API REST: http://localhost:8080/guarderia-central/api/socios");
        System.out.println("==============================================");
    }

}

package org.example.java_webservice;

import org.example.java_webservice.entities.Compte;
import org.example.java_webservice.entities.TypeCompte;
import org.example.java_webservice.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class JavaWebserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaWebserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository) {
        return args -> {
            compteRepository.save(new Compte(null, Math.random() * 9000, TypeCompte.EPARGNE));
            compteRepository.save(new Compte(null, Math.random() * 9000, TypeCompte.COURANT));
            compteRepository.save(new Compte(null, Math.random() * 9000, TypeCompte.EPARGNE));
            compteRepository.findAll().forEach(c -> {
                System.out.println(c.toString());
            });
        };
    }
}

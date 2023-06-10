package com.example.devise.Utilisateur;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class UtilisateurConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            UtilisateurRepository repository) {
        return  args -> {
            Utilisateur Jacob = new Utilisateur(
                    1L,
                    "Jacob",
                    "Zuma",
                    LocalDate.of(1960, Month.JANUARY, 5),
                    "hsgg6768dwhgjjdkjwhsjfhjshdjhsjd",
                    "jaczu@gmail.com",
                    "343, rue de la poule",
                    LocalDate.of(2023, Month.JANUARY, 5),
                    LocalDate.of(2023, Month.JANUARY, 5)
            );
            Utilisateur Jacobi = new Utilisateur(
                    1L,
                    "Jacobi",
                    "Zuma2",
                    LocalDate.of(1960, Month.JANUARY, 5),
                    "hsgg6768dwhgjjdkjwhsjfhjshdjhsjd",
                    "jaczu@gmail.com",
                    "343, rue de la poule",
                    LocalDate.of(2023, Month.JANUARY, 5),
                    LocalDate.of(2023, Month.JANUARY, 5)
            );
            repository.saveAll(
                    List.of(Jacob, Jacobi)
            );
        };
    }
}

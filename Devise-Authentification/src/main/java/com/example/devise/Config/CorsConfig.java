package com.example.devise.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:63342") // Remplacez par l'URL de votre frontend
                    .allowedMethods("GET", "POST", "PUT", "DELETE") // Ajoutez ici les méthodes HTTP que vous souhaitez autoriser
                    .allowedHeaders("*") // Autorisez tous les en-têtes
                    .allowCredentials(true); // Autorisez l'envoi de cookies et d'informations d'authentification
        }

}

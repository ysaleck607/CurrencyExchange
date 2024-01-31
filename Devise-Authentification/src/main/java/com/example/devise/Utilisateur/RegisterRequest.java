package com.example.devise.Utilisateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String prenom;
    private String nom;
    private LocalDate dateNaissance;
    private String motDePasse;
    private String email;
    private String adresse;
    private Role role;
}

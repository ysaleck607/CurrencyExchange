package com.example.devise.Demande;

import com.example.devise.Utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
}

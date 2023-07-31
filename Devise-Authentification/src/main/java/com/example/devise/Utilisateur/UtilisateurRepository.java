package com.example.devise.Utilisateur;

import com.example.devise.Demande.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository
        extends JpaRepository<Utilisateur, Long>{
    Optional<Utilisateur> findByEmail(String email);

    Utilisateur findByIdUtilisateur (Long idUtilisateur);
}

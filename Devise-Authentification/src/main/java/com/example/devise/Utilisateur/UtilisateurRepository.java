package com.example.devise.Utilisateur;

import com.example.devise.Demande.Demande;
import com.example.devise.Utilisateur.Status;
import com.example.devise.Utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository
        extends JpaRepository<Utilisateur, Long>{
    Optional<Utilisateur> findByEmail(String email);

    Utilisateur findByIdUtilisateur (Long idUtilisateur);
    List<Utilisateur> findAllByStatus(Status status);
}

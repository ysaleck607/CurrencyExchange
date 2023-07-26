package com.example.devise.Demande;

import com.example.devise.Utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    //List<Demande> findByIdDemandeurAndStatutDemande(Long idDemandeur, List<StatutDemand> listStatutDemand);
    List<Optional<Demande>> findByIdDemandeurAndStatutDemandeIn(Long idDemandeur, List<StatutDemand> statutDemand);
}

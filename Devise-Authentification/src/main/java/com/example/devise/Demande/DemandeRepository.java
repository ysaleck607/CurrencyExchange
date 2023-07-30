package com.example.devise.Demande;

import com.example.devise.Utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    //List<Demande> findByIdDemandeurAndStatutDemande(Long idDemandeur, List<StatutDemand> listStatutDemand);
    List<Optional<Demande>> findByIdDemandeurAndStatutIn(Long idDemandeur, List<String> statutDemand);
    List<Optional<Demande>> findByIdDemandeurNotAndStatutIn(Long idDemandeur, List<String> statutDemand);
//    @Modifying
//    @Query("INSERT INTO DemandeDevise (idDemandeur, deviseVoulu, deviseOfferte, montantVoulu, statutDemande, dateDemande) " +
//            "VALUES (:idDemandeur, :deviseVoulu, :deviseOfferte, :montantVoulu, :statutDemande, :dateDemande)")
//    void insertDemande(@Param("idDemandeur") Long idDemandeur,
//                       @Param("deviseVoulu") String deviseVoulu,
//                       @Param("deviseOfferte") String deviseOfferte,
//                       @Param("montantVoulu") BigDecimal montantVoulu,
//                       @Param("statutDemande") StatutDemand statutDemande,
//                       @Param("dateDemande") LocalDateTime dateDemande);
}

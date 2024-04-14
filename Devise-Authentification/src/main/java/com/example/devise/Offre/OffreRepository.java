package com.example.devise.Offre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {
    List<Optional<Offre>> findByIdOffreurAndAndStatutOffreIn(Long idOffreur, List<String> statutOffres);
    List<Offre> findByIdDemandeAndStatutOffre(Long idDemand, String StatutOffre);
    List<Offre> findByIdDemandeAndStatutOffreIn(Long idDemand, List<String> StatutOffre);
    @Query("SELECT o, ou.nom, ou.prenom, ou.email " +
            "FROM Offre o " +
            "LEFT JOIN Utilisateur ou ON o.idOffreur = ou.idUtilisateur " +
            "WHERE o.idDemande = :idDemand")
    List<Object[]> findByIdDemande(@Param("idDemand") Long idDemand);

    @Query("SELECT o, du.nom, du.prenom, ou.nom, ou.prenom, d.deviseOfferte, d.deviseVoulu, d.montantVoulu, d.dateDemande, d.idDemandeur, d.montantOffert " +
            "FROM Offre o " +
            "LEFT JOIN Demande d ON o.idDemande = d.idDemande " +
            "LEFT JOIN Utilisateur du ON d.idDemandeur = du.idUtilisateur " +
            "LEFT JOIN Utilisateur ou ON o.idOffreur = ou.idUtilisateur " +
            "WHERE o.idOffreur = :utilisateurId")
    List<Object[]> getOffresWithNamesByUtilisateurId(@Param("utilisateurId") Long utilisateurId);

    @Query("SELECT o, du.nom, du.prenom, ou.nom, ou.prenom, d.deviseOfferte, d.deviseVoulu, d.montantVoulu, d.dateDemande, d.montantOffert " +
            "FROM Offre o " +
            "LEFT JOIN Demande d ON o.idDemande = d.idDemande " +
            "LEFT JOIN Utilisateur du ON d.idDemandeur = du.idUtilisateur " +
            "LEFT JOIN Utilisateur ou ON o.idOffreur = ou.idUtilisateur " +
            "WHERE d.idDemande = :demandeId")
    List<Object[]> getOffresWithNamesByDemandeId(@Param("demandeId") Long demandeId);

    Boolean existsByIdDemandeAndAndIdOffreur(Long idDemande, Long idOffreur);

}

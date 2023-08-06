package com.example.devise.Offre;

import com.example.devise.Demande.StatutDemand;
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
    @Query("SELECT o, ou.nom, ou.prenom, ou.email " +
            "FROM Offre o " +
            "LEFT JOIN Utilisateur ou ON o.idOffreur = ou.idUtilisateur " +
            "WHERE o.idDemande = :idDemand")
    List<Object[]> findByIdDemande(@Param("idDemand") Long idDemand);


//    @Query("SELECT d, o, du.nom, ou.nom FROM Demande d " +
//            "LEFT JOIN Offre o ON d.idDemande = o.idDemande " +
//            "LEFT JOIN Utilisateur du ON d.idDemandeur = du.idUtilisateur " +
//            "LEFT JOIN Utilisateur ou ON o.idOffreur = ou.idUtilisateur " +
//            "WHERE d.idDemande = :demandeId")
//    Object[] getDemandeWithOffresAndNames(@Param("demandeId") Long demandeId);
//
//    @Query("SELECT o, du.nom, ou.nom FROM OffreDevise o " +
//            "LEFT JOIN o.idDemande d " +
//            "LEFT JOIN d.idDemandeur du " +
//            "LEFT JOIN o.idOffreur of " +
//            "WHERE d.idDemande = :demandeId")
//    List<Object[]> getOffresWithNamesForDemande(@Param("demandeId") Long demandeId);
//
//    @Query("SELECT o, du.nom, of.nom FROM Offre o " +
//            "LEFT JOIN Utilisateur du ON o.idDemande.idDemandeur = du.idUtilisateur " +
//            "LEFT JOIN Utilisateur of ON o.idOffreur = of.idUtilisateur " +
//            "WHERE du.idUtilisateur = :utilisateurId")
//    List<Object[]> getOffresWithNamesByUtilisateurId(@Param("utilisateurId") Long utilisateurId);
//
//    @Query("SELECT o, du.nom, du.prenom, ou.nom, ou.prenom " +
//            "FROM Offre o " +
//            "LEFT JOIN o.demandeDevise dd " +
//            "LEFT JOIN dd.demandeur du " +
//            "LEFT JOIN o.offreur ou " +
//            "WHERE dd.idDemande = :demandeId")
//    List<Object[]> getOffresWithNamesByDemandeId(@Param("demandeId") Long demandeId);
//
//    @Query("SELECT dd.IdDemande, dd.Statut, dd.deviseVoulu, dd.deviseOfferte, dd.montantVoulu, dd.DateDemande, du.Nom AS NomDemandeur, du.Prenom AS PrenomDemandeur, ou.Nom AS NomOffreur, ou.Prenom AS PrenomOffreur " +
//            "FROM DemandeDevise dd" +
//            "INNER JOIN Utilisateur du ON dd.IdDemandeur = du.IdUtilisateur" +
//            INNER JOIN OffreDevise od ON dd.IdDemande = od.IdDemande
//            INNER JOIN Utilisateur ou ON od.IdOffreur = ou.IdUtilisateur
//            WHERE
//            dd.IdDemande = [ID_DEMANDE];)

    @Query("SELECT o, du.nom, du.prenom, ou.nom, ou.prenom, d.deviseOfferte, d.deviseVoulu, d.montantVoulu, d.dateDemande " +
            "FROM Offre o " +
            "LEFT JOIN Demande d ON o.idDemande = d.idDemande " +
            "LEFT JOIN Utilisateur du ON d.idDemandeur = du.idUtilisateur " +
            "LEFT JOIN Utilisateur ou ON o.idOffreur = ou.idUtilisateur " +
            "WHERE o.idOffreur = :utilisateurId")
    List<Object[]> getOffresWithNamesByUtilisateurId(@Param("utilisateurId") Long utilisateurId);

    @Query("SELECT o, du.nom, du.prenom, ou.nom, ou.prenom, d.deviseOfferte, d.deviseVoulu, d.montantVoulu, d.dateDemande " +
            "FROM Offre o " +
            "LEFT JOIN Demande d ON o.idDemande = d.idDemande " +
            "LEFT JOIN Utilisateur du ON d.idDemandeur = du.idUtilisateur " +
            "LEFT JOIN Utilisateur ou ON o.idOffreur = ou.idUtilisateur " +
            "WHERE d.idDemande = :demandeId")
    List<Object[]> getOffresWithNamesByDemandeId(@Param("demandeId") Long demandeId);

}

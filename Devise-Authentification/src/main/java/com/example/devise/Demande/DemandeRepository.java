package com.example.devise.Demande;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Optional<Demande>> findByIdDemandeurAndStatutIn(Long idDemandeur, List<String> statutDemand);
    List<Optional<Demande>> findByIdDemandeurNotAndStatutIn(Long idDemandeur, List<String> statutDemand);

    List<Demande> findByIdDemandeur(Long idUser);

    Demande findByIdDemande (Long idDemande);

    @Query("SELECT d, u.nom, u.prenom " +
            "FROM Demande d " +
            "LEFT JOIN Utilisateur u ON d.idDemandeur = u.idUtilisateur " +
            "WHERE d.idDemandeur = :userId")
    List<Object[]> getDemandesWithNamesByUserId(@Param("userId") Long userId);

    @Query("SELECT d, u.nom, u.prenom " +
            "FROM Demande d " +
            "LEFT JOIN Utilisateur u ON d.idDemandeur = u.idUtilisateur " +
            "WHERE d.idDemandeur = :userId")
    List<Object[]> getDemandesWithNamesByOthersUsers(@Param("userId") Long userId);

    @Query("SELECT d, du.nom, du.prenom FROM Demande d " +
            "LEFT JOIN Utilisateur du ON d.idDemandeur = du.idUtilisateur " +
            "WHERE d.idDemandeur <> :userId " +
            "AND d.statut IN ('ENCOURS', 'ENATTENTE')")
    List<Object[]> getDemandsWithDemandeurNamesExceptUser(@Param("userId") Long userId);

    @Query("SELECT " +
            "   SUM(CASE WHEN d.statut = 'TERMINER' THEN 1 ELSE 0 END) + " +
            "   SUM(CASE WHEN o.statutOffre = 'TERMINER' THEN 1 ELSE 0 END) AS totalCompletedItems " +
            "FROM " +
            "   Demande d " +
            "LEFT JOIN " +
            "   Offre o ON d.idDemandeur = o.idOffreur " +
            "WHERE " +
            "   d.idDemandeur = :userId OR o.idOffreur = :userId")
    int getTotalCompletedItemsForUser(@Param("userId") Long userId);
}

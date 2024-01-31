package com.example.devise.Commentaire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Optional<Commentaire>> findByIdUtilisateurNote(Long idUtilisateur);

    @Query("SELECT c, u.nom, u.prenom " +
            "FROM Commentaire c " +
            "JOIN Utilisateur u ON c.idUtilisateurNote = u.idUtilisateur " +
            "WHERE c.idUtilisateurNote = :idUtilisateur")
    List<Object[]> getCommentairesWithUtilisateurInfo(@Param("idUtilisateur") Long idUtilisateur);
}

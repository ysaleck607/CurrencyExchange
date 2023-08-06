package com.example.devise.Commentaire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByIdUtilisateurNote(Long idUtilisateur);
}

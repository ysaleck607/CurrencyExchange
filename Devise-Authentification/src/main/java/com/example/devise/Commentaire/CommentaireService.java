package com.example.devise.Commentaire;

import com.example.devise.Utilisateur.Utilisateur;
import com.example.devise.Utilisateur.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentaireService {
    private final CommentaireRepository commentaireRepository;
    private final UtilisateurRepository utilisateurRepository;
    // Constructeur

    public void ajouterCommentaire(AddCommentaireRequest request) {
        var nouveauCommentaire = Commentaire.builder()
                .idUtilisateur(request.getIdUtilisateur())
                .idUtilisateurNote(request.getUtilisateurNote())
                .note(request.getNote())
                .commentaire(request.getCommentaire())
                .idOffre(request.getIdOffre())
                .idDemande(request.getIdDemande())
                .dateCommentaire(LocalDateTime.now())
                .build();
        commentaireRepository.save(nouveauCommentaire);
    }

    public List<Commentaire> getCommentairesByUtilisateur(Long idUtilisateur) {
        return commentaireRepository.findByIdUtilisateurNote(idUtilisateur);
    }
}

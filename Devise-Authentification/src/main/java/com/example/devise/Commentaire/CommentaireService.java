package com.example.devise.Commentaire;

import com.example.devise.Demande.DemandeRepository;
import com.example.devise.Utilisateur.Utilisateur;
import com.example.devise.Utilisateur.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentaireService {
    private final CommentaireRepository commentaireRepository;
    private final UtilisateurRepository utilisateurRepository;

    private final DemandeRepository demandeRepository;

    public void ajouterCommentaire(AddCommentaireRequest request) {
        var nouveauCommentaire = Commentaire.builder()
                .idUtilisateurNoteur(request.getIdUtilisateurNoteur())
                .idUtilisateurNote(request.getIdUtilisateurNote())
                .note(request.getNote())
                .commentaire(request.getCommentaire())
                .idOffre(request.getIdOffre())
                .idDemande(request.getIdDemande())
                .dateCommentaire(LocalDateTime.now())
                .build();
        commentaireRepository.save(nouveauCommentaire);
    }

    public List<CommentaireResponse> getCommentairesByUtilisateur(Long idUtilisateur) {
        List<Object[]> commentairesInfo = commentaireRepository.getCommentairesWithUtilisateurInfo(idUtilisateur);
        int nombreEchanesEffectue = demandeRepository.getTotalCompletedItemsForUser(idUtilisateur);

        List<CommentaireResponse> commentairesResponse = new ArrayList<>();

        for (Object[] info : commentairesInfo) {
            Commentaire commentaire = (Commentaire) info[0];
            String nom = (String) info[1];
            String prenom = (String) info[2];

            Utilisateur utilisateurNote = utilisateurRepository.findById(commentaire.getIdUtilisateurNote())
                    .orElseThrow(() -> new IllegalStateException("Utilisateur not found"));

            CommentaireResponse commentaireResponse = CommentaireResponse.builder()
                    .idCommentaire(commentaire.getIdCommentaire())
                    .idUtilisateurNoteur(commentaire.getIdUtilisateurNoteur())
                    .nomPrenomNoteur(nom + " " + prenom)
                    .idUtilisateurNote(commentaire.getIdUtilisateurNote())
                    .note(commentaire.getNote())
                    .idOffre(commentaire.getIdOffre())
                    .idDemande(commentaire.getIdDemande())
                    .commentaire(commentaire.getCommentaire())
                    .dateCommentaire(commentaire.getDateCommentaire())
                    .nombreEchangeEffectue(nombreEchanesEffectue)
                    .build();

            commentairesResponse.add(commentaireResponse);
        }

        return commentairesResponse;
    }
}

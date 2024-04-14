package com.example.devise.Commentaire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentaireResponse {
    private Long idCommentaire;

    private Long idUtilisateurNoteur; // L'utilisateur qui a laissé le commentaire

    private String nomPrenomNoteur;

    private Long idUtilisateurNote; // L'utilisateur qui est noté

    private int note; // La note attribuée à l'utilisateur (par exemple, de 1 à 5)

    private int idOffre;

    private int idDemande;

    private String commentaire; // Le texte du commentaire

    private int nombreEchangeEffectue;

    private LocalDateTime dateCommentaire;
}

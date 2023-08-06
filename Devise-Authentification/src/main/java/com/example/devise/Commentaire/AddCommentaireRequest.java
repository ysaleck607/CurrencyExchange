package com.example.devise.Commentaire;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentaireRequest {

    private Long idUtilisateur; // L'utilisateur qui a laissé le commentaire

    private Long utilisateurNote; // L'utilisateur qui est noté

    private int note; // La note attribuée à l'utilisateur (par exemple, de 1 à 5)

    private int idOffre;

    private int idDemande;

    private String commentaire; // Le texte du commentaire

}

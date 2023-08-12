package com.example.devise.Commentaire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentaireRequest {

    private Long idUtilisateur;

    private Long utilisateurNote;

    private int note;

    private int idOffre;

    private int idDemande;

    private String commentaire;

}

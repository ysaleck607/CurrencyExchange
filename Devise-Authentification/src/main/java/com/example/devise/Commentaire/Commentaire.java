package com.example.devise.Commentaire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commentaire")
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcommentaire")
    private Long idCommentaire;

    //@ManyToOne
    @Column(name = "idutilisateurnoteur")
    private Long idUtilisateur; // L'utilisateur qui a laissé le commentaire

    //@ManyToOne
    @Column(name = "idutilisateurnote")
    private Long idUtilisateurNote; // L'utilisateur qui est noté

    @Column(name = "note")
    private int note; // La note attribuée à l'utilisateur (par exemple, de 1 à 5)

    @Column(name = "idoffre")
    private int idOffre;

    @Column(name = "iddemande")
    private int idDemande;

    @Column(name = "textecommentaire")
    private String commentaire; // Le texte du commentaire

    @Column(name = "datecommentaire")
    private LocalDateTime dateCommentaire; // Le texte du commentaire

    public Commentaire(Long idUtilisateur, Long idUtilisateurNote, int note, int idOffre, int idDemande, String commentaire, LocalDateTime dateCommentaire) {
        this.idUtilisateur = idUtilisateur;
        this.idUtilisateurNote = idUtilisateurNote;
        this.note = note;
        this.idOffre = idOffre;
        this.idDemande = idDemande;
        this.commentaire = commentaire;
        this.dateCommentaire = dateCommentaire;
    }


    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Long getIdUtilisateurNote() {
        return idUtilisateurNote;
    }

    public void setIdUtilisateurNote(Long utilisateurNote) {
        this.idUtilisateurNote = utilisateurNote;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDateTime getDateCommentaire() {
        return dateCommentaire;
    }

    public void setDateCommentaire(LocalDateTime dateCommentaire) {
        this.dateCommentaire = dateCommentaire;
    }

    public Long getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(Long idCommentaire) {
        this.idCommentaire = idCommentaire;
    }
}
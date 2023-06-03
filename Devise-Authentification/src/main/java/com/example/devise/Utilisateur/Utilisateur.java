package com.example.devise.Utilisateur;

import java.time.LocalDate;

public class Utilisateur {
    private Long idUtilisateur;
    private String prenom;
    private String nom;
    private LocalDate dateNaissance;
    private String motDePasse;
    private String email;
    private String adresse;
    private LocalDate dateCreation;
    private LocalDate dateMAJ;

    public Utilisateur(Long idUtilisateur,
                       String prenom,
                       String nom,
                       LocalDate dateNaissance,
                       String motDePasse,
                       String email,
                       String adresse,
                       LocalDate dateCreation,
                       LocalDate dateMAJ) {
        this.idUtilisateur = idUtilisateur;
        this.prenom = prenom;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.motDePasse = motDePasse;
        this.email = email;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
        this.dateMAJ = dateMAJ;
    }

    public Utilisateur() {
    }

    public Utilisateur(String prenom,
                       String nom,
                       LocalDate dateNaissance,
                       String motDePasse,
                       String email,
                       String adresse,
                       LocalDate dateCreation,
                       LocalDate dateMAJ) {
        this.prenom = prenom;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.motDePasse = motDePasse;
        this.email = email;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
        this.dateMAJ = dateMAJ;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateMAJ() {
        return dateMAJ;
    }

    public void setDateMAJ(LocalDate dateMAJ) {
        this.dateMAJ = dateMAJ;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", motDePasse='" + motDePasse + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateMAJ=" + dateMAJ +
                '}';
    }
}

package com.example.devise.Utilisateur;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements UserDetails {
    @Id
//    @SequenceGenerator(
//            name = "utilisateur_sequence",
//            sequenceName = "utilisateur_sequence",
//            allocationSize = 1
//    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
            //generator = "utilisateur_sequence"
    )
    @Column(name = "idutilisateur")
    private Long idUtilisateur;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "nom")
    private String nom;
    @Column(name = "datenaissance")
    private LocalDate dateNaissance;
    @Column(name = "motdepassehache")
    private String motDePasse;
    @Column(name = "email")
    private String email;
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "datecreation")
    private LocalDate dateCreation;
    @Column(name = "dernieredatemaj")
    private LocalDate dateMAJ;
    @Enumerated(EnumType.STRING)
    @Column(name = "roleutilisateur")
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Utilisateur(String prenom,
                       String nom,
                       LocalDate dateNaissance,
                       String motDePasse,
                       String email,
                       String adresse,
                       LocalDate dateCreation,
                       LocalDate dateMAJ,
                       Role role,
                       Status status) {
        this.prenom = prenom;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.motDePasse = motDePasse;
        this.email = email;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
        this.dateMAJ = dateMAJ;
        this.role = role;
        this.status= status;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.example.devise.Demande;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "demandedevise")
public class Demande {
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
    @Column(name = "iddemande")
    private Long idDemande;
    @Column(name = "iddemandeur")
    private Long idDemandeur;
    @Column(name = "devisevoulu")
    private String deviseVoulu;
    @Column(name = "montantvoulu")
    private BigDecimal montantVoulu;
    @Column(name = "deviseofferte")
    private String deviseOfferte;
    //@Column(name = "montantOfferte")
    //private BigDecimal montantOfferte;
    @Column(name = "datedemande")
    private LocalDateTime dateDemande;
    @Column(name = "statut")
    private String statut;

    public Demande(Long idDemandeur, String deviseVoulu, BigDecimal montantVoulu, String deviseOfferte,
                   LocalDateTime dateDemande, String statut) {
        this.idDemandeur = idDemandeur;
        this.deviseVoulu = deviseVoulu;
        this.montantVoulu = montantVoulu;
        this.deviseOfferte = deviseOfferte;
        this.dateDemande = dateDemande;
        this.statut = statut;
    }

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long idDemade) {
        this.idDemande = idDemade;
    }

    public String getDeviseVoulu() {
        return deviseVoulu;
    }

    public void setDeviseVoulu(String deviseVoulu) {
        this.deviseVoulu = deviseVoulu;
    }

    public BigDecimal getMontantVoulu() {
        return montantVoulu;
    }

    public void setMontantVoulu(BigDecimal montantVoulu) {
        this.montantVoulu = montantVoulu;
    }

    public String getDeviseOfferte() {
        return deviseOfferte;
    }

    public void setDeviseOfferte(String deviseOfferte) {
        this.deviseOfferte = deviseOfferte;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    @Override
    public String toString() {
        return "Demande{" +
                "idDemade=" + idDemande +
                ", deviseVoulu='" + deviseVoulu + '\'' +
                ", montantVoulu=" + montantVoulu +
                ", deviseOfferte='" + deviseOfferte + '\'' +
                ", dateDemande=" + dateDemande +
                '}';
    }

    public Long getIdDemandeur() {
        return idDemandeur;
    }

    public void setIdDemandeur(Long iddemandeur) {
        this.idDemandeur = iddemandeur;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String termine) {
        this.statut = termine;
    }
//    @Column(name = "motdepassehache")
//    private String motDePasse;
//    @Column(name = "email")
//    private String email;
//    @Column(name = "adresse")
//    private String adresse;
//    @Column(name = "datecreation")
//    private LocalDate dateCreation;
//    @Column(name = "dernieredatemaj")
//    private LocalDate dateMAJ;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "roleutilisateur")
}

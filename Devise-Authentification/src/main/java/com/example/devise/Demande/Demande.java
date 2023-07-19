package com.example.devise.Demande;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "demande")
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
    private Long iddemandeur;
    @Column(name = "deviseVoulu")
    private String deviseVoulu;
    @Column(name = "montantVoulu")
    private BigDecimal montantVoulu;
    @Column(name = "deviseOfferte")
    private String deviseOfferte;
    //@Column(name = "montantOfferte")
    //private BigDecimal montantOfferte;
    @Column(name = "dateDemande")
    private LocalDateTime dateDemande;
    @Column(name = "termine")
    private Boolean termine;

    public Demande(Long iddemandeur, String deviseVoulu, BigDecimal montantVoulu, String deviseOfferte,
                   LocalDateTime dateDemande, Boolean termine) {
        this.iddemandeur = iddemandeur;
        this.deviseVoulu = deviseVoulu;
        this.montantVoulu = montantVoulu;
        this.deviseOfferte = deviseOfferte;
        this.dateDemande = dateDemande;
        this.termine = termine;
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

    public Long getIddemandeur() {
        return iddemandeur;
    }

    public void setIddemandeur(Long iddemandeur) {
        this.iddemandeur = iddemandeur;
    }

    public Boolean getTermine() {
        return termine;
    }

    public void setTermine(Boolean termine) {
        this.termine = termine;
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

package com.example.devise.Offre;

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
@Table(name = "offre")
public class Offre {
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
    @Column(name = "idOffre")
    private Long idOffre;
    @Column(name = "iddemande")
    private Long idDemande;

    @Column(name = "idoffreur")
    private Long idOffreur;
//    @Column(name = "deviseVoulu")
//    private String deviseVoulu;
//    @Column(name = "montantVoulu")
//    private BigDecimal montantVoulu;
//    @Column(name = "deviseOfferte")
//    private String deviseOfferte;
    //@Column(name = "montantOfferte")
    //private BigDecimal montantOfferte;
    @Column(name = "dateOffre")
    private LocalDateTime dateOffre;
    @Column(name = "statutOffre")
    private StatutOffre statutOffre;

    public Offre(Long idDemande, LocalDateTime dateOffre, StatutOffre statutOffre) {
        this.idDemande = idDemande;
        this.dateOffre = dateOffre;
        this.statutOffre = statutOffre;
    }

    public Long getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(Long idOffre) {
        this.idOffre = idOffre;
    }

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long iddemande) {
        this.idDemande = iddemande;
    }

    public LocalDateTime getDateOffre() {
        return dateOffre;
    }

    public void setDateOffre(LocalDateTime dateOffre) {
        this.dateOffre = dateOffre;
    }

    public StatutOffre getStatutOffre() {
        return statutOffre;
    }

    public void setStatutOffre(StatutOffre statutOffre) {
        this.statutOffre = statutOffre;
    }

    public Long getIdOffreur() {
        return idOffreur;
    }

    public void setIdOffreur(Long iddOfreur) {
        this.idOffreur = iddOfreur;
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

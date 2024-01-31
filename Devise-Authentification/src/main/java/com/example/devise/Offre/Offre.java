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
@Table(name = "offredevise")
public class Offre {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "idoffre")
    private Long idOffre;
    @Column(name = "iddemande")
    private Long idDemande;

    @Column(name = "idoffreur")
    private Long idOffreur;
    @Column(name = "dateoffre")
    private LocalDateTime dateOffre;
    @Column(name = "statut")
    private String statutOffre;

    public Offre(Long idDemande, LocalDateTime dateOffre, String statutOffre) {
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

    public String getStatutOffre() {
        return statutOffre;
    }

    public void setStatutOffre(String statutOffre) {
        this.statutOffre = statutOffre;
    }

    public Long getIdOffreur() {
        return idOffreur;
    }

    public void setIdOffreur(Long iddOfreur) {
        this.idOffreur = iddOfreur;
    }

}

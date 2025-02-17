package com.example.devise.Offre;

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
public class OffreResponse {
    private Long idOffre;
    private Long idDemande;
    private Long idDemandeur;
    private String nomPrenomDemandeur;
    private String nomPrenomOffreur;
    private Long idOffreur;
    private String deviseVoulu;
    private BigDecimal montantVoulu;
    private BigDecimal montantOffert;
    private String deviseOfferte;
    private LocalDateTime dateDemande;
    private LocalDateTime dateOffre;
    private String statutOffre;
}

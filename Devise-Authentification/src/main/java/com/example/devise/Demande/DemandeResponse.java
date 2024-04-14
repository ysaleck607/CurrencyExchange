package com.example.devise.Demande;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class DemandeResponse {

    private Long idDemande;
    private Long idDemandeur;
    private String nomPrenomDemandeur;
    private String deviseVoulu;
    private BigDecimal montantVoulu;
    private BigDecimal montantOffert;
    private String deviseOfferte;
    private LocalDateTime dateDemande;
    private String statut;
}

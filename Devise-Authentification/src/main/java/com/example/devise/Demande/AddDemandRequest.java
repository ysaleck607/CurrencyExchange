package com.example.devise.Demande;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddDemandRequest {
    //private Long idDemande;
    private Long idDemandeur;
    private String deviseVoulu;
    private BigDecimal montantVoulu;
    private String deviseOfferte;
    //private LocalDate dateDemande;
}

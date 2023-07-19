package com.example.devise.Offre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddOffreRequest {
    //private Long idDemande;
    private Long idOffreur;
    private Long idDemande;
    //private String deviseVoulu;
    //private BigDecimal montantRecu;
    //private String deviseOfferte;
    //private LocalDate dateDemande;
}

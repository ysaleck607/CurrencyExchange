package com.example.devise.Demande;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddDemandRequest {
    private Long idDemandeur;
    private String deviseVoulue;
    private BigDecimal montantVoulu;
    private BigDecimal montantOffert;
    private String deviseOfferte;
}

package com.example.devise.Demande;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class DemandeService {
    private final DemandeRepository demandeRepository;
    public void addDemand(AddDemandRequest request) {
        var demand = Demande.builder()
                .iddemandeur(request.getIdDemandeur())
                .deviseVoulu(request.getDeviseVoulu())
                .montantVoulu(request.getMontantVoulu())
                .deviseOfferte(request.getDeviseOfferte())
                .dateDemande(LocalDateTime.now())
                .termine(Boolean.FALSE)
                .build();
        demandeRepository.save(demand);
    }

    public void deleteDemand(Long demandId) {
//        var demand = Demande.builder()
//                .iddemandeur(request.getIdDemandeur())
//                .deviseVoulu(request.getDeviseVoulu())
//                .montantVoulu(request.getMontantVoulu())
//                .deviseOfferte(request.getDeviseOfferte())
//                .dateDemande(LocalDateTime.now())
//                .termine(Boolean.FALSE)
//                .build();
        //demandeRepository.save(demand);
        boolean exists = demandeRepository.existsById(demandId);
        if (!exists) {
            throw new IllegalStateException(
                    "La demande avec l'id " + demandId + " n'existe pas"
            );
        }
        demandeRepository.deleteById(demandId);
    }
}

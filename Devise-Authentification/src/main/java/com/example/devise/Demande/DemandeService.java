package com.example.devise.Demande;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandeService {
    private final DemandeRepository demandeRepository;
    public void addDemand(AddDemandRequest request) {
        var demand = Demande.builder()
                .idDemandeur(request.getIdDemandeur())
                .deviseVoulu(request.getDeviseVoulu())
                .montantVoulu(request.getMontantVoulu())
                .deviseOfferte(request.getDeviseOfferte())
                .dateDemande(LocalDateTime.now())
                .statutDemande(StatutDemand.ENATTENTE)
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

    public List<Demande> getDemandsEnCours(Long idUser) {
        //List<StatutDemand> statuts = Arrays.asList(StatutDemand.ENATTENTE, StatutDemand.ACCEPTER, StatutDemand.ENCOURS);
        //return demandeRepository.findByIdDemandeurAndStatutDemande(idUser, statuts);
        return null;
    }

    public List<Optional<Demande>> getDemandEnCours(Long idUser) {
        List<StatutDemand> statuts = Arrays.asList(StatutDemand.ENATTENTE, StatutDemand.ACCEPTER, StatutDemand.ENCOURS,
                StatutDemand.PAYER);
        //List<StatutDemand> statuts = new ArrayList<>();
        //statuts.add(StatutDemand.ENATTENTE);
        //statuts.add(StatutDemand.ENCOURS);
        //statuts.add(StatutDemand.ACCEPTER);
        return demandeRepository.findByIdDemandeurAndStatutDemandeIn(idUser, statuts);
        //return null;
    }

    public List<Optional<Demande>> getDemandTerminer(Long idUser) {
        List<StatutDemand> statuts = Arrays.asList(StatutDemand.ANNULER, StatutDemand.TERMINER);
        return demandeRepository.findByIdDemandeurAndStatutDemandeIn(idUser, statuts);
    }
    @Transactional
    public void payerDeamnde(Long idDemande) {
        Demande demande = demandeRepository.findById(idDemande).orElseThrow(() -> new IllegalStateException(
                "La demande avec l'id " + idDemande + " n'existe pas"
        ));;
        demande.setStatutDemande(StatutDemand.PAYER);
    }

    public void annulerDeamnde(Long idDemande) {
        Demande demande = demandeRepository.findById(idDemande).orElseThrow(() -> new IllegalStateException(
                "La demande avec l'id " + idDemande + " n'existe pas"
        ));;
        demande.setStatutDemande(StatutDemand.ANNULER);
    }

    public void terminerDeamnde(Long idDemande) {
        Demande demande = demandeRepository.findById(idDemande).orElseThrow(() -> new IllegalStateException(
                "La demande avec l'id " + idDemande + " n'existe pas"
        ));;
        demande.setStatutDemande(StatutDemand.TERMINER);
    }
}

package com.example.devise.Demande;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
                .deviseVoulu(request.getDeviseVoulue())
                .montantVoulu(request.getMontantVoulu())
                .deviseOfferte(request.getDeviseOfferte())
                .dateDemande(LocalDateTime.now())
                .statut(StatutDemand.ENCOURS.name())
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

    public List<Optional<Demande>> getDemandUtilisateurEnCours(Long idUser) {
        List<String> statuts = Arrays.asList(StatutDemand.ENATTENTE.name(), StatutDemand.ACCEPTER.name(), StatutDemand.ENCOURS.name(),
                StatutDemand.PAYER.name());
        //List<StatutDemand> statuts = new ArrayList<>();
        //statuts.add(StatutDemand.ENATTENTE);
        //statuts.add(StatutDemand.ENCOURS);
        //statuts.add(StatutDemand.ACCEPTER);
        return demandeRepository.findByIdDemandeurAndStatutIn(idUser, statuts);
        //return null;
    }

    public List<Optional<Demande>> getDemandUtilisateurTerminer(Long idUser) {
        List<String> statuts = Arrays.asList(StatutDemand.ANNULER.name(), StatutDemand.TERMINER.name());
        return demandeRepository.findByIdDemandeurAndStatutIn(idUser, statuts);
    }
    @Transactional
    public void payerDeamnde(Long idDemande) {
        Demande demande = demandeRepository.findById(idDemande).orElseThrow(() -> new IllegalStateException(
                "La demande avec l'id " + idDemande + " n'existe pas"
        ));;
        demande.setStatut(StatutDemand.PAYER.name());
    }

    public void annulerDeamnde(Long idDemande) {
        Demande demande = demandeRepository.findById(idDemande).orElseThrow(() -> new IllegalStateException(
                "La demande avec l'id " + idDemande + " n'existe pas"
        ));;
        demande.setStatut(StatutDemand.ANNULER.name());
    }

    public void terminerDeamnde(Long idDemande) {
        Demande demande = demandeRepository.findById(idDemande).orElseThrow(() -> new IllegalStateException(
                "La demande avec l'id " + idDemande + " n'existe pas"
        ));;
        demande.setStatut(StatutDemand.TERMINER.name());
    }

    public List<Optional<Demande>> getDemandAutresUtilisateurs(Long idUser) {
        List<String> statuts = Arrays.asList(StatutDemand.ENATTENTE.name(), StatutDemand.ENCOURS.name());
        return demandeRepository.findByIdDemandeurNotAndStatutIn(idUser, statuts);
    }

    public List<Optional<Demande>> getDemandesUtilisateur(Long idUser) {
        return demandeRepository.findByIdDemandeur(idUser);
    }
}

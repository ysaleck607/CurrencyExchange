package com.example.devise.Demande;

import com.example.devise.Config.EmailService;
import com.example.devise.Offre.Offre;
import com.example.devise.Offre.OffreRepository;
import com.example.devise.Offre.StatutOffre;
import com.example.devise.Utilisateur.Utilisateur;
import com.example.devise.Utilisateur.UtilisateurRepository;
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
    private final UtilisateurRepository utilisateurRepository;
    private final OffreRepository offreRepository;
    private final EmailService emailService;

    public void addDemand(AddDemandRequest request) {
        var demand = Demande.builder()
                .idDemandeur(request.getIdDemandeur())
                .deviseVoulu(request.getDeviseVoulue())
                .montantVoulu(request.getMontantVoulu())
                .montantOffert(request.getMontantOffert())
                .deviseOfferte(request.getDeviseOfferte())
                .dateDemande(LocalDateTime.now())
                .statut(StatutDemand.ENATTENTE.name())
                .build();
        demandeRepository.save(demand);
    }

    public void deleteDemand(Long demandId) {
        boolean exists = demandeRepository.existsById(demandId);
        if (!exists) {
            throw new IllegalStateException(
                    "La demande avec l'id " + demandId + " n'existe pas"
            );
        }
        demandeRepository.deleteById(demandId);
    }

    public List<Optional<Demande>> getDemandUtilisateurEnCours(Long idUser) {
        List<String> statuts = Arrays.asList(StatutDemand.ENATTENTE.name(), StatutDemand.ACCEPTER.name(), StatutDemand.ENCOURS.name(),
                StatutDemand.PAYER.name());
        return demandeRepository.findByIdDemandeurAndStatutIn(idUser, statuts);
    }

    public List<Optional<Demande>> getDemandUtilisateurTerminer(Long idUser) {
        List<String> statuts = Arrays.asList(StatutDemand.ANNULER.name(), StatutDemand.TERMINER.name());
        return demandeRepository.findByIdDemandeurAndStatutIn(idUser, statuts);
    }
    @Transactional
    public void payerDeamnde(Long idDemande) {
        Demande demande = demandeRepository.findById(idDemande).orElseThrow(() -> new RuntimeException("Demande not found"));

        // Check if any offre has been accepted or paid for this demande
        List<String> statuts = Arrays.asList(StatutOffre.ACCEPTER.name(), StatutOffre.PAYER.name());
        List<Offre> offres = offreRepository.findByIdDemandeAndStatutOffreIn(idDemande, statuts);

        if (!offres.isEmpty()) {

            Utilisateur utilisateur = utilisateurRepository.findById(offres.get(0).getIdOffreur())
                    .orElseThrow(() -> new IllegalStateException(
                            "L'utilisateur avec l'id " + offres.get(0).getIdOffreur() + " n'existe pas"
                    ));
            // Send email notification to the offreur (Replace this with actual email sending code)
            String offreurEmail = utilisateur.getEmail();
            String emailContent = "Vous avez été payé pour votre offre sur la demande.";
            emailService.sendEmail(offreurEmail, "Notification de paiement", emailContent);

            // Update the statut of the demande to "PAYER"
            demande.setStatut(StatutDemand.PAYER.name());
            if(offres.get(0).getStatutOffre().equals(StatutOffre.PAYER.name())){
                offres.get(0).setStatutOffre(StatutOffre.TERMINER.name());
                demande.setStatut(StatutDemand.TERMINER.name());
            }
        } else {
            throw new RuntimeException("Demande can only be paid in ENCOURS status.");
        }
    }
    @Transactional
    public void annulerDeamnde(Long idDemande) {
        Demande demande = demandeRepository.findById(idDemande).orElseThrow(() -> new RuntimeException("Demande not found"));

        // Check if any offre has been accepted for this demande
        List<Offre> offres = offreRepository.findByIdDemandeAndStatutOffre(idDemande, StatutOffre.ACCEPTER.name());

        if (offres.isEmpty()) {
            // No offre has been accepted, update the statut of all offres related to this demande to "Refuser"
            List<Object[]> offresToUpdate = offreRepository.findByIdDemande(idDemande);
            for (Object[] offre : offresToUpdate) {
                ((Offre)offre[0]).setStatutOffre(StatutOffre.REFUSER.name());

                // Envoyer un courriel à l'utilisateur
                String recipientEmail = (String) offre[3];
                String subject = "Votre offre a ete refuser apres l'annulation de la demande";
                String message = "Votre offre a ete refuser apres l'annulation de la demande. Veuillez vérifier votre " +
                        "compte afin de trouver une autre demande.";
                emailService.sendEmail(recipientEmail, subject, message);
            }

            demande.setStatut(StatutDemand.ANNULER.name());
        } else {
            throw new RuntimeException("Cannot annuler demande with accepted offre");
        }
    }
    @Transactional
    public void accepterOffre(Long offreId) {
        Offre offre = offreRepository.findById(offreId).orElseThrow(() -> new RuntimeException("Offre not found"));
        Demande demande = demandeRepository.findById(offre.getIdDemande()).orElseThrow(() -> new RuntimeException("Demande not found"));

        if (demande.getStatut().equals(StatutDemand.ENCOURS.name())) {
            if (offre.getStatutOffre().equals(StatutOffre.ENATTENTE.name())) {
                // Accept the offre
                offre.setStatutOffre(StatutOffre.ACCEPTER.name());

                Utilisateur utilisateur = utilisateurRepository.findById(offre.getIdOffreur())
                        .orElseThrow(() -> new IllegalStateException(
                                "L'utilisateur avec l'id " + offre.getIdOffreur() + " n'existe pas"
                        ));
                // Send email notification to the offreur (Replace this with actual email sending code)
                String offreurEmail = utilisateur.getEmail();
                String emailContent = "Vous offre a ete accepte sur une demande.";
                emailService.sendEmail(offreurEmail, "Notification d'offre acceptee", emailContent);

                // Set the statut of the demande to "ACCEPTER"
                demande.setStatut(StatutDemand.ACCEPTER.name());
            } else {
                throw new RuntimeException("The selected offre cannot be accepted.");
            }
        } else {
            throw new RuntimeException("Demande can only accept an offre if it has offers.");
        }
    }
    @Transactional
    public void refuserOffre(Long offreId) {
        Offre offre = offreRepository.findById(offreId).orElseThrow(() -> new RuntimeException("Offre not found"));
        Demande demande = demandeRepository.findById(offre.getIdDemande()).orElseThrow(() -> new RuntimeException("Demande not found"));
            if (offre.getStatutOffre().equals(StatutOffre.ENATTENTE.name())) {
                offre.setStatutOffre(StatutOffre.REFUSER.name());

                Utilisateur utilisateur = utilisateurRepository.findById(offre.getIdOffreur())
                        .orElseThrow(() -> new IllegalStateException(
                                "L'utilisateur avec l'id " + offre.getIdOffreur() + " n'existe pas"
                        ));
                // Send email notification to the offreur (Replace this with actual email sending code)
                String offreurEmail = utilisateur.getEmail();
                String emailContent = "Vous offre a ete refuser sur une demande.";
                emailService.sendEmail(offreurEmail, "Notification d'offre refusee", emailContent);
            } else {
                throw new RuntimeException("The selected offre cannot be refused.");
            }
    }

    @Transactional
    public void terminerDeamnde(Long idDemande) {
        Demande demande = demandeRepository.findById(idDemande).orElseThrow(() -> new IllegalStateException(
                "La demande avec l'id " + idDemande + " n'existe pas"
        ));;
        demande.setStatut(StatutDemand.TERMINER.name());
    }

    public List<DemandeResponse> getDemandAutresUtilisateurs(Long idUser) {
        List<Object[]> demandes = demandeRepository.getDemandsWithDemandeurNamesExceptUser(idUser);
        List<DemandeResponse> demandesRes = new ArrayList<>();

        for (Object[] demandeInfo : demandes) {
            Demande demande = (Demande) demandeInfo[0];
            String nom = (String) demandeInfo[1];
            String prenom = (String) demandeInfo[2];

            var demand = DemandeResponse.builder()
                    .idDemande(demande.getIdDemande())
                    .idDemandeur(demande.getIdDemandeur())
                    .nomPrenomDemandeur(nom + " " + prenom)
                    .deviseVoulu(demande.getDeviseVoulu())
                    .montantVoulu(demande.getMontantVoulu())
                    .montantOffert(demande.getMontantOffert())
                    .deviseOfferte(demande.getDeviseOfferte())
                    .dateDemande(demande.getDateDemande())
                    .statut(demande.getStatut())
                    .build();
            demandesRes.add(demand);
        }

        return demandesRes;
    }

    public List<DemandeResponse> getDemandesUtilisateur(Long idUser) {
        List<Object[]> demandes = demandeRepository.getDemandesWithNamesByUserId(idUser);
        List<DemandeResponse> demandesRes = new ArrayList<>();

        for (Object[] demandeInfo : demandes) {
            Demande demande = (Demande) demandeInfo[0];
            String nom = (String) demandeInfo[1];
            String prenom = (String) demandeInfo[2];

            var demand = DemandeResponse.builder()
                    .idDemande(demande.getIdDemande())
                    .idDemandeur(demande.getIdDemandeur())
                    .nomPrenomDemandeur(nom + " " + prenom)
                    .deviseVoulu(demande.getDeviseVoulu())
                    .montantVoulu(demande.getMontantVoulu())
                    .montantOffert(demande.getMontantOffert())
                    .deviseOfferte(demande.getDeviseOfferte())
                    .dateDemande(demande.getDateDemande())
                    .statut(demande.getStatut())
                    .build();
            demandesRes.add(demand);
        }
        return demandesRes;
    }
}

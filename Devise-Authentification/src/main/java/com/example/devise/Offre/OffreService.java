package com.example.devise.Offre;

import com.example.devise.Config.EmailService;
import com.example.devise.Demande.Demande;
import com.example.devise.Demande.DemandeRepository;
import com.example.devise.Demande.StatutDemand;
import com.example.devise.Utilisateur.Utilisateur;
import com.example.devise.Utilisateur.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OffreService {

    private final OffreRepository offreRepository;
    private final DemandeRepository demandeRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EmailService emailService;
    @Transactional
    public void addOffre(AddOffreRequest request) {
        // Logique pour ajouter l'offre à la base de données
        boolean offreExistante = offreRepository.existsByIdDemandeAndAndIdOffreur(request.getIdDemande(), request.getIdOffreur());

        if (!offreExistante) {
            var offre = Offre.builder()
                    .idDemande(request.getIdDemande())
                    .idOffreur(request.getIdOffreur())
                    .dateOffre(LocalDateTime.now())
                    .statutOffre(StatutOffre.ENATTENTE.name())
                    .build();
            offreRepository.save(offre);
            // Récupérer l'utilisateur de la demande
            Demande demande = demandeRepository.findByIdDemande(request.getIdDemande());
            if(demande.getStatut().equals(StatutDemand.ENATTENTE.name())) {
                demande.setStatut(StatutDemand.ENCOURS.name());
            }
            Long idDemandeur = demandeRepository.findByIdDemande(request.getIdDemande()).getIdDemandeur();
            Utilisateur demandeur = utilisateurRepository.findByIdUtilisateur(idDemandeur);

            // Envoyer un courriel à l'utilisateur
            String recipientEmail = demandeur.getEmail();
            String subject = "Nouvelle offre pour votre demande";
            String message = "Une nouvelle offre a été ajoutée à votre demande. Veuillez vérifier votre compte.";
            emailService.sendEmail(recipientEmail, subject, message);

        } else {
            throw new RuntimeException("L'utilisateur a déjà fait une offre sur cette demande.");
        }
    }

    public void deleteOffer(Long offerId) {
        boolean exists = offreRepository.existsById(offerId);
        if (!exists) {
            throw new IllegalStateException(
                    "L'offre avec l'id " + offerId + " n'existe pas"
            );
        }
        offreRepository.deleteById(offerId);
    }

    public List<Optional<Offre>> getOffreEnCours(Long idUser) {
        List<String> statuts = Arrays.asList(StatutOffre.ENATTENTE.name(), StatutOffre.ACCEPTER.name(),
                StatutOffre.PAYER.name());
        return offreRepository.findByIdOffreurAndAndStatutOffreIn(idUser, statuts);
    }

    public List<Optional<Offre>> getOffreTerminer(Long idUser) {
        List<String> statuts = Arrays.asList(StatutOffre.TERMINER.name(), StatutOffre.ANNULER.name());
        return offreRepository.findByIdOffreurAndAndStatutOffreIn(idUser, statuts);

    }
    @Transactional
    public void payerOffre(Long idOffre) {
        Offre offre = offreRepository.findById(idOffre).orElseThrow(() -> new RuntimeException("Offre not found"));
        // Check if any offre has been accepted for this demande

        if (offre.getStatutOffre().equals(StatutOffre.ACCEPTER.name())) {

            Demande demande = demandeRepository.findById(offre.getIdDemande()).orElseThrow(() -> new RuntimeException("Demande not found"));
            Utilisateur utilisateur = utilisateurRepository.findById(demande.getIdDemandeur())
                    .orElseThrow(() -> new IllegalStateException(
                            "L'utilisateur avec l'id " + demande.getIdDemandeur() + " n'existe pas"
                    ));
            String offreurEmail = utilisateur.getEmail();
            String emailContent = "Vous avez été payé pour votre demande.";
            emailService.sendEmail(offreurEmail, "Notification de paiement", emailContent);

            // Update the statut of the demande to "PAYER"
            offre.setStatutOffre(StatutDemand.PAYER.name());

            if(demande.getStatut().equals(StatutDemand.PAYER.name())){
                offre.setStatutOffre(StatutOffre.TERMINER.name());
                demande.setStatut(StatutDemand.TERMINER.name());
            }
        } else {
            throw new RuntimeException("Demande can only be paid in ENCOURS status.");
        }


    }

    public void annulerOffre(Long idOffre) {
        Offre offre = offreRepository.findById(idOffre).orElseThrow(() -> new RuntimeException("Offre not found"));
        Demande demande = demandeRepository.findById(offre.getIdDemande()).orElseThrow(() -> new RuntimeException("Demande not found"));

        if (!offre.getStatutOffre().equals(StatutOffre.ACCEPTER.name())) {
            Utilisateur utilisateur = utilisateurRepository.findByIdUtilisateur(demande.getIdDemandeur());

                // Envoyer un courriel à l'utilisateur
                String recipientEmail = utilisateur.getEmail();
                String subject = "Offre annule sur votre demande";
                String message = "Une offre a ete annule sur votre demande. Veuillez vérifier votre compte";
                emailService.sendEmail(recipientEmail, subject, message);

            offre.setStatutOffre(StatutDemand.ANNULER.name());
        } else {
            throw new RuntimeException("Cannot annuler demande with accepted offre");
        }
    }

    public void terminerOffre(Long idOffre) {
        Offre offre = offreRepository.findById(idOffre).orElseThrow(() -> new IllegalStateException(
                "L'offre avec l'id " + idOffre + " n'existe pas"
        ));;
        offre.setStatutOffre(StatutOffre.TERMINER.name());
    }

    public List<OffreResponse> getOffresDemade(Long idDemande) {

        List<Object[]> offres = offreRepository.getOffresWithNamesByDemandeId(idDemande);
        List<OffreResponse> offresRes = new ArrayList<>();

        for (Object[] offreInfo : offres) {
            Offre offre = (Offre) offreInfo[0];
            String nomDemandeur = (String) offreInfo[1];
            String prenomDemandeur = (String) offreInfo[2];
            String nomOffreur = (String) offreInfo[3];
            String prenomOffreur = (String) offreInfo[4];
            String deviseVoulu = (String) offreInfo[6];
            BigDecimal montantVoulu = (BigDecimal) offreInfo[7];
            String deviseOfferte = (String) offreInfo[5];
            LocalDateTime dateDemande = (LocalDateTime) offreInfo[8];
            BigDecimal montantOffert = (BigDecimal) offreInfo[9];

            var offer = OffreResponse.builder()
                    .idOffre(offre.getIdOffre())
                    .idDemande(offre.getIdDemande())
                    .nomPrenomDemandeur(nomDemandeur + " " + prenomDemandeur)
                    .idOffreur(offre.getIdOffreur())
                    .nomPrenomOffreur(nomOffreur + " " + prenomOffreur)
                    .deviseVoulu(deviseVoulu)
                    .montantVoulu(montantVoulu)
                    .montantOffert(montantOffert)
                    .deviseOfferte(deviseOfferte)
                    .dateDemande(dateDemande)
                    .dateOffre(offre.getDateOffre())
                    .statutOffre(offre.getStatutOffre())
                    .build();
            offresRes.add(offer);
        }

        return offresRes;
    }

    public List<OffreResponse> getOffresUtilisateur(Long idUser) {
        List<Object[]> offres = offreRepository.getOffresWithNamesByUtilisateurId(idUser);
        List<OffreResponse> offresRes = new ArrayList<>();

        for (Object[] offreInfo : offres) {
            Offre offre = (Offre) offreInfo[0];
            String nomDemandeur = (String) offreInfo[1];
            String prenomDemandeur = (String) offreInfo[2];
            String nomOffreur = (String) offreInfo[3];
            String prenomOffreur = (String) offreInfo[4];
            String deviseVoulu = (String) offreInfo[6];
            BigDecimal montantVoulu = (BigDecimal) offreInfo[7];
            String deviseOfferte = (String) offreInfo[5];
            LocalDateTime dateDemande = (LocalDateTime) offreInfo[8];
            Long idDemandeur = (Long) offreInfo[9];
            BigDecimal montantOffert = (BigDecimal) offreInfo[10];

            var offer = OffreResponse.builder()
                    .idOffre(offre.getIdOffre())
                    .idDemande(offre.getIdDemande())
                    .nomPrenomDemandeur(nomDemandeur + " " + prenomDemandeur)
                    .idOffreur(offre.getIdOffreur())
                    .nomPrenomOffreur(nomOffreur + " " + prenomOffreur)
                    .deviseVoulu(deviseVoulu)
                    .montantVoulu(montantVoulu)
                    .montantOffert(montantOffert)
                    .deviseOfferte(deviseOfferte)
                    .dateDemande(dateDemande)
                    .dateOffre(offre.getDateOffre())
                    .statutOffre(offre.getStatutOffre())
                    .idDemandeur(idDemandeur)
                    .build();
            offresRes.add(offer);
        }

        return offresRes;
    }
}

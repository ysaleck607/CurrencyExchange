package com.example.devise.Offre;

import com.example.devise.Config.EmailService;
import com.example.devise.Demande.Demande;
import com.example.devise.Demande.DemandeRepository;
import com.example.devise.Demande.StatutDemand;
import com.example.devise.Utilisateur.Utilisateur;
import com.example.devise.Utilisateur.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void addOffre(AddOffreRequest request) {
        // Logique pour ajouter l'offre à la base de données
        var offre = Offre.builder()
                .idDemande(request.getIdDemande())
                .idOffreur(request.getIdOffreur())
                .dateOffre(LocalDateTime.now())
                .statutOffre(StatutOffre.ENATTENTE.name())
                .build();
        offreRepository.save(offre);
        // Récupérer l'utilisateur de la demande (supposons qu'il a une adresse e-mail dans sa classe Utilisateur)
        Long idDemandeur = demandeRepository.findByIdDemande(request.getIdDemande()).getIdDemandeur();
        Utilisateur demandeur = utilisateurRepository.findByIdUtilisateur(idDemandeur);

        // Envoyer un courriel à l'utilisateur
        String recipientEmail = demandeur.getEmail();
        String subject = "Nouvelle offre pour votre demande";
        String message = "Une nouvelle offre a été ajoutée à votre demande. Veuillez vérifier votre compte.";
        emailService.sendEmail(recipientEmail, subject, message);
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
        //List<StatutDemand> statuts = new ArrayList<>();
        //statuts.add(StatutDemand.ENATTENTE);
        //statuts.add(StatutDemand.ENCOURS);
        //statuts.add(StatutDemand.ACCEPTER);
        return offreRepository.findByIdOffreurAndAndStatutOffreIn(idUser, statuts);
        //return null;
    }

    public List<Optional<Offre>> getOffreTerminer(Long idUser) {
        List<String> statuts = Arrays.asList(StatutOffre.TERMINER.name(), StatutOffre.ANNULER.name());
        //List<StatutDemand> statuts = new ArrayList<>();
        //statuts.add(StatutDemand.ENATTENTE);
        //statuts.add(StatutDemand.ENCOURS);
        //statuts.add(StatutDemand.ACCEPTER);
        return offreRepository.findByIdOffreurAndAndStatutOffreIn(idUser, statuts);
        //return null;
    }

    public void payerOffre(Long idOffre) {
        Offre offre = offreRepository.findById(idOffre).orElseThrow(() -> new IllegalStateException(
                "L'offre avec l'id " + idOffre + " n'existe pas"
        ));;
        offre.setStatutOffre(StatutOffre.PAYER.name());
    }

    public void annulerOffre(Long idOffre) {
        Offre offre = offreRepository.findById(idOffre).orElseThrow(() -> new IllegalStateException(
                "L'offre avec l'id " + idOffre + " n'existe pas"
        ));;
        offre.setStatutOffre(StatutOffre.ANNULER.name());
    }

    public void terminerOffre(Long idOffre) {
        Offre offre = offreRepository.findById(idOffre).orElseThrow(() -> new IllegalStateException(
                "L'offre avec l'id " + idOffre + " n'existe pas"
        ));;
        offre.setStatutOffre(StatutOffre.TERMINER.name());
    }

    public List<Optional<Offre>> getOffresDemade(Long idDemande) {
        return offreRepository.findByIdDemande(idDemande);
    }
}

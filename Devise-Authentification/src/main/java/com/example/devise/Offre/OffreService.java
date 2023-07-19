package com.example.devise.Offre;

import com.example.devise.Config.EmailService;
import com.example.devise.Demande.Demande;
import com.example.devise.Demande.DemandeRepository;
import com.example.devise.Utilisateur.Utilisateur;
import com.example.devise.Utilisateur.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
                .iddOfreur(request.getIdOffreur())
                .dateOffre(LocalDateTime.now())
                .statutOffre(StatutOffre.NONACCEPTE)
                .build();
        offreRepository.save(offre);
        // Récupérer l'utilisateur de la demande (supposons qu'il a une adresse e-mail dans sa classe Utilisateur)
        Utilisateur demandeur = utilisateurRepository.getReferenceById(request.getIdDemande());

        // Envoyer un courriel à l'utilisateur
        String recipientEmail = demandeur.getEmail();
        String subject = "Nouvelle offre pour votre demande";
        String message = "Une nouvelle offre a été ajoutée à votre demande. Veuillez vérifier votre compte.";
        emailService.sendEmail(recipientEmail, subject, message);
    }
}

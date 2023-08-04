package com.example.devise.Offre;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Offres")
@RequiredArgsConstructor
public class OffreController {
    private final OffreService offreService;

    @PostMapping("/addoffre")
    ResponseEntity<String> addOffre(@RequestBody AddOffreRequest request) {
        ResponseEntity<String> response;
        try {
            offreService.addOffre(request);
        }
        catch (Exception e) {
//            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
//                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
//                return response;
//            }

        }
        response = new ResponseEntity<>("Offre enregistre avec succes",  HttpStatus.OK);
        return response;
    }

    @PostMapping("/deleteoffer/{offerId}")
    ResponseEntity<String> deleteOffre(@PathVariable("offerId") Long offerId) {
        ResponseEntity<String> response;
        try {
            offreService.deleteOffer(offerId);
        }
        catch (Exception e) {
//            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
//                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
//                return response;
//            }

        }
        response = new ResponseEntity<>("Offre supprimée avec succes",  HttpStatus.OK);
        return response;
    }

    @GetMapping("/getdoffreencours/{idUser}")
    ResponseEntity<?> getOffreEnCours(@PathVariable("idUser") Long idUser) {
        ResponseEntity<?> response;
        List<Optional<Offre>> offreEnCours = null;
        try {
            offreEnCours = offreService.getOffreEnCours(idUser);
        } catch (Exception e) {
//            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
//                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
//                return response;
//            }

        }
        response = new ResponseEntity<>(offreEnCours, HttpStatus.OK);
        return response;
    }

    @GetMapping("/getdoffresutilisateur/{idUser}")
    ResponseEntity<?> getOffreUtilisateur(@PathVariable("idUser") Long idUser) {
        ResponseEntity<?> response;
        List<OffreResponse> offreUtilisateur = null;
        try {
            offreUtilisateur = offreService.getOffresUtilisateur(idUser);
        } catch (Exception e) {
//            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
//                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
//                return response;
//            }

        }
        response = new ResponseEntity<>(offreUtilisateur, HttpStatus.OK);
        return response;
    }

    @GetMapping("/getoffresdamande/{idDemande}")
    ResponseEntity<?> getOffresDemade(@PathVariable("idDemande") Long idDemande) {
        ResponseEntity<?> response;
        List<OffreResponse> offres = null;
        try {
            offres = offreService.getOffresDemade(idDemande);
        } catch (Exception e) {
//            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
//                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
//                return response;
//            }

        }
        response = new ResponseEntity<>(offres, HttpStatus.OK);
        return response;
    }

    @GetMapping("/getoffretermine/{idUser}")
    ResponseEntity<?> getOffreTermine(@PathVariable("idUser") Long idUser) {
        ResponseEntity<?> response;
        List<Optional<Offre>> offreTerminer = null;
        try {
            offreTerminer = offreService.getOffreTerminer(idUser);
        } catch (Exception e) {
//            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
//                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
//                return response;
//            }

        }
        response = new ResponseEntity<>(offreTerminer, HttpStatus.OK);
        return response;
    }

    @PutMapping("/payerOffre/{idOffre}")
    public ResponseEntity<String> payerDeamnde(@PathVariable("idOffre") Long idOffre) {
        try {
            offreService.payerOffre(idOffre);
            return ResponseEntity.ok("Demande annulée avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/annuleroffre/{idOffre}")
    public void annulerDeamnde(@PathVariable("idOffre") Long idOffre) {
        offreService.annulerOffre(idOffre);

    }

    @PutMapping("/termineroffre/{idOffre}")
    public void terminerDeamnde(@PathVariable("idOffre") Long idOffre) {
        offreService.terminerOffre(idOffre);

    }
}

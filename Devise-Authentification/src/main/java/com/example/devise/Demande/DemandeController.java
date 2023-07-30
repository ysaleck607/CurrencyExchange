package com.example.devise.Demande;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins =  "http://localhost:63342")
@RestController
@RequestMapping(path = "api/v1/demandes")
@RequiredArgsConstructor
public class DemandeController {
    private final DemandeService demandeService;

    @PostMapping("/adddemand")
    ResponseEntity<String> addDemand(@RequestBody AddDemandRequest request) {
        ResponseEntity<String> response;
        try {
            demandeService.addDemand(request);
        }
        catch (Exception e) {
//            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
//                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
//                return response;
//            }

        }
        response = new ResponseEntity<>("Demande enregistre avec succes",  HttpStatus.OK);
        return response;
    }
    @PostMapping("/deletedemand/{demandId}")
    ResponseEntity<String> deleteDemand(@PathVariable("demandId") Long demandId) {
        ResponseEntity<String> response;
        try {
            demandeService.deleteDemand(demandId);
        }
        catch (Exception e) {
//            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
//                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
//                return response;
//            }

        }
        response = new ResponseEntity<>("Demande supprimée avec succes",  HttpStatus.OK);
        return response;
    }

    @GetMapping("/getdemandencours/{idUser}")
    ResponseEntity<?> getDemandeEnCours(@PathVariable("idUser") Long idUser) {
        ResponseEntity<?> response;
        List<Optional<Demande>> demandeEnCours = null;
        try {
            demandeEnCours = demandeService.getDemandEnCours(idUser);
        } catch (Exception e) {
//            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
//                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
//                return response;
//            }

        }
        response = new ResponseEntity<>(demandeEnCours, HttpStatus.OK);
        return response;
    }

    @GetMapping("/getdemandetermine/{idUser}")
    ResponseEntity<?> getDemandTermine(@PathVariable("idUser") Long idUser) {
        ResponseEntity<?> response;
        List<Optional<Demande>> demandeTerminer = null;
        try {
            demandeTerminer = demandeService.getDemandTerminer(idUser);
        } catch (Exception e) {
//            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
//                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
//                return response;
//            }

        }
        response = new ResponseEntity<>(demandeTerminer, HttpStatus.OK);
        return response;
    }

    @PutMapping("/payerdemande/{idDemande}")
    public void payerDeamnde(@PathVariable("idDemande") Long idDemande) {
        demandeService.payerDeamnde(idDemande);

    }

    @PutMapping("/annulerdemande/{idDemande}")
    public void annulerDeamnde(@PathVariable("idDemande") Long idDemande) {
        demandeService.annulerDeamnde(idDemande);

    }

    @PutMapping("/terminerdemande/{idDemande}")
    public void terminerDeamnde(@PathVariable("idDemande") Long idDemande) {
        demandeService.terminerDeamnde(idDemande);

    }
}

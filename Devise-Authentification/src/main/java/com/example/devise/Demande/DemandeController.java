package com.example.devise.Demande;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins =  "http://localhost:63343")
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

        }
        response = new ResponseEntity<>("Demande supprimée avec succes",  HttpStatus.OK);
        return response;
    }

    @GetMapping("/getdemandes/{idUser}")
    ResponseEntity<?> getDemandesUtilisateur(@PathVariable("idUser") Long idUser) {
        ResponseEntity<?> response;
        List<DemandeResponse> demandes = null;
        try {
            demandes = demandeService.getDemandesUtilisateur(idUser);
        } catch (Exception e) {

        }
        response = new ResponseEntity<>(demandes, HttpStatus.OK);
        return response;
    }

    @GetMapping("/getdemandencours/{idUser}")
    ResponseEntity<?> getDemandeUtilisateurEnCours(@PathVariable("idUser") Long idUser) {
        ResponseEntity<?> response;
        List<Optional<Demande>> demandeEnCours = null;
        try {
            demandeEnCours = demandeService.getDemandUtilisateurEnCours(idUser);
        } catch (Exception e) {

        }
        response = new ResponseEntity<>(demandeEnCours, HttpStatus.OK);
        return response;
    }

    @GetMapping("/getdemandetermine/{idUser}")
    ResponseEntity<?> getDemandUtlisateurTermine(@PathVariable("idUser") Long idUser) {
        ResponseEntity<?> response;
        List<Optional<Demande>> demandeTerminer = null;
        try {
            demandeTerminer = demandeService.getDemandUtilisateurTerminer(idUser);
        } catch (Exception e) {

        }
        response = new ResponseEntity<>(demandeTerminer, HttpStatus.OK);
        return response;
    }

    @GetMapping("/getdemandeautresutilisateurs/{idUser}")
    ResponseEntity<?> getDemandAutresUtlisateurs(@PathVariable("idUser") Long idUser) {
        ResponseEntity<?> response;
        List<DemandeResponse> demandeAutresUtilisateurs = null;
        try {
            demandeAutresUtilisateurs = demandeService.getDemandAutresUtilisateurs(idUser);
        } catch (Exception e) {

        }
        response = new ResponseEntity<>(demandeAutresUtilisateurs, HttpStatus.OK);
        return response;
    }

    @PutMapping("/payerdemande/{idDemande}")
    public ResponseEntity<String> payerDeamnde(@PathVariable("idDemande") Long idDemande) {
        try {
            demandeService.payerDeamnde(idDemande);
            return ResponseEntity.ok("Demande paye avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/annulerdemande/{idDemande}")
    public ResponseEntity<String> annulerDeamnde(@PathVariable("idDemande") Long idDemande) {
        try {
            demandeService.annulerDeamnde(idDemande);
            return ResponseEntity.ok("Demande annulée avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/accepterOffre/{offreId}")
    public ResponseEntity<String> accepterOffre(@PathVariable Long offreId) {
        try {
            demandeService.accepterOffre(offreId);
            return ResponseEntity.ok("Offre acceptée avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/refuserOffre/{offreId}")
    public ResponseEntity<String> refuserOffre(@PathVariable Long offreId) {
        try {
            demandeService.refuserOffre(offreId);
            return ResponseEntity.ok("Offre refusée avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/terminerdemande/{idDemande}")
    public void terminerDeamnde(@PathVariable("idDemande") Long idDemande) {
        demandeService.terminerDeamnde(idDemande);

    }

}

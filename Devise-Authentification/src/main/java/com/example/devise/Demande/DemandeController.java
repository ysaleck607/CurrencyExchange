package com.example.devise.Demande;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<String> deleteDemand(@PathVariable("utilisateurId") Long demandId) {
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
}

package com.example.devise.Offre;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/0ffres")
@RequiredArgsConstructor
public class OffreController {
    private final OffreService offreService;

    @PostMapping("/addoffre")
    ResponseEntity<String> addDemand(@RequestBody AddOffreRequest request) {
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
}

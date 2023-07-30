package com.example.devise.Utilisateur;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping(path = "api/v1/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurControleur {
    private final  UtilisateurService utilisateurService;

    @GetMapping("/list")
    public List<Utilisateur> obtenirListUtilisateurs() {
        return utilisateurService.obtenirListUtilisateurs();
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        //utilisateurService.enregistrerNouvelUtilisateur(utilisateur);
        ResponseEntity<String> response;
        try {
            AuthenticationResponse authenticationResponse = utilisateurService.register(request);
        }
        catch (Exception e) {
            if (e.getMessage().contains("Email deja utilise par un autre utilisateur")) {
                response = new ResponseEntity<>("Email deja utilise par un autre utilisateur",  HttpStatus.BAD_REQUEST);
                return response;
            }

        }
        response = new ResponseEntity<>("Utilisateur enregistre avec succes",  HttpStatus.OK);
        return response;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(utilisateurService.authenticate(request));
    }
    @DeleteMapping(path = "/delete/{utilisateurId}")
    public ResponseEntity<String> deleteUser(@PathVariable("utilisateurId") Long userId) {
        ResponseEntity<String> response;
        try {
            utilisateurService.deleteUser(userId);
        }
        catch (Exception e) {
            if (e.getMessage().contains("L'utilisateur avec l'id")) {
                response = new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
                return response;
            }

        }
        response = new ResponseEntity<>("L'tilisateur " + userId + "a ete supprime avec succes",  HttpStatus.OK);
        return response;
    }
    @PutMapping(path = "/update/{utilisateurId}")
    public void updateUser(
            @PathVariable("utilisateurId") Long userId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password) {
        utilisateurService.updateUser(userId, email, password);
    }
}

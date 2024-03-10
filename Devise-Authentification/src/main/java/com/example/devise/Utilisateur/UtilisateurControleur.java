package com.example.devise.Utilisateur;

import com.example.devise.Utilisateur.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63343")
@RestController
@RequestMapping(path = "api/v1/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurControleur {
    private final  UtilisateurService utilisateurService;

    @GetMapping("/getlistutilisateurs")
    public List<Utilisateur> obtenirListUtilisateurs() {
        return utilisateurService.obtenirListUtilisateurs();
    }

    @GetMapping("/getutilisateur")
    public Optional<Utilisateur> obtenirUtilisateur(@PathVariable("utilisateurId") Long idUser) {
        return utilisateurService.obtenirUtilisateur(idUser);
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

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public Utilisateur addUser(
            @Payload String email
    ) {
        return utilisateurService.connetToChat(email);

    }

//    @MessageMapping("/user.disconnectUser")
//    @SendTo("/user/public")
//    public User disconnectUser(
//            @Payload User user
//    ) {
//        userService.disconnect(user);
//        return user;
//    }

//    @GetMapping("/users")
//    public ResponseEntity<List<User>> findConnectedUsers() {
//        return ResponseEntity.ok(userService.findConnectedUsers());
//    }
}

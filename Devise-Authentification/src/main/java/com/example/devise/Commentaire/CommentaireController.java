package com.example.devise.Commentaire;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins =  "http://localhost:63342")
@RestController
@RequestMapping(path = "api/v1/commentaires")
@RequiredArgsConstructor
public class CommentaireController {
    private final CommentaireService commentaireService;

    @PostMapping("/ajouterCommentaire")
    public ResponseEntity<String> ajouterCommentaire(@RequestBody AddCommentaireRequest request) {
        // Vérifiez l'authentification de l'utilisateur et effectuez d'autres validations si nécessaire
        commentaireService.ajouterCommentaire(request);
        return new ResponseEntity<>("Commentaire ajouté avec succès.", HttpStatus.CREATED);
    }

    @GetMapping("/utilisateur/{idUtilisateur}")
    public ResponseEntity<List<Commentaire>> getCommentairesByUtilisateur(@PathVariable Long idUtilisateur) {
        // Recherchez l'utilisateur à partir de l'ID et vérifiez l'authentification si nécessaire
        List<Commentaire> commentaires = commentaireService.getCommentairesByUtilisateur(idUtilisateur);
        return new ResponseEntity<>(commentaires, HttpStatus.OK);
    }
}

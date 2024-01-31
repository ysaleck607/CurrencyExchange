package com.example.devise.Commentaire;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins =  "http://localhost:63343")
@RestController
@RequestMapping(path = "api/v1/commentaires")
@RequiredArgsConstructor
public class CommentaireController {
    private final CommentaireService commentaireService;

    @PostMapping("/ajouterCommentaire")
    public ResponseEntity<String> ajouterCommentaire(@RequestBody AddCommentaireRequest request) {
        commentaireService.ajouterCommentaire(request);
        return new ResponseEntity<>("Commentaire ajouté avec succès.", HttpStatus.CREATED);
    }

    @GetMapping("/getCommentairesSur/{idUtilisateur}")
    public ResponseEntity<?> getCommentairesByUtilisateur(@PathVariable Long idUtilisateur) {
        try {
            List<CommentaireResponse> commentaireResponses = commentaireService.getCommentairesByUtilisateur(idUtilisateur);
            return ResponseEntity.ok(commentaireResponses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

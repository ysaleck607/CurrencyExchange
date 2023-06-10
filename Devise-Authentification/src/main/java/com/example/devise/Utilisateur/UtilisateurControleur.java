package com.example.devise.Utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/utilisateurs")
public class UtilisateurControleur {
    private final  UtilisateurService utilisateurService;
    @Autowired
    public UtilisateurControleur(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<Utilisateur> obtenirListUtilisateurs() {
        return utilisateurService.obtenirListUtilisateurs();
    }
    @PostMapping
    public void enregistrerNouvelUtilisateur(@RequestBody Utilisateur utilisateur){
        utilisateurService.enregistrerNouvelUtilisateur(utilisateur);
    }
}

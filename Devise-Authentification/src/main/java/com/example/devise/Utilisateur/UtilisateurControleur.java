package com.example.devise.Utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Utilisateur> obtenirUtilisateur() {
        return utilisateurService.obtenirUtilisateur();
    }
}

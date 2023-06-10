package com.example.devise.Utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {
    private final  UtilisateurRepository utilisateurRepository;
    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> obtenirListUtilisateurs() {
        return  utilisateurRepository.findAll();
    }

    public void enregistrerNouvelUtilisateur(Utilisateur utilisateur) {
        System.out.println(utilisateur);
    }
}

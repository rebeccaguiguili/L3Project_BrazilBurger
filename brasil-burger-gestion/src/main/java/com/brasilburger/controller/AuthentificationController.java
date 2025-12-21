package com.brasilburger.controller;

import com.brasilburger.entity.Utilisateur;
import com.brasilburger.service.UtilisateurService;

public class AuthentificationController {
    private final UtilisateurService utilisateurService;

    public AuthentificationController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public Utilisateur connexion(String email, String motDePasse) {
        return utilisateurService.authentifier(email, motDePasse);
    }
}

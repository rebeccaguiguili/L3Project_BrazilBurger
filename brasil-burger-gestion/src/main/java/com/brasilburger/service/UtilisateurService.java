package com.brasilburger.service;

import com.brasilburger.entity.Utilisateur;

public interface UtilisateurService {
    Utilisateur authentifier(String email, String motDePasse);
}

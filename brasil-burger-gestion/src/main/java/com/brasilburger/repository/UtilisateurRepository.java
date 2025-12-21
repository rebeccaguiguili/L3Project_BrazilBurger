package com.brasilburger.repository;

import com.brasilburger.entity.Utilisateur;
import java.util.Optional;

public interface UtilisateurRepository {
    Optional<Utilisateur> findByEmail(String email);
    // Autres méthodes CRUD si besoin
}

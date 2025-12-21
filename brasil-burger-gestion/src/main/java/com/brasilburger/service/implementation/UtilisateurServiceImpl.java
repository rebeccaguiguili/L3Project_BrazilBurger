package com.brasilburger.service.implementation;

import com.brasilburger.entity.Utilisateur;
import com.brasilburger.repository.UtilisateurRepository;
import com.brasilburger.service.UtilisateurService;
import java.util.Optional;

public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public Utilisateur authentifier(String email, String motDePasse) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            if (utilisateur.getMotDePasse().equals(motDePasse) && "GESTIONNAIRE".equals(utilisateur.getRole())) {
                return utilisateur;
            }
        }
        return null;
    }
}

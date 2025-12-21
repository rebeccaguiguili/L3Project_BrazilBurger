package com.brasilburger.service;

import com.brasilburger.entity.Commande;

import java.util.List;
import java.util.Optional;

public interface CommandeService {

    Commande ajouterCommande(Commande commande);

    Commande modifierCommande(Long id, Commande commande);

    boolean annulerCommande(Long id);

    List<Commande> listerCommandes();

    Optional<Commande> trouverCommandeParId(Long id);
}

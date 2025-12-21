package com.brasilburger.service;

import com.brasilburger.entity.Livraison;
import java.util.List;

/**
 * Interface du service Livraison
 */
public interface LivraisonService {

    Livraison ajouterLivraison(Livraison livraison);

    Livraison modifierLivraison(Long id, Livraison livraison);

    void supprimerLivraison(Long id);

    List<Livraison> listerLivraisons();
}

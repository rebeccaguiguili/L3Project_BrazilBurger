package com.brasilburger.view;

import com.brasilburger.entity.Livraison;

import java.util.List;

public class LivraisonView {

    public void afficherLivraisons(List<Livraison> livraisons) {
        System.out.println("Liste des livraisons :");
        for (Livraison livraison : livraisons) {
            System.out.println("ID: " + livraison.getId() +
                               ", Adresse: " + livraison.getAdresse() +
                               ", Livreur: " + livraison.getLivreur() +
                               ", Statut: " + livraison.getStatut());
        }
    }

    public void afficherDetailsLivraison(Livraison livraison) {
        System.out.println("Détails de la livraison :");
        System.out.println("ID: " + livraison.getId());
        System.out.println("Adresse: " + livraison.getAdresse());
        System.out.println("Livreur: " + livraison.getLivreur());
        System.out.println("Statut: " + livraison.getStatut());
    }

    public void afficherMessage(String message) {
        System.out.println(message);
    }
}
package com.brasilburger.view;

import com.brasilburger.entity.Commande;

import java.util.List;

public class CommandeView {

    public void afficherDetailsCommande(Commande commande) {
        System.out.println("Détails de la commande :");
        System.out.println("ID : " + commande.getId());
        System.out.println("Client : " + commande.getClient().getNom() + " " + commande.getClient().getPrenom());
        // TODO: Ajouter la relation avec les articles/lignes de commande
        // System.out.println("Articles commandés : " + commande.getArticles());
        System.out.println("Statut : " + commande.getStatut());
        System.out.println("Date de création : " + commande.getDateCreation());
        System.out.println("Montant total : " + commande.getMontantTotal());
    }

    public void afficherListeCommandes(List<Commande> commandes) {
        System.out.println("Liste des commandes :");
        for (Commande commande : commandes) {
            System.out.println("ID : " + commande.getId() + ", Client : " + commande.getClient().getNom());
        }
    }

    public void afficherMessage(String message) {
        System.out.println(message);
    }
}
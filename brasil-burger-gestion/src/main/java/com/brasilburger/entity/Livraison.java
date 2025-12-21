package com.brasilburger.entity;

import javax.persistence.*;

@Entity
@Table(name = "livraison")
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "livreur_id")
    private Utilisateur livreur;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Column(name = "statut")
    private String statut; // EnAttente, EnCours, Livree, Annulee

    // Constructeur par défaut (requis par JPA)
    public Livraison() {
    }

    // Constructeur pour compatibilité
    public Livraison(Long id, Commande commande, Utilisateur livreur, String statut) {
        this.id = id;
        this.commande = commande;
        this.livreur = livreur;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Utilisateur getLivreur() {
        return livreur;
    }

    public void setLivreur(Utilisateur livreur) {
        this.livreur = livreur;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    // Méthodes de compatibilité avec l'ancien code
    public String getAdresse() {
        return commande != null ? commande.getAdresseLivraison() : null;
    }
}

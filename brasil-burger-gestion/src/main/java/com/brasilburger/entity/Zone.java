package com.brasilburger.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "zone")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prix_livraison")
    private BigDecimal prixLivraison;

    // Constructeur par défaut (requis par JPA)
    public Zone() {
    }

    // Constructeur
    public Zone(Long id, String nom, BigDecimal prixLivraison) {
        this.id = id;
        this.nom = nom;
        this.prixLivraison = prixLivraison;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BigDecimal getPrixLivraison() {
        return prixLivraison;
    }

    public void setPrixLivraison(BigDecimal prixLivraison) {
        this.prixLivraison = prixLivraison;
    }

    // Méthode de compatibilité
    public double getPrix() {
        return prixLivraison != null ? prixLivraison.doubleValue() : 0.0;
    }

    public void setPrix(double prix) {
        this.prixLivraison = BigDecimal.valueOf(prix);
    }
}

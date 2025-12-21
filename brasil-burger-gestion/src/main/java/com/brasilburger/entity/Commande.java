package com.brasilburger.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Utilisateur client;

    @Column(name = "date_commande")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCommande;

    @Column(name = "etat")
    private String etat; // EnAttente, Validee, EnPreparation, Pret, EnLivraison, Terminee, Annulee

    @Column(name = "type_consommation")
    private String typeConsommation; // SurPlace, AEmporter, Livraison

    @Column(name = "adresse_livraison")
    private String adresseLivraison;

    @Column(name = "montant_total")
    private BigDecimal montantTotal;

    // Constructeur par défaut (requis par JPA)
    public Commande() {
    }

    // Constructeur pour compatibilité avec l'ancien code
    public Commande(Long id, Utilisateur client, String etat, Date dateCommande) {
        this.id = id;
        this.client = client;
        this.etat = etat;
        this.dateCommande = dateCommande;
        this.montantTotal = BigDecimal.ZERO;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utilisateur getClient() {
        return client;
    }

    public void setClient(Utilisateur client) {
        this.client = client;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    // Méthode de compatibilité avec l'ancien code
    public Date getDateCreation() {
        return dateCommande;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCommande = dateCreation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    // Méthode de compatibilité avec l'ancien code
    public String getStatut() {
        return etat;
    }

    public void setStatut(String statut) {
        this.etat = statut;
    }

    public String getTypeConsommation() {
        return typeConsommation;
    }

    public void setTypeConsommation(String typeConsommation) {
        this.typeConsommation = typeConsommation;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }
}

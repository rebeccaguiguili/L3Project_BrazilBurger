package com.brasilburger.entity;

import java.util.List;

public class Menu {
    private Long id;
    private String nom;
    private String image;
    private List<Burger> burgers;
    private double prix;
    private boolean archive;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public List<Burger> getBurgers() { return burgers; }
    public void setBurgers(List<Burger> burgers) { this.burgers = burgers; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public boolean isArchive() { return archive; }
    public void setArchive(boolean archive) { this.archive = archive; }
}

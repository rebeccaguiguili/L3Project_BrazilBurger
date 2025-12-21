package com.brasilburger.repository.impl;

import com.brasilburger.entity.Burger;
import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BurgerRepositoryImpl implements BurgerRepository {

    @Override
    public Burger save(Burger burger) {
        Connection conn = null;
        PreparedStatement stmtProduit = null;
        PreparedStatement stmtBurger = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Démarrer une transaction

            // 1. Insérer dans la table produit
            String sqlProduit = "INSERT INTO produit (nom, prix, image_url, est_archive) VALUES (?, ?, ?, ?)";
            stmtProduit = conn.prepareStatement(sqlProduit, Statement.RETURN_GENERATED_KEYS);

            stmtProduit.setString(1, burger.getNom());
            stmtProduit.setDouble(2, burger.getPrix());
            stmtProduit.setString(3, burger.getImage());
            stmtProduit.setBoolean(4, burger.isArchive());

            int rowsAffected = stmtProduit.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmtProduit.getGeneratedKeys();
                if (rs.next()) {
                    burger.setId(rs.getLong(1));
                }

                // 2. Insérer dans la table burger
                String sqlBurger = "INSERT INTO burger (produit_id, description) VALUES (?, ?)";
                stmtBurger = conn.prepareStatement(sqlBurger);
                stmtBurger.setLong(1, burger.getId());
                stmtBurger.setString(2, null); // Description vide pour l'instant
                stmtBurger.executeUpdate();

                conn.commit(); // Valider la transaction
                System.out.println("Burger ajoute avec succes! ID: " + burger.getId());
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Annuler en cas d'erreur
            } catch (SQLException ex) {
                System.err.println("Erreur lors du rollback: " + ex.getMessage());
            }
            System.err.println("Erreur lors de l'ajout du burger: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmtProduit != null) stmtProduit.close();
                if (stmtBurger != null) stmtBurger.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources: " + e.getMessage());
            }
        }
        return burger;
    }

    @Override
    public Burger update(Burger burger) {
        String sql = "UPDATE produit SET nom = ?, prix = ?, image_url = ?, est_archive = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, burger.getNom());
            stmt.setDouble(2, burger.getPrix());
            stmt.setString(3, burger.getImage());
            stmt.setBoolean(4, burger.isArchive());
            stmt.setLong(5, burger.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Burger modifie avec succes!");
            } else {
                System.out.println("Aucun burger trouve avec l'ID: " + burger.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du burger: " + e.getMessage());
            e.printStackTrace();
        }
        return burger;
    }

    @Override
    public void archiver(Long id) {
        String sql = "UPDATE produit SET est_archive = true WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Burger archive avec succes!");
            } else {
                System.out.println("Aucun burger trouve avec l'ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'archivage du burger: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Burger> findById(Long id) {
        String sql = "SELECT p.id, p.nom, p.prix, p.image_url, p.est_archive " +
                     "FROM produit p INNER JOIN burger b ON p.id = b.produit_id " +
                     "WHERE p.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Burger burger = new Burger();
                burger.setId(rs.getLong("id"));
                burger.setNom(rs.getString("nom"));
                burger.setPrix(rs.getDouble("prix"));
                burger.setImage(rs.getString("image_url"));
                burger.setArchive(rs.getBoolean("est_archive"));
                return Optional.of(burger);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du burger: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Burger> findAll() {
        List<Burger> burgers = new ArrayList<>();
        String sql = "SELECT p.id, p.nom, p.prix, p.image_url, p.est_archive " +
                     "FROM produit p INNER JOIN burger b ON p.id = b.produit_id " +
                     "WHERE p.est_archive = false";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Burger burger = new Burger();
                burger.setId(rs.getLong("id"));
                burger.setNom(rs.getString("nom"));
                burger.setPrix(rs.getDouble("prix"));
                burger.setImage(rs.getString("image_url"));
                burger.setArchive(rs.getBoolean("est_archive"));
                burgers.add(burger);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recuperation des burgers: " + e.getMessage());
            e.printStackTrace();
        }
        return burgers;
    }
}

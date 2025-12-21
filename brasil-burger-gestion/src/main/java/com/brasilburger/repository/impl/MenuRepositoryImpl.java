package com.brasilburger.repository.impl;

import com.brasilburger.entity.Menu;
import com.brasilburger.repository.MenuRepository;
import com.brasilburger.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuRepositoryImpl implements MenuRepository {

    @Override
    public Menu save(Menu menu) {
        Connection conn = null;
        PreparedStatement stmtProduit = null;
        PreparedStatement stmtMenu = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Démarrer une transaction

            // 1. Insérer dans la table produit
            String sqlProduit = "INSERT INTO produit (nom, prix, image_url, est_archive) VALUES (?, ?, ?, ?)";
            stmtProduit = conn.prepareStatement(sqlProduit, Statement.RETURN_GENERATED_KEYS);

            stmtProduit.setString(1, menu.getNom());
            stmtProduit.setDouble(2, menu.getPrix());
            stmtProduit.setString(3, menu.getImage());
            stmtProduit.setBoolean(4, menu.isArchive());

            int rowsAffected = stmtProduit.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmtProduit.getGeneratedKeys();
                if (rs.next()) {
                    menu.setId(rs.getLong(1));
                }

                // 2. Insérer dans la table menu
                String sqlMenu = "INSERT INTO menu (produit_id, description) VALUES (?, ?)";
                stmtMenu = conn.prepareStatement(sqlMenu);
                stmtMenu.setLong(1, menu.getId());
                stmtMenu.setString(2, null); // Description vide pour l'instant
                stmtMenu.executeUpdate();

                conn.commit(); // Valider la transaction
                System.out.println("Menu ajoute avec succes! ID: " + menu.getId());
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Annuler en cas d'erreur
            } catch (SQLException ex) {
                System.err.println("Erreur lors du rollback: " + ex.getMessage());
            }
            System.err.println("Erreur lors de l'ajout du menu: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmtProduit != null) stmtProduit.close();
                if (stmtMenu != null) stmtMenu.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources: " + e.getMessage());
            }
        }
        return menu;
    }

    @Override
    public Menu update(Menu menu) {
        String sql = "UPDATE produit SET nom = ?, prix = ?, image_url = ?, est_archive = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, menu.getNom());
            stmt.setDouble(2, menu.getPrix());
            stmt.setString(3, menu.getImage());
            stmt.setBoolean(4, menu.isArchive());
            stmt.setLong(5, menu.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Menu modifie avec succes!");
            } else {
                System.out.println("Aucun menu trouve avec l'ID: " + menu.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du menu: " + e.getMessage());
            e.printStackTrace();
        }
        return menu;
    }

    @Override
    public void archiver(Long id) {
        String sql = "UPDATE produit SET est_archive = true WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Menu archive avec succes!");
            } else {
                System.out.println("Aucun menu trouve avec l'ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'archivage du menu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Menu> findById(Long id) {
        String sql = "SELECT p.id, p.nom, p.prix, p.image_url, p.est_archive " +
                     "FROM produit p INNER JOIN menu m ON p.id = m.produit_id " +
                     "WHERE p.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getLong("id"));
                menu.setNom(rs.getString("nom"));
                menu.setPrix(rs.getDouble("prix"));
                menu.setImage(rs.getString("image_url"));
                menu.setArchive(rs.getBoolean("est_archive"));
                return Optional.of(menu);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du menu: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Menu> findAll() {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT p.id, p.nom, p.prix, p.image_url, p.est_archive " +
                     "FROM produit p INNER JOIN menu m ON p.id = m.produit_id " +
                     "WHERE p.est_archive = false";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getLong("id"));
                menu.setNom(rs.getString("nom"));
                menu.setPrix(rs.getDouble("prix"));
                menu.setImage(rs.getString("image_url"));
                menu.setArchive(rs.getBoolean("est_archive"));
                menus.add(menu);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recuperation des menus: " + e.getMessage());
            e.printStackTrace();
        }
        return menus;
    }
}

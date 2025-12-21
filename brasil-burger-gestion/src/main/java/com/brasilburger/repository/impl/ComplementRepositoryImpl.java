package com.brasilburger.repository.impl;

import com.brasilburger.entity.Complement;
import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComplementRepositoryImpl implements ComplementRepository {

    @Override
    public Complement save(Complement complement) {
        Connection conn = null;
        PreparedStatement stmtProduit = null;
        PreparedStatement stmtComplement = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Démarrer une transaction

            // 1. Insérer dans la table produit
            String sqlProduit = "INSERT INTO produit (nom, prix, image_url, est_archive) VALUES (?, ?, ?, ?)";
            stmtProduit = conn.prepareStatement(sqlProduit, Statement.RETURN_GENERATED_KEYS);

            stmtProduit.setString(1, complement.getNom());
            stmtProduit.setDouble(2, complement.getPrix());
            stmtProduit.setString(3, complement.getImage());
            stmtProduit.setBoolean(4, complement.isArchive());

            int rowsAffected = stmtProduit.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmtProduit.getGeneratedKeys();
                if (rs.next()) {
                    complement.setId(rs.getLong(1));
                }

                // 2. Insérer dans la table complement
                // Déterminer le type basé sur le nom (Frites ou Boisson)
                String type = complement.getNom().toLowerCase().contains("frit") ? "Frites" : "Boisson";
                String sqlComplement = "INSERT INTO complement (produit_id, type) VALUES (?, ?)";
                stmtComplement = conn.prepareStatement(sqlComplement);
                stmtComplement.setLong(1, complement.getId());
                stmtComplement.setString(2, type);
                stmtComplement.executeUpdate();

                conn.commit(); // Valider la transaction
                System.out.println("Complement ajoute avec succes! ID: " + complement.getId());
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Annuler en cas d'erreur
            } catch (SQLException ex) {
                System.err.println("Erreur lors du rollback: " + ex.getMessage());
            }
            System.err.println("Erreur lors de l'ajout du complement: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmtProduit != null) stmtProduit.close();
                if (stmtComplement != null) stmtComplement.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources: " + e.getMessage());
            }
        }
        return complement;
    }

    @Override
    public Complement update(Complement complement) {
        String sql = "UPDATE produit SET nom = ?, prix = ?, image_url = ?, est_archive = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, complement.getNom());
            stmt.setDouble(2, complement.getPrix());
            stmt.setString(3, complement.getImage());
            stmt.setBoolean(4, complement.isArchive());
            stmt.setLong(5, complement.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Complement modifie avec succes!");
            } else {
                System.out.println("Aucun complement trouve avec l'ID: " + complement.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du complement: " + e.getMessage());
            e.printStackTrace();
        }
        return complement;
    }

    @Override
    public void archiver(Long id) {
        String sql = "UPDATE produit SET est_archive = true WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Complement archive avec succes!");
            } else {
                System.out.println("Aucun complement trouve avec l'ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'archivage du complement: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Complement> findById(Long id) {
        String sql = "SELECT p.id, p.nom, p.prix, p.image_url, p.est_archive " +
                     "FROM produit p INNER JOIN complement c ON p.id = c.produit_id " +
                     "WHERE p.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Complement complement = new Complement();
                complement.setId(rs.getLong("id"));
                complement.setNom(rs.getString("nom"));
                complement.setPrix(rs.getDouble("prix"));
                complement.setImage(rs.getString("image_url"));
                complement.setArchive(rs.getBoolean("est_archive"));
                return Optional.of(complement);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du complement: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Complement> findAll() {
        List<Complement> complements = new ArrayList<>();
        String sql = "SELECT p.id, p.nom, p.prix, p.image_url, p.est_archive " +
                     "FROM produit p INNER JOIN complement c ON p.id = c.produit_id " +
                     "WHERE p.est_archive = false";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Complement complement = new Complement();
                complement.setId(rs.getLong("id"));
                complement.setNom(rs.getString("nom"));
                complement.setPrix(rs.getDouble("prix"));
                complement.setImage(rs.getString("image_url"));
                complement.setArchive(rs.getBoolean("est_archive"));
                complements.add(complement);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recuperation des complements: " + e.getMessage());
            e.printStackTrace();
        }
        return complements;
    }
}

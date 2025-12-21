package com.brasilburger;

import com.brasilburger.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  TEST DE CONNEXION - NEON POSTGRESQL     ");
        System.out.println("===========================================\n");

        // Afficher la configuration
        DatabaseConnection.printConfiguration();

        // Tester la connexion
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println("\nConnexion reussie!");

            // Tester une requete simple
            try {
                Statement stmt = conn.createStatement();

                // Compter les burgers (via jointure produit + burger)
                ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(*) as count FROM produit p INNER JOIN burger b ON p.id = b.produit_id"
                );
                if (rs.next()) {
                    System.out.println("Nombre de burgers dans la BD: " + rs.getInt("count"));
                }

                // Compter les menus (via jointure produit + menu)
                rs = stmt.executeQuery(
                    "SELECT COUNT(*) as count FROM produit p INNER JOIN menu m ON p.id = m.produit_id"
                );
                if (rs.next()) {
                    System.out.println("Nombre de menus dans la BD: " + rs.getInt("count"));
                }

                // Compter les complements (via jointure produit + complement)
                rs = stmt.executeQuery(
                    "SELECT COUNT(*) as count FROM produit p INNER JOIN complement c ON p.id = c.produit_id"
                );
                if (rs.next()) {
                    System.out.println("Nombre de complements dans la BD: " + rs.getInt("count"));
                }

                // Compter tous les produits
                rs = stmt.executeQuery("SELECT COUNT(*) as count FROM produit");
                if (rs.next()) {
                    System.out.println("Nombre total de produits dans la BD: " + rs.getInt("count"));
                }

                rs.close();
                stmt.close();

                System.out.println("\nToutes les requetes de test ont reussi!");

            } catch (Exception e) {
                System.err.println("Erreur lors de l'execution des requetes: " + e.getMessage());
                e.printStackTrace();
            }

            // Fermer la connexion
            DatabaseConnection.closeConnection();
        } else {
            System.err.println("\nEchec de la connexion!");
        }
    }
}

package com.brasilburger.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER;
    private static Connection connection = null;

    static {
        loadDatabaseProperties();
    }

    private static void loadDatabaseProperties() {
        Properties props = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input == null) {
                System.err.println("Fichier database.properties introuvable!");
                // Valeurs par defaut pour Neon PostgreSQL
                URL = "jdbc:postgresql://ep-raspy-glitter-a417bp77-pooler.us-east-1.aws.neon.tech/neondb?sslmode=require";
                USER = "neondb_owner";
                PASSWORD = "npg_vLxQ5bukUE3B";
                DRIVER = "org.postgresql.Driver";
                return;
            }

            props.load(input);
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.username");
            PASSWORD = props.getProperty("db.password");
            DRIVER = props.getProperty("db.driver");

            System.out.println("Configuration de la base de donnees chargee avec succes!");
            System.out.println("Type de BD: " + props.getProperty("db.type"));

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier de configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion a la base de donnees Neon PostgreSQL etablie avec succes!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL non trouve: " + e.getMessage());
            System.err.println("Assurez-vous que le driver PostgreSQL est dans le classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion a la base de donnees: " + e.getMessage());
            System.err.println("Verifiez vos parametres de connexion dans database.properties");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion a la base de donnees fermee.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
        }
    }

    // Methode utilitaire pour afficher les informations de configuration
    public static void printConfiguration() {
        System.out.println("\n========== Configuration Base de Donnees ==========");
        System.out.println("URL: " + URL);
        System.out.println("User: " + USER);
        System.out.println("Driver: " + DRIVER);
        System.out.println("===================================================\n");
    }
}

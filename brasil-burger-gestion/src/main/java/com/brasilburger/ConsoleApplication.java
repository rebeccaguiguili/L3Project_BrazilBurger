package com.brasilburger;

import com.brasilburger.entity.Burger;
import com.brasilburger.entity.Complement;
import com.brasilburger.entity.Menu;
import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.repository.MenuRepository;
import com.brasilburger.repository.impl.BurgerRepositoryImpl;
import com.brasilburger.repository.impl.ComplementRepositoryImpl;
import com.brasilburger.repository.impl.MenuRepositoryImpl;
import com.brasilburger.util.DatabaseConnection;

import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BurgerRepository burgerRepository = new BurgerRepositoryImpl();
    private static final MenuRepository menuRepository = new MenuRepositoryImpl();
    private static final ComplementRepository complementRepository = new ComplementRepositoryImpl();

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  BRASIL BURGER - GESTION DES RESSOURCES  ");
        System.out.println("===========================================");

        // Test de connexion
        DatabaseConnection.getConnection();

        boolean running = true;
        while (running) {
            afficherMenuPrincipal();
            int choix = lireChoix();

            switch (choix) {
                case 1:
                    gererBurgers();
                    break;
                case 2:
                    gererMenus();
                    break;
                case 3:
                    gererComplements();
                    break;
                case 0:
                    running = false;
                    System.out.println("\nFermeture de l'application...");
                    DatabaseConnection.closeConnection();
                    System.out.println("Au revoir!");
                    break;
                default:
                    System.out.println("\nChoix invalide. Veuillez reessayer.");
            }
        }
        scanner.close();
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1. Gerer les Burgers");
        System.out.println("2. Gerer les Menus");
        System.out.println("3. Gerer les Complements");
        System.out.println("0. Quitter");
        System.out.print("\nVotre choix: ");
    }

    private static void gererBurgers() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n========== GESTION DES BURGERS ==========");
            System.out.println("1. Ajouter un burger");
            System.out.println("2. Lister les burgers");
            System.out.println("3. Modifier un burger");
            System.out.println("4. Archiver un burger");
            System.out.println("0. Retour au menu principal");
            System.out.print("\nVotre choix: ");

            int choix = lireChoix();
            switch (choix) {
                case 1:
                    ajouterBurger();
                    break;
                case 2:
                    listerBurgers();
                    break;
                case 3:
                    modifierBurger();
                    break;
                case 4:
                    archiverBurger();
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("\nChoix invalide. Veuillez reessayer.");
            }
        }
    }

    private static void gererMenus() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n========== GESTION DES MENUS ==========");
            System.out.println("1. Ajouter un menu");
            System.out.println("2. Lister les menus");
            System.out.println("3. Modifier un menu");
            System.out.println("4. Archiver un menu");
            System.out.println("0. Retour au menu principal");
            System.out.print("\nVotre choix: ");

            int choix = lireChoix();
            switch (choix) {
                case 1:
                    ajouterMenu();
                    break;
                case 2:
                    listerMenus();
                    break;
                case 3:
                    modifierMenu();
                    break;
                case 4:
                    archiverMenu();
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("\nChoix invalide. Veuillez reessayer.");
            }
        }
    }

    private static void gererComplements() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n========== GESTION DES COMPLEMENTS ==========");
            System.out.println("1. Ajouter un complement");
            System.out.println("2. Lister les complements");
            System.out.println("3. Modifier un complement");
            System.out.println("4. Archiver un complement");
            System.out.println("0. Retour au menu principal");
            System.out.print("\nVotre choix: ");

            int choix = lireChoix();
            switch (choix) {
                case 1:
                    ajouterComplement();
                    break;
                case 2:
                    listerComplements();
                    break;
                case 3:
                    modifierComplement();
                    break;
                case 4:
                    archiverComplement();
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("\nChoix invalide. Veuillez reessayer.");
            }
        }
    }

    // ========== OPERATIONS BURGERS ==========
    private static void ajouterBurger() {
        System.out.println("\n--- Ajouter un nouveau burger ---");
        scanner.nextLine(); // Consommer le retour a la ligne

        System.out.print("Nom du burger: ");
        String nom = scanner.nextLine();

        System.out.print("Prix: ");
        double prix = scanner.nextDouble();
        scanner.nextLine(); // Consommer le retour a la ligne

        System.out.print("Chemin de l'image: ");
        String image = scanner.nextLine();

        Burger burger = new Burger();
        burger.setNom(nom);
        burger.setPrix(prix);
        burger.setImage(image);
        burger.setArchive(false);

        burgerRepository.save(burger);
    }

    private static void listerBurgers() {
        System.out.println("\n--- Liste des burgers ---");
        List<Burger> burgers = burgerRepository.findAll();

        if (burgers.isEmpty()) {
            System.out.println("Aucun burger disponible.");
        } else {
            System.out.println(String.format("%-5s | %-25s | %-10s | %-30s", "ID", "NOM", "PRIX", "IMAGE"));
            System.out.println("---------------------------------------------------------------------------------");
            for (Burger burger : burgers) {
                System.out.println(String.format("%-5d | %-25s | %-10.2f | %-30s",
                        burger.getId(), burger.getNom(), burger.getPrix(), burger.getImage()));
            }
        }
    }

    private static void modifierBurger() {
        System.out.println("\n--- Modifier un burger ---");
        listerBurgers();

        System.out.print("\nID du burger a modifier: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consommer le retour a la ligne

        burgerRepository.findById(id).ifPresent(burger -> {
            System.out.print("Nouveau nom (actuel: " + burger.getNom() + "): ");
            String nom = scanner.nextLine();
            if (!nom.isEmpty()) burger.setNom(nom);

            System.out.print("Nouveau prix (actuel: " + burger.getPrix() + "): ");
            String prixStr = scanner.nextLine();
            if (!prixStr.isEmpty()) burger.setPrix(Double.parseDouble(prixStr));

            System.out.print("Nouveau chemin de l'image (actuel: " + burger.getImage() + "): ");
            String image = scanner.nextLine();
            if (!image.isEmpty()) burger.setImage(image);

            burgerRepository.update(burger);
        });
    }

    private static void archiverBurger() {
        System.out.println("\n--- Archiver un burger ---");
        listerBurgers();

        System.out.print("\nID du burger a archiver: ");
        long id = scanner.nextLong();

        burgerRepository.archiver(id);
    }

    // ========== OPERATIONS MENUS ==========
    private static void ajouterMenu() {
        System.out.println("\n--- Ajouter un nouveau menu ---");
        scanner.nextLine(); // Consommer le retour a la ligne

        System.out.print("Nom du menu: ");
        String nom = scanner.nextLine();

        System.out.print("Prix: ");
        double prix = scanner.nextDouble();
        scanner.nextLine(); // Consommer le retour a la ligne

        System.out.print("Chemin de l'image: ");
        String image = scanner.nextLine();

        Menu menu = new Menu();
        menu.setNom(nom);
        menu.setPrix(prix);
        menu.setImage(image);
        menu.setArchive(false);

        menuRepository.save(menu);
    }

    private static void listerMenus() {
        System.out.println("\n--- Liste des menus ---");
        List<Menu> menus = menuRepository.findAll();

        if (menus.isEmpty()) {
            System.out.println("Aucun menu disponible.");
        } else {
            System.out.println(String.format("%-5s | %-25s | %-10s | %-30s", "ID", "NOM", "PRIX", "IMAGE"));
            System.out.println("---------------------------------------------------------------------------------");
            for (Menu menu : menus) {
                System.out.println(String.format("%-5d | %-25s | %-10.2f | %-30s",
                        menu.getId(), menu.getNom(), menu.getPrix(), menu.getImage()));
            }
        }
    }

    private static void modifierMenu() {
        System.out.println("\n--- Modifier un menu ---");
        listerMenus();

        System.out.print("\nID du menu a modifier: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consommer le retour a la ligne

        menuRepository.findById(id).ifPresent(menu -> {
            System.out.print("Nouveau nom (actuel: " + menu.getNom() + "): ");
            String nom = scanner.nextLine();
            if (!nom.isEmpty()) menu.setNom(nom);

            System.out.print("Nouveau prix (actuel: " + menu.getPrix() + "): ");
            String prixStr = scanner.nextLine();
            if (!prixStr.isEmpty()) menu.setPrix(Double.parseDouble(prixStr));

            System.out.print("Nouveau chemin de l'image (actuel: " + menu.getImage() + "): ");
            String image = scanner.nextLine();
            if (!image.isEmpty()) menu.setImage(image);

            menuRepository.update(menu);
        });
    }

    private static void archiverMenu() {
        System.out.println("\n--- Archiver un menu ---");
        listerMenus();

        System.out.print("\nID du menu a archiver: ");
        long id = scanner.nextLong();

        menuRepository.archiver(id);
    }

    // ========== OPERATIONS COMPLEMENTS ==========
    private static void ajouterComplement() {
        System.out.println("\n--- Ajouter un nouveau complement ---");
        scanner.nextLine(); // Consommer le retour a la ligne

        System.out.print("Nom du complement: ");
        String nom = scanner.nextLine();

        System.out.print("Prix: ");
        double prix = scanner.nextDouble();
        scanner.nextLine(); // Consommer le retour a la ligne

        System.out.print("Chemin de l'image: ");
        String image = scanner.nextLine();

        Complement complement = new Complement();
        complement.setNom(nom);
        complement.setPrix(prix);
        complement.setImage(image);
        complement.setArchive(false);

        complementRepository.save(complement);
    }

    private static void listerComplements() {
        System.out.println("\n--- Liste des complements ---");
        List<Complement> complements = complementRepository.findAll();

        if (complements.isEmpty()) {
            System.out.println("Aucun complement disponible.");
        } else {
            System.out.println(String.format("%-5s | %-25s | %-10s | %-30s", "ID", "NOM", "PRIX", "IMAGE"));
            System.out.println("---------------------------------------------------------------------------------");
            for (Complement complement : complements) {
                System.out.println(String.format("%-5d | %-25s | %-10.2f | %-30s",
                        complement.getId(), complement.getNom(), complement.getPrix(), complement.getImage()));
            }
        }
    }

    private static void modifierComplement() {
        System.out.println("\n--- Modifier un complement ---");
        listerComplements();

        System.out.print("\nID du complement a modifier: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consommer le retour a la ligne

        complementRepository.findById(id).ifPresent(complement -> {
            System.out.print("Nouveau nom (actuel: " + complement.getNom() + "): ");
            String nom = scanner.nextLine();
            if (!nom.isEmpty()) complement.setNom(nom);

            System.out.print("Nouveau prix (actuel: " + complement.getPrix() + "): ");
            String prixStr = scanner.nextLine();
            if (!prixStr.isEmpty()) complement.setPrix(Double.parseDouble(prixStr));

            System.out.print("Nouveau chemin de l'image (actuel: " + complement.getImage() + "): ");
            String image = scanner.nextLine();
            if (!image.isEmpty()) complement.setImage(image);

            complementRepository.update(complement);
        });
    }

    private static void archiverComplement() {
        System.out.println("\n--- Archiver un complement ---");
        listerComplements();

        System.out.print("\nID du complement a archiver: ");
        long id = scanner.nextLong();

        complementRepository.archiver(id);
    }

    private static int lireChoix() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Consommer l'entree invalide
            return -1;
        }
    }
}

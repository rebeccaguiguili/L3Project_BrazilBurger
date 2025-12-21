# Guide d'utilisation - Brasil Burger Console Application

## Installation rapide

### Etape 1: Preparer la base de donnees

1. Demarrez votre serveur MySQL
2. Executez le script SQL de creation:

```bash
mysql -u root -p < database/create_tables.sql
```

Ou connectez-vous a MySQL et executez:
```sql
SOURCE database/create_tables.sql;
```

### Etape 2: Verifier la configuration

Assurez-vous que les parametres de connexion dans `src/main/java/com/brasilburger/util/DatabaseConnection.java` correspondent a votre configuration MySQL:

```java
private static final String URL = "jdbc:mysql://localhost:3306/brasil_burger";
private static final String USER = "root";
private static final String PASSWORD = "";
```

### Etape 3: Lancer l'application

**Sur Windows:**
```bash
run-console.bat
```

**Sur Linux/Mac:**
```bash
./run-console.sh
```

**Ou via Maven directement:**
```bash
mvn exec:java -Dexec.mainClass="com.brasilburger.ConsoleApplication"
```

## Utilisation de l'application

### Menu principal

Apres le lancement, vous verrez:

```
===========================================
  BRASIL BURGER - GESTION DES RESSOURCES
===========================================

========== MENU PRINCIPAL ==========
1. Gerer les Burgers
2. Gerer les Menus
3. Gerer les Complements
0. Quitter

Votre choix:
```

### Gestion des Burgers

**Ajouter un burger:**
1. Choisissez `1` dans le menu principal
2. Choisissez `1` pour ajouter
3. Entrez les informations:
   - Nom: `Big Brasil Special`
   - Prix: `6000`
   - Chemin de l'image: `/images/big-brasil-special.jpg`

**Lister les burgers:**
1. Choisissez `1` dans le menu principal
2. Choisissez `2` pour lister
3. Vous verrez un tableau avec tous les burgers disponibles

**Modifier un burger:**
1. Choisissez `1` dans le menu principal
2. Choisissez `3` pour modifier
3. Entrez l'ID du burger a modifier
4. Entrez les nouvelles valeurs (ou appuyez sur Entree pour garder l'ancienne valeur)

**Archiver un burger:**
1. Choisissez `1` dans le menu principal
2. Choisissez `4` pour archiver
3. Entrez l'ID du burger a archiver

### Gestion des Menus

Les operations sont identiques aux burgers:
1. Choisissez `2` dans le menu principal
2. Choisissez l'operation souhaitee (1-4)

### Gestion des Complements

Les operations sont identiques aux burgers:
1. Choisissez `3` dans le menu principal
2. Choisissez l'operation souhaitee (1-4)

## Exemples de scenarios

### Scenario 1: Ajouter un nouveau burger

```
Votre choix: 1
========== GESTION DES BURGERS ==========
1. Ajouter un burger
...
Votre choix: 1

--- Ajouter un nouveau burger ---
Nom du burger: Super Brasil
Prix: 7000
Chemin de l'image: /images/super-brasil.jpg

Connexion a la base de donnees etablie avec succes!
Burger ajoute avec succes! ID: 4
```

### Scenario 2: Lister et modifier un menu

```
Votre choix: 2
========== GESTION DES MENUS ==========
...
Votre choix: 2

--- Liste des menus ---
ID    | NOM                       | PRIX       | IMAGE
---------------------------------------------------------------------------------
1     | Menu Big Brasil           | 6500.00    | /images/menu-big-brasil.jpg
2     | Menu Chicken Brasil       | 6000.00    | /images/menu-chicken-brasil.jpg
3     | Menu Cheese Brasil        | 5500.00    | /images/menu-cheese-brasil.jpg

Votre choix: 3

--- Modifier un menu ---
ID du menu a modifier: 1
Nouveau nom (actuel: Menu Big Brasil): Menu Big Brasil Deluxe
Nouveau prix (actuel: 6500.0): 7000
Nouveau chemin de l'image (actuel: /images/menu-big-brasil.jpg):

Menu modifie avec succes!
```

## Troubleshooting

### Erreur de connexion a la base de donnees

Si vous voyez:
```
Erreur de connexion a la base de donnees: ...
```

Verifiez:
1. Que MySQL est bien demarre
2. Que la base de donnees `brasil_burger` existe
3. Que les credentials (user/password) sont corrects dans `DatabaseConnection.java`

### Erreur "Driver MySQL non trouve"

Si vous voyez:
```
Driver MySQL non trouve: ...
```

Executez:
```bash
mvn clean install
```

Cela telechargera toutes les dependances necessaires incluant le driver MySQL.

### Tables inexistantes

Si vous voyez des erreurs SQL mentionnant des tables inexistantes:
```bash
mysql -u root -p brasil_burger < database/create_tables.sql
```

## Structure du projet

```
brasil-burger-gestion/
├── src/main/java/com/brasilburger/
│   ├── ConsoleApplication.java          # Point d'entree console
│   ├── entity/                          # Entites
│   ├── repository/                      # Couche d'acces aux donnees
│   ├── service/                         # Logique metier
│   └── util/                            # Utilitaires (connexion BD)
├── database/
│   └── create_tables.sql                # Script de creation des tables
├── run-console.bat                      # Script de lancement Windows
├── run-console.sh                       # Script de lancement Linux/Mac
├── README_CONSOLE.md                    # Documentation technique
└── GUIDE_UTILISATION.md                 # Ce fichier
```

## Notes importantes

- L'application console utilise JDBC directement (pas Spring Boot)
- Les ressources archivees ne sont pas supprimees definitivement
- La connexion a la base de donnees reste ouverte pendant toute la session
- Appuyez sur `0` pour quitter proprement et fermer la connexion

## Support

Pour toute question ou probleme, consultez:
- README_CONSOLE.md pour les details techniques
- Le code source dans src/main/java/com/brasilburger/
- Les logs d'erreur dans la console

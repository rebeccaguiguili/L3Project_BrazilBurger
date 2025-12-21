# Brasil Burger - Application Console Java

## Description
Application console Java pour la gestion des ressources du restaurant Brasil Burger (Burgers, Menus, Complements).

## Prerequis
- Java 8 ou superieur
- Maven
- MySQL Server (avec la base de donnees `brasil_burger`)

## Configuration de la base de donnees

Assurez-vous que votre base de donnees MySQL est configuree avec les informations suivantes:
- **URL**: `jdbc:mysql://localhost:3306/brasil_burger`
- **Username**: `root`
- **Password**: (vide par defaut)

Si vos parametres sont differents, modifiez le fichier:
`src/main/java/com/brasilburger/util/DatabaseConnection.java`

## Structure des tables

L'application utilise les tables suivantes:

```sql
-- Table burgers
CREATE TABLE IF NOT EXISTS burgers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix DOUBLE NOT NULL,
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE
);

-- Table menus
CREATE TABLE IF NOT EXISTS menus (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix DOUBLE NOT NULL,
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE
);

-- Table complements
CREATE TABLE IF NOT EXISTS complements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix DOUBLE NOT NULL,
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE
);
```

## Compilation

```bash
# Compiler le projet
mvn clean compile

# Ou compiler et creer un package
mvn clean package
```

## Execution de l'application console

### Option 1: Via Maven
```bash
mvn exec:java -Dexec.mainClass="com.brasilburger.ConsoleApplication"
```

### Option 2: Via Java directement
```bash
# Compiler d'abord
mvn clean compile

# Executer
java -cp "target/classes:$HOME/.m2/repository/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.jar" com.brasilburger.ConsoleApplication
```

### Option 3: Creer un JAR executable
```bash
# Compiler et packager
mvn clean package

# Executer le JAR
java -jar target/brasil-burger-gestion-1.0-SNAPSHOT.jar
```

## Utilisation de l'application

L'application propose un menu interactif avec les options suivantes:

### Menu Principal
1. **Gerer les Burgers**
   - Ajouter un burger
   - Lister les burgers
   - Modifier un burger
   - Archiver un burger

2. **Gerer les Menus**
   - Ajouter un menu
   - Lister les menus
   - Modifier un menu
   - Archiver un menu

3. **Gerer les Complements**
   - Ajouter un complement
   - Lister les complements
   - Modifier un complement
   - Archiver un complement

### Exemple d'utilisation

1. Lancez l'application
2. Choisissez "1" pour gerer les burgers
3. Choisissez "1" pour ajouter un burger
4. Entrez les informations demandees:
   - Nom: Big Brasil
   - Prix: 5000
   - Chemin de l'image: /images/big-brasil.jpg
5. Le burger sera ajoute dans la base de donnees

## Architecture du projet

```
src/main/java/com/brasilburger/
├── ConsoleApplication.java          # Application console principale
├── entity/
│   ├── Burger.java                 # Entite Burger
│   ├── Menu.java                   # Entite Menu
│   └── Complement.java             # Entite Complement
├── repository/
│   ├── BurgerRepository.java       # Interface repository Burger
│   ├── MenuRepository.java         # Interface repository Menu
│   ├── ComplementRepository.java   # Interface repository Complement
│   └── impl/
│       ├── BurgerRepositoryImpl.java      # Implementation JDBC
│       ├── MenuRepositoryImpl.java        # Implementation JDBC
│       └── ComplementRepositoryImpl.java  # Implementation JDBC
├── service/
│   ├── BurgerService.java          # Service Burger
│   ├── MenuService.java            # Service Menu
│   └── ComplementService.java      # Service Complement
└── util/
    └── DatabaseConnection.java     # Gestionnaire de connexion BD
```

## Fonctionnalites implementees

- [x] Gestion complete des Burgers (CRUD)
- [x] Gestion complete des Menus (CRUD)
- [x] Gestion complete des Complements (CRUD)
- [x] Connexion a la base de donnees MySQL via JDBC
- [x] Interface console interactive
- [x] Archivage des ressources (soft delete)

## Notes importantes

- L'application utilise JDBC directement (pas de Spring Boot pour la partie console)
- Les ressources archivees ne sont pas supprimees mais marquees comme archivees
- La connexion a la base de donnees est partagee et geree par la classe DatabaseConnection
- Les tables doivent exister dans la base de donnees avant de lancer l'application

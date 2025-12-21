# Brasil Burger - Application Console Java avec Neon PostgreSQL

## Description
Application console Java pour la gestion des ressources du restaurant Brasil Burger (Burgers, Menus, Complements) utilisant une base de données PostgreSQL hébergée sur Neon.

## Architecture
- **Langage**: Java 8
- **Build Tool**: Maven
- **Base de données**: PostgreSQL (Neon Cloud)
- **Connexion**: JDBC
- **Pattern**: Repository Pattern

## Configuration de la base de données

### Neon PostgreSQL Cloud

L'application est configurée pour se connecter à une base de données PostgreSQL hébergée sur Neon.

**Informations de connexion** (dans `src/main/resources/database.properties`):
```properties
db.type=postgresql
db.url=jdbc:postgresql://ep-raspy-glitter-a417bp77-pooler.us-east-1.aws.neon.tech/neondb?sslmode=require&channel_binding=require
db.username=neondb_owner
db.password=npg_vLxQ5bukUE3B
db.driver=org.postgresql.Driver
```

### Modifier la configuration

Pour utiliser une autre base de données Neon ou PostgreSQL, modifiez le fichier:
`src/main/resources/database.properties`

Les informations de connexion sont centralisées dans ce fichier unique.

## Structure des tables

Les tables sont créées avec le script SQL fourni:

```sql
-- Table burgers
CREATE TABLE IF NOT EXISTS burgers (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix NUMERIC(10,2) NOT NULL,
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table menus
CREATE TABLE IF NOT EXISTS menus (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix NUMERIC(10,2) NOT NULL,
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table complements
CREATE TABLE IF NOT EXISTS complements (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prix NUMERIC(10,2) NOT NULL,
    image VARCHAR(255),
    archive BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Installation et démarrage

### Prérequis
- Java 8 ou supérieur
- Maven 3.x
- Connexion Internet (pour accéder à Neon)

### Étape 1: Cloner le projet
```bash
git clone <votre-repo>
cd brasil-burger-gestion
```

### Étape 2: Vérifier la configuration
Les tables doivent déjà exister dans votre base Neon. Si ce n'est pas le cas, exécutez le script SQL:
```bash
# Connectez-vous à Neon et exécutez:
psql 'postgresql://neondb_owner:npg_vLxQ5bukUE3B@ep-raspy-glitter-a417bp77-pooler.us-east-1.aws.neon.tech/neondb?sslmode=require'

# Puis exécutez le script:
\i database/create_tables.sql
```

### Étape 3: Compiler le projet
```bash
mvn clean compile
```

### Étape 4: Tester la connexion
```bash
# Windows
test-connection.bat

# Linux/Mac
./test-connection.sh

# Ou via Maven
mvn exec:java -Dexec.mainClass="com.brasilburger.TestConnection"
```

### Étape 5: Lancer l'application console

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

### Menu Principal
```
===========================================
  BRASIL BURGER - GESTION DES RESSOURCES
===========================================

========== MENU PRINCIPAL ==========
1. Gerer les Burgers
2. Gerer les Menus
3. Gerer les Complements
0. Quitter
```

### Fonctionnalités disponibles

#### 1. Gestion des Burgers
- **Ajouter**: Créer un nouveau burger avec nom, prix et image
- **Lister**: Afficher tous les burgers non archivés
- **Modifier**: Mettre à jour les informations d'un burger
- **Archiver**: Marquer un burger comme archivé (soft delete)

#### 2. Gestion des Menus
- **Ajouter**: Créer un nouveau menu avec nom, prix et image
- **Lister**: Afficher tous les menus non archivés
- **Modifier**: Mettre à jour les informations d'un menu
- **Archiver**: Marquer un menu comme archivé (soft delete)

#### 3. Gestion des Compléments
- **Ajouter**: Créer un nouveau complément avec nom, prix et image
- **Lister**: Afficher tous les compléments non archivés
- **Modifier**: Mettre à jour les informations d'un complément
- **Archiver**: Marquer un complément comme archivé (soft delete)

## Architecture du projet

```
brasil-burger-gestion/
├── src/main/
│   ├── java/com/brasilburger/
│   │   ├── ConsoleApplication.java      # Point d'entrée principal
│   │   ├── TestConnection.java          # Test de connexion
│   │   ├── entity/
│   │   │   ├── Burger.java              # Entité Burger
│   │   │   ├── Menu.java                # Entité Menu
│   │   │   └── Complement.java          # Entité Complement
│   │   ├── repository/
│   │   │   ├── BurgerRepository.java    # Interface
│   │   │   ├── MenuRepository.java      # Interface
│   │   │   ├── ComplementRepository.java # Interface
│   │   │   └── impl/
│   │   │       ├── BurgerRepositoryImpl.java      # JDBC Impl
│   │   │       ├── MenuRepositoryImpl.java        # JDBC Impl
│   │   │       └── ComplementRepositoryImpl.java  # JDBC Impl
│   │   ├── service/
│   │   │   ├── BurgerService.java       # Service Interface
│   │   │   ├── MenuService.java         # Service Interface
│   │   │   ├── ComplementService.java   # Service Interface
│   │   │   └── implementation/
│   │   │       ├── BurgerServiceImpl.java
│   │   │       ├── MenuServiceImpl.java
│   │   │       └── ComplementServiceImpl.java
│   │   └── util/
│   │       └── DatabaseConnection.java  # Gestion connexion centralisée
│   └── resources/
│       ├── database.properties          # Configuration BD centralisée
│       └── application.properties       # Config Spring Boot
├── database/
│   └── create_tables.sql                # Script SQL PostgreSQL
├── pom.xml                              # Configuration Maven
├── run-console.bat                      # Script Windows
├── run-console.sh                       # Script Linux/Mac
├── test-connection.bat                  # Test connexion Windows
└── test-connection.sh                   # Test connexion Linux/Mac
```

## Avantages de Neon PostgreSQL

1. **Cloud-Based**: Pas besoin d'installer PostgreSQL localement
2. **Scalable**: S'adapte automatiquement à vos besoins
3. **Sécurisé**: Connexions SSL obligatoires
4. **Gratuit**: Tier gratuit généreux pour le développement
5. **Compatible**: 100% compatible PostgreSQL

## Différences avec MySQL

L'application a été adaptée de MySQL vers PostgreSQL:

| Feature | MySQL | PostgreSQL |
|---------|-------|------------|
| Auto-increment | AUTO_INCREMENT | BIGSERIAL |
| Decimal | DOUBLE | NUMERIC(10,2) |
| Driver | mysql-connector-java | postgresql |
| URL | jdbc:mysql:// | jdbc:postgresql:// |

## Troubleshooting

### Erreur de connexion

**Symptôme:**
```
Erreur de connexion a la base de donnees: ...
```

**Solution:**
1. Vérifiez votre connexion Internet
2. Vérifiez que les credentials dans `database.properties` sont corrects
3. Vérifiez que votre base Neon est active

### Driver PostgreSQL non trouvé

**Symptôme:**
```
Driver PostgreSQL non trouve: ...
```

**Solution:**
```bash
mvn clean install
```

### Tables inexistantes

**Symptôme:**
```
ERROR: relation "burgers" does not exist
```

**Solution:**
Exécutez le script SQL de création des tables dans votre base Neon.

### JAVA_HOME non défini

**Symptôme:**
```
The JAVA_HOME environment variable is not defined correctly
```

**Solution:**
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_xxx

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
```

## Tests

### Test de connexion
```bash
mvn exec:java -Dexec.mainClass="com.brasilburger.TestConnection"
```

Affiche:
- Configuration de la base de données
- Statut de la connexion
- Nombre de burgers, menus et compléments dans la BD

### Exemple de sortie réussie:
```
===========================================
  TEST DE CONNEXION - NEON POSTGRESQL
===========================================

Configuration de la base de donnees chargee avec succes!
Type de BD: postgresql

========== Configuration Base de Donnees ==========
URL: jdbc:postgresql://ep-raspy-glitter-a417bp77-pooler.us-east-1.aws.neon.tech/neondb?sslmode=require
User: neondb_owner
Driver: org.postgresql.Driver
===================================================

Connexion a la base de donnees Neon PostgreSQL etablie avec succes!

Connexion reussie!
Nombre de burgers dans la BD: 3
Nombre de menus dans la BD: 3
Nombre de complements dans la BD: 4

Toutes les requetes de test ont reussi!
```

## Notes importantes

- **Configuration centralisée**: Toutes les informations de connexion sont dans `database.properties`
- **SSL requis**: Neon exige des connexions SSL sécurisées
- **Soft delete**: Les ressources archivées ne sont jamais supprimées physiquement
- **Connection pooling**: Une seule connexion est réutilisée pendant toute la session
- **JDBC pur**: L'application console n'utilise pas Spring Boot, seulement JDBC

## Sécurité

**IMPORTANT**: Le fichier `database.properties` contient des credentials sensibles.

Pour la production:
1. N'incluez jamais `database.properties` dans Git
2. Ajoutez-le au `.gitignore`
3. Utilisez des variables d'environnement ou un service de secrets

Exemple `.gitignore`:
```
src/main/resources/database.properties
```

## Support et documentation

- **Neon Documentation**: https://neon.tech/docs
- **PostgreSQL JDBC**: https://jdbc.postgresql.org/
- **Maven**: https://maven.apache.org/

## Contributeurs

Brasil Burger - L3 ISM - Semestre 1

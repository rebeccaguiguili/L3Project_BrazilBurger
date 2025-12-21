# Quick Start - Brasil Burger Console Application

## Démarrage rapide en 5 étapes

### 1. Vérifier les prérequis
```bash
# Vérifier Java
java -version
# Doit afficher Java 8 ou supérieur

# Vérifier Maven
mvn -version
# Doit afficher Maven 3.x
```

### 2. La base de données est déjà configurée!

Les tables suivantes existent déjà dans votre base Neon:
- `burgers` (avec 3 burgers de test)
- `menus` (avec 3 menus de test)
- `complements` (avec 4 compléments de test)

Configuration actuelle dans `src/main/resources/database.properties`:
- **Host**: ep-raspy-glitter-a417bp77-pooler.us-east-1.aws.neon.tech
- **Database**: neondb
- **User**: neondb_owner

### 3. Tester la connexion

**Windows:**
```bash
test-connection.bat
```

**Linux/Mac:**
```bash
chmod +x test-connection.sh
./test-connection.sh
```

**Résultat attendu:**
```
Configuration de la base de donnees chargee avec succes!
Connexion a la base de donnees Neon PostgreSQL etablie avec succes!
Nombre de burgers dans la BD: 3
Nombre de menus dans la BD: 3
Nombre de complements dans la BD: 4
```

### 4. Lancer l'application

**Windows:**
```bash
run-console.bat
```

**Linux/Mac:**
```bash
chmod +x run-console.sh
./run-console.sh
```

### 5. Utiliser l'application

Menu principal:
```
1. Gerer les Burgers
2. Gerer les Menus
3. Gerer les Complements
0. Quitter
```

## Exemples d'utilisation

### Ajouter un nouveau burger
1. Tapez `1` (Gerer les Burgers)
2. Tapez `1` (Ajouter un burger)
3. Entrez les informations:
   ```
   Nom du burger: Mega Brasil
   Prix: 7500
   Chemin de l'image: /images/mega-brasil.jpg
   ```

### Lister tous les menus
1. Tapez `2` (Gerer les Menus)
2. Tapez `2` (Lister les menus)

### Modifier un complément
1. Tapez `3` (Gerer les Complements)
2. Tapez `2` (Lister les complements)
3. Notez l'ID du complément à modifier
4. Tapez `3` (Modifier un complement)
5. Entrez l'ID et les nouvelles valeurs

## Commandes Maven utiles

```bash
# Compiler le projet
mvn clean compile

# Tester la connexion
mvn exec:java -Dexec.mainClass="com.brasilburger.TestConnection"

# Lancer l'application
mvn exec:java -Dexec.mainClass="com.brasilburger.ConsoleApplication"

# Nettoyer et recompiler
mvn clean install
```

## En cas de problème

### Problème: Driver PostgreSQL non trouvé
**Solution:**
```bash
mvn clean install
```

### Problème: Erreur de connexion
**Solution:**
1. Vérifiez votre connexion Internet
2. Vérifiez les credentials dans `src/main/resources/database.properties`

### Problème: JAVA_HOME non défini
**Solution Windows:**
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_xxx
```

**Solution Linux/Mac:**
```bash
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
```

## Structure du projet

```
brasil-burger-gestion/
├── src/main/java/com/brasilburger/
│   ├── ConsoleApplication.java          # Lancez ceci pour l'app
│   ├── TestConnection.java              # Lancez ceci pour tester
│   ├── entity/                          # Classes métier
│   ├── repository/                      # Accès base de données
│   ├── service/                         # Logique métier
│   └── util/
│       └── DatabaseConnection.java      # Configuration centralisée
└── src/main/resources/
    └── database.properties              # Credentials Neon
```

## Informations importantes

- **Configuration centralisée**: Tout est dans `database.properties`
- **Connexion cloud**: Utilise Neon PostgreSQL (pas d'installation locale)
- **Données de test**: Déjà présentes dans la base
- **Soft delete**: Les suppressions sont logiques (archive=true)

## Prochaines étapes

1. Testez toutes les fonctionnalités (ajouter, lister, modifier, archiver)
2. Vérifiez que les données sont bien enregistrées dans Neon
3. Explorez le code dans `src/main/java/com/brasilburger/`

## Documentation complète

- **README_NEON.md**: Documentation technique complète
- **GUIDE_UTILISATION.md**: Guide détaillé d'utilisation
- **README_CONSOLE.md**: Documentation originale

## Support

Pour toute question:
1. Consultez README_NEON.md
2. Vérifiez les logs d'erreur dans la console
3. Testez la connexion avec `TestConnection`

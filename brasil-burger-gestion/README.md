# Brasil Burger Gestion

Cette application Java permet de gérer les commandes et les livraisons pour le restaurant Brasil Burger. Elle est construite selon l'architecture Entity-Service-View-Repository-Controller (ESVRC) et se concentre sur les fonctionnalités destinées aux gestionnaires.

## Structure du projet

Le projet est organisé comme suit :

```
brasil-burger-gestion
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── brasilburger
│   │   │           ├── entity
│   │   │           │   ├── Commande.java
│   │   │           │   ├── Livraison.java
│   │   │           │   └── Utilisateur.java
│   │   │           ├── repository
│   │   │           │   ├── CommandeRepository.java
│   │   │           │   └── LivraisonRepository.java
│   │   │           ├── service
│   │   │           │   ├── CommandeService.java
│   │   │           │   └── LivraisonService.java
│   │   │           ├── controller
│   │   │           │   ├── CommandeController.java
│   │   │           │   └── LivraisonController.java
│   │   │           └── view
│   │   │               ├── CommandeView.java
│   │   │               └── LivraisonView.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── brasilburger
│                   └── service
│                       ├── CommandeServiceTest.java
│                       └── LivraisonServiceTest.java
├── pom.xml
└── README.md
```

## Fonctionnalités

### Gestion des Commandes
- Ajouter, modifier, annuler et lister les commandes.
- Suivi du statut des commandes.

### Gestion des Livraisons
- Ajouter, modifier et lister les livraisons.
- Suivi du statut des livraisons.

## Configuration

Pour configurer l'application, modifiez le fichier `src/main/resources/application.properties` avec les paramètres de connexion à votre base de données.

## Exécution

Pour exécuter l'application, utilisez Maven. Assurez-vous d'avoir Maven installé, puis exécutez la commande suivante dans le répertoire du projet :

```
mvn spring-boot:run
```

## Tests

Des tests unitaires sont fournis pour vérifier le bon fonctionnement des services de commande et de livraison. Exécutez les tests avec la commande suivante :

```
mvn test
```

## Contributions

Les contributions sont les bienvenues. Veuillez soumettre une demande de tirage pour toute fonctionnalité ou correction de bogue.

## License

Ce projet est sous licence MIT.
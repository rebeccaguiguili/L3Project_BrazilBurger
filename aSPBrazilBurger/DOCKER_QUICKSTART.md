# Démarrage Rapide - Docker & Render

Guide de démarrage rapide pour tester et déployer BrazilBurger.

## Test Local Rapide

### Option 1: Script automatique (Recommandé)

#### Linux/Mac:
```bash
chmod +x build-and-test.sh
./build-and-test.sh
```

#### Windows (PowerShell):
```powershell
.\build-and-test.ps1
```

Suivez le menu interactif pour choisir votre option de test.

### Option 2: Docker Compose (Environnement complet)

```bash
# Démarrer tous les services (Web + PostgreSQL + PgAdmin)
docker-compose up -d

# Voir les logs
docker-compose logs -f

# Arrêter
docker-compose down
```

Accès:
- Application: http://localhost:8080
- PgAdmin: http://localhost:5050 (admin@brazilburger.com / admin)

### Option 3: Docker seul (Application uniquement)

```bash
# Build
docker build -t brazilburger-app .

# Run
docker run -d \
  -p 8080:8080 \
  -e ASPNETCORE_ENVIRONMENT=Production \
  -e PORT=8080 \
  -e DATABASE_URL="votre_connection_string" \
  --name brazilburger \
  brazilburger-app

# Logs
docker logs -f brazilburger

# Arrêt
docker stop brazilburger
```

## Déploiement sur Render

### Méthode 1: Via Dashboard (Simple)

1. Connectez-vous à [Render.com](https://render.com)
2. New + → Web Service
3. Connectez votre repository Git
4. Configuration:
   - **Environment**: Docker
   - **Root Directory**: `aSPBrazilBurger`
   - **Dockerfile Path**: `./Dockerfile`

5. Variables d'environnement:
   ```
   ASPNETCORE_ENVIRONMENT=Production
   DATABASE_URL=votre_connection_string_postgresql
   ```

6. Déployez!

### Méthode 2: Infrastructure as Code (Avancé)

Le fichier `render.yaml` est déjà configuré.

1. Poussez le code sur Git
2. Dans Render: New → Blueprint
3. Connectez le repository
4. Render détecte automatiquement `render.yaml`
5. Configurez `DATABASE_URL` manuellement
6. Déployez!

## Variables d'Environnement Requises

### Render.com

```bash
# Obligatoire
ASPNETCORE_ENVIRONMENT=Production
DATABASE_URL=Host=xxx;Database=xxx;Username=xxx;Password=xxx;SSL Mode=Require

# Optionnel (valeurs par défaut)
ASPNETCORE_URLS=http://+:8080
PORT=8080
DOTNET_RUNNING_IN_CONTAINER=true
```

### Format DATABASE_URL

PostgreSQL (Neon.tech ou Render):
```
Host=votre-host.neon.tech;Database=neondb;Username=user;Password=pass;SSL Mode=Require
```

## Vérification Rapide

### Santé de l'application:
```bash
curl http://localhost:8080
```

### Logs en temps réel:
```bash
# Docker
docker logs -f brazilburger

# Docker Compose
docker-compose logs -f web
```

### Taille de l'image:
```bash
docker images brazilburger-app
```

Taille attendue: ~220-280 MB (image finale optimisée)

## Troubleshooting Express

| Problème | Solution |
|----------|----------|
| Build échoue | Vérifiez que Docker est installé et démarré |
| App ne démarre pas | Vérifiez DATABASE_URL et les logs |
| Port déjà utilisé | Changez le port: `-p 8081:8080` |
| Erreur SSL PostgreSQL | Ajoutez `SSL Mode=Require` à DATABASE_URL |

## Documentation Complète

Pour plus de détails, consultez:
- **RENDER_DEPLOYMENT.md**: Guide complet de déploiement Render
- **docker-compose.yml**: Configuration complète des services
- **Dockerfile**: Configuration détaillée de l'image

## Support

En cas de problème:
1. Consultez les logs: `docker logs -f <container>`
2. Vérifiez les variables d'environnement
3. Consultez RENDER_DEPLOYMENT.md section Troubleshooting
4. Testez la connexion PostgreSQL séparément

## Commandes Utiles

```bash
# Nettoyer tout
docker-compose down -v
docker system prune -a

# Rebuild complet
docker-compose up -d --build --force-recreate

# Inspecter le container
docker exec -it brazilburger-app bash

# Vérifier les variables d'environnement
docker exec brazilburger-app env | grep ASP
```

---

Bonne chance avec votre déploiement! 🚀

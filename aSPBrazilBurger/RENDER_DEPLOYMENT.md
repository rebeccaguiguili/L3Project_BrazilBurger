# Guide de Déploiement sur Render.com

## Table des matières
1. [Prérequis](#prérequis)
2. [Configuration du projet](#configuration-du-projet)
3. [Configuration Render](#configuration-render)
4. [Variables d'environnement](#variables-denvironnement)
5. [Déploiement](#déploiement)
6. [Vérification et tests](#vérification-et-tests)
7. [Troubleshooting](#troubleshooting)

---

## Prérequis

- Compte Render.com (gratuit ou payant)
- Repository Git (GitHub, GitLab, ou Bitbucket)
- Base de données PostgreSQL (Neon.tech ou Render PostgreSQL)
- Fichiers Docker configurés (Dockerfile, .dockerignore)

---

## Configuration du projet

### Fichiers créés pour le déploiement

#### 1. **Dockerfile**
Dockerfile multi-stage optimisé pour:
- Build léger et rapide
- Sécurité (utilisateur non-root)
- Compatibilité .NET 9.0
- Support du port dynamique de Render

#### 2. **.dockerignore**
Exclusion des fichiers inutiles pour:
- Réduire la taille de l'image Docker
- Accélérer le build
- Éviter les conflits de fichiers

#### 3. **appsettings.Production.json**
Configuration production avec:
- Support des variables d'environnement
- Logging optimisé pour production
- Configuration Kestrel pour le port dynamique

#### 4. **Program.cs (modifié)**
Modifications apportées:
- Support du port dynamique via `PORT` env var
- Support de `DATABASE_URL` env var
- Désactivation de la redirection HTTPS (géré par Render)
- Configuration Kestrel pour écouter sur 0.0.0.0

---

## Configuration Render

### Étape 1: Créer un nouveau Web Service

1. Connectez-vous à [Render.com](https://render.com)
2. Cliquez sur **"New +"** → **"Web Service"**
3. Connectez votre repository Git
4. Sélectionnez le repository **L3Project_BrazilBurger**

### Étape 2: Configuration du service

Remplissez les champs suivants:

| Paramètre | Valeur |
|-----------|--------|
| **Name** | `brazilburger-app` (ou votre choix) |
| **Region** | Choisir la région la plus proche |
| **Branch** | `csharp` (ou votre branche principale) |
| **Root Directory** | `aSPBrazilBurger` |
| **Environment** | **Docker** |
| **Dockerfile Path** | `./Dockerfile` |
| **Docker Build Context Directory** | `.` |
| **Instance Type** | `Free` ou `Starter` (selon vos besoins) |

### Étape 3: Configuration avancée

#### Build Command
Laisser vide (Docker gère le build)

#### Start Command
Laisser vide (défini dans le Dockerfile via ENTRYPOINT)

#### Health Check Path
```
/
```

---

## Variables d'environnement

### Configuration sur Render

Allez dans **Environment** → **Environment Variables** et ajoutez:

#### Variables obligatoires:

```bash
# Environnement ASP.NET Core
ASPNETCORE_ENVIRONMENT=Production

# Port (automatiquement défini par Render, mais vous pouvez le spécifier)
# PORT=8080  # Optionnel, Render le définit automatiquement

# Connexion à la base de données PostgreSQL
DATABASE_URL=Host=ep-raspy-glitter-a417bp77-pooler.us-east-1.aws.neon.tech;Database=neondb;Username=neondb_owner;Password=npg_vLxQ5bukUE3B;SSL Mode=Require

# Configuration des URLs ASP.NET Core
ASPNETCORE_URLS=http://+:8080

# Variables système .NET
DOTNET_RUNNING_IN_CONTAINER=true
DOTNET_SYSTEM_GLOBALIZATION_INVARIANT=false
```

#### Variables optionnelles:

```bash
# Désactiver HTTPS (Render gère SSL)
ASPNETCORE_HTTPS_PORT=

# Logging
Logging__LogLevel__Default=Information
Logging__LogLevel__Microsoft.AspNetCore=Warning

# Hosts autorisés (par défaut: tous)
AllowedHosts=*
```

### Format de DATABASE_URL

Si vous utilisez une base de données Render PostgreSQL:

1. Créez une base de données PostgreSQL sur Render
2. Copiez l'**Internal Connection String**
3. Format: `Host=xxx;Database=xxx;Username=xxx;Password=xxx;SSL Mode=Require`

**Note importante**: Utilisez toujours **SSL Mode=Require** pour PostgreSQL en production.

---

## Déploiement

### Méthode 1: Déploiement automatique (Recommandé)

1. Dans Render, activez **Auto-Deploy**:
   - Settings → Build & Deploy → Auto-Deploy: **Yes**
2. Poussez vos modifications sur Git:
   ```bash
   git add .
   git commit -m "Configure for Render deployment"
   git push origin csharp
   ```
3. Render détectera automatiquement les changements et lancera le build

### Méthode 2: Déploiement manuel

1. Dans le dashboard Render, cliquez sur **"Manual Deploy"**
2. Sélectionnez **"Deploy latest commit"**
3. Suivez les logs de build en temps réel

### Processus de build

Le build Docker se déroule en 3 étapes:

1. **Stage Build**: Restauration des packages NuGet et compilation
   - Durée: 2-3 minutes

2. **Stage Publish**: Publication de l'application optimisée
   - Durée: 1-2 minutes

3. **Stage Final**: Création de l'image runtime légère
   - Durée: 30 secondes

**Build total estimé**: 4-6 minutes

---

## Vérification et tests

### 1. Vérifier les logs

Dans le dashboard Render, section **Logs**:

```
✓ Pulling image...
✓ Building Docker image...
✓ Successfully built image
✓ Starting service...
✓ Application started successfully
✓ Listening on http://0.0.0.0:XXXX
```

### 2. Tester l'application

Une fois déployé, Render vous fournit une URL:
```
https://brazilburger-app.onrender.com
```

Testez les endpoints principaux:
- **Page d'accueil**: `https://votre-app.onrender.com/`
- **Login**: `https://votre-app.onrender.com/Account/Login`
- **Produits**: `https://votre-app.onrender.com/Products`

### 3. Vérifier la base de données

Testez une action qui nécessite la BDD:
- Création de compte
- Connexion
- Ajout au panier
- Création de commande

---

## Troubleshooting

### Problème 1: Build échoue

**Erreur**: `The framework 'Microsoft.NETCore.App', version 'X.X.X' was not found`

**Solution**:
- Vérifiez la version .NET dans `aSPBrazilBurger.csproj`
- Si vous utilisez .NET 10, changez pour .NET 9 ou 8 (versions stables)
- Modifiez le Dockerfile pour utiliser la bonne image SDK

### Problème 2: Application ne démarre pas

**Erreur**: `Application startup exception`

**Solutions**:
1. Vérifiez les logs Render pour l'erreur exacte
2. Vérifiez que `DATABASE_URL` est correctement configurée
3. Testez la connexion PostgreSQL avec SSL Mode=Require
4. Vérifiez que le port est bien configuré

### Problème 3: Erreur de connexion base de données

**Erreur**: `Npgsql.NpgsqlException: Failed to connect to database`

**Solutions**:
1. Vérifiez le format de `DATABASE_URL`:
   ```
   Host=xxx;Database=xxx;Username=xxx;Password=xxx;SSL Mode=Require
   ```
2. Vérifiez que votre IP n'est pas bloquée (whitelist Neon.tech)
3. Testez la connexion en local avec les mêmes credentials
4. Assurez-vous que `SSL Mode=Require` est présent

### Problème 4: Port non reconnu

**Erreur**: Application démarre mais n'est pas accessible

**Solutions**:
1. Vérifiez que `Program.cs` contient bien la configuration du port:
   ```csharp
   var port = Environment.GetEnvironmentVariable("PORT") ?? "8080";
   builder.WebHost.ConfigureKestrel(serverOptions =>
   {
       serverOptions.ListenAnyIP(int.Parse(port));
   });
   ```
2. Vérifiez les logs pour voir sur quel port l'application écoute
3. Assurez-vous que `ASPNETCORE_URLS=http://+:8080` est défini

### Problème 5: Images/CSS ne se chargent pas

**Erreur**: Fichiers statiques 404

**Solutions**:
1. Vérifiez que `wwwroot` est bien inclus dans l'image Docker
2. Vérifiez que `app.MapStaticAssets()` est présent dans `Program.cs`
3. Assurez-vous que `.dockerignore` n'exclut pas `wwwroot`

### Problème 6: Cookies/Sessions ne fonctionnent pas

**Erreur**: Utilisateur déconnecté après chaque requête

**Solutions**:
1. Vérifiez que les cookies sont configurés avec `SecurePolicy.Always`
2. Assurez-vous que le domaine Render supporte HTTPS
3. Vérifiez l'ordre des middlewares dans `Program.cs`:
   ```csharp
   app.UseSession();
   app.UseAuthentication();
   app.UseAuthorization();
   ```

---

## Optimisations post-déploiement

### 1. Configuration du domaine personnalisé

1. Render Settings → Custom Domain
2. Ajoutez votre domaine
3. Configurez les DNS selon les instructions Render

### 2. Monitoring et alertes

1. Activez les notifications Render (Deploy failed, etc.)
2. Configurez Sentry ou Application Insights pour le monitoring d'erreurs
3. Activez les health checks personnalisés

### 3. Scaling

Pour augmenter les performances:
- **Instance Type**: Passez de Free à Starter/Standard
- **Horizontal Scaling**: Ajoutez plusieurs instances (plans payants)
- **CDN**: Utilisez un CDN pour les assets statiques

### 4. Sécurité

- Changez régulièrement les credentials de base de données
- Utilisez des secrets Render pour les variables sensibles
- Activez 2FA sur votre compte Render
- Configurez un Web Application Firewall (WAF)

---

## Architecture de déploiement

```
[Client Browser]
      ↓
[Render Load Balancer + SSL]
      ↓
[Docker Container - ASP.NET Core App]
      ↓
[Neon.tech PostgreSQL Database]
```

### Flux de requêtes:
1. Client HTTPS → Render Edge (SSL termination)
2. Render → Docker Container (HTTP interne)
3. Container → PostgreSQL (connexion SSL)
4. Réponse retournée via HTTPS

---

## Commandes utiles

### Build local du Docker (test)
```bash
cd aSPBrazilBurger
docker build -t brazilburger-app .
docker run -p 8080:8080 \
  -e ASPNETCORE_ENVIRONMENT=Production \
  -e DATABASE_URL="votre_connection_string" \
  brazilburger-app
```

### Vérifier les logs Render
```bash
# Via Render CLI (si installé)
render logs -s brazilburger-app --tail 100
```

### Test de connexion PostgreSQL
```bash
# Depuis le container Docker
docker exec -it <container_id> dotnet ef database drop --force
docker exec -it <container_id> dotnet ef database update
```

---

## Ressources utiles

- [Documentation Render Docker](https://render.com/docs/docker)
- [ASP.NET Core sur Docker](https://docs.microsoft.com/en-us/aspnet/core/host-and-deploy/docker/)
- [Npgsql Documentation](https://www.npgsql.org/doc/)
- [Neon.tech Documentation](https://neon.tech/docs)

---

## Support et contact

Pour toute question ou problème:
- Consultez les logs Render en temps réel
- Vérifiez la section Troubleshooting ci-dessus
- Contactez le support Render (plans payants)
- Consultez la documentation ASP.NET Core

---

**Date de dernière mise à jour**: 21 Décembre 2025
**Version du guide**: 1.0
**Compatible avec**: .NET 9.0, Render Docker, PostgreSQL 14+

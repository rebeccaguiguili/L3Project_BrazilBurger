# Correction de l'erreur NETSDK1045 - .NET 10.0 non supporté

## Problème Rencontré

```
error NETSDK1045: The current .NET SDK does not support targeting .NET 10.0.
Either target .NET 9.0 or lower, or use a version of the .NET SDK that supports .NET 10.0.
```

## Cause

Votre projet ciblait `.NET 10.0` qui n'existe pas encore en version stable.
Le SDK .NET 9.0 dans le Dockerfile ne peut pas compiler pour .NET 10.0.

## Corrections Appliquées

### 1. Fichier `aSPBrazilBurger.csproj` modifié

**AVANT:**
```xml
<TargetFramework>net10.0</TargetFramework>
```

**APRÈS:**
```xml
<TargetFramework>net9.0</TargetFramework>
```

### 2. Nettoyage des fichiers de build

Suppression des répertoires obsolètes:
- `bin/` - Contenait des builds pour net10.0 et net8.0
- `obj/` - Fichiers objets obsolètes
- `publish/` - Publications obsolètes

## Étapes pour Redéployer sur Render

### Méthode 1: Commit et Push (Recommandé)

```bash
# 1. Vérifier les modifications
git status

# 2. Ajouter les fichiers modifiés
git add aSPBrazilBurger.csproj
git add Dockerfile .dockerignore
git add appsettings.Production.json
git add Program.cs
git add docker-compose.yml render.yaml
git add *.md *.sh *.ps1

# 3. Créer le commit
git commit -m "fix: change target framework from .NET 10.0 to .NET 9.0 for Render compatibility

- Update aSPBrazilBurger.csproj to target net9.0
- Add Docker configuration for Render deployment
- Add deployment documentation
- Clean obsolete build directories

🤖 Generated with Claude Code"

# 4. Pousser vers GitHub
git push origin csharp
# ou si votre branche est main:
git push origin main
```

### Méthode 2: Via l'interface Render

1. Connectez-vous à [Render Dashboard](https://dashboard.render.com)
2. Sélectionnez votre service `brazilburger-app`
3. Allez dans **"Manual Deploy"**
4. Cliquez sur **"Clear build cache & deploy"**
5. Le build devrait maintenant réussir

## Vérification du Build

Le build devrait maintenant suivre ces étapes avec succès:

```
✓ Stage 1: Build - Restoration des packages avec .NET SDK 9.0
✓ Stage 2: Publish - Compilation avec .NET 9.0
✓ Stage 3: Final - Image runtime avec ASP.NET 9.0
✓ Démarrage de l'application
```

**Durée estimée**: 4-6 minutes

## Que Faire Si le Build Échoue Encore?

### Erreur: "Cannot find aSPBrazilBurger.csproj"

**Solution**: Vérifiez dans Render → Settings → Build & Deploy:
- **Root Directory**: doit être exactement `aSPBrazilBurger`
- **Dockerfile Path**: doit être `./Dockerfile`

### Erreur: "DATABASE_URL not set"

**Solution**: Configurez la variable d'environnement dans Render:
1. Settings → Environment
2. Ajouter: `DATABASE_URL=Host=xxx;Database=xxx;Username=xxx;Password=xxx;SSL Mode=Require`

### Erreur: "Port already in use"

**Solution**: Render gère automatiquement le port. Assurez-vous que:
- `ASPNETCORE_URLS=http://+:8080` est défini
- Le code dans `Program.cs` configure bien le port dynamique

### Erreur: Build réussit mais l'app ne démarre pas

**Solution**: Vérifiez les logs Render:
1. Dans Render Dashboard → Logs
2. Cherchez les erreurs de démarrage
3. Vérifiez que PostgreSQL est accessible
4. Testez la connexion DATABASE_URL

## Test Local Avant Redéploiement (Optionnel)

Pour tester localement que tout fonctionne:

### Option A: Avec Docker

```bash
cd aSPBrazilBurger

# Build l'image
docker build -t brazilburger-test .

# Run
docker run -p 8080:8080 \
  -e ASPNETCORE_ENVIRONMENT=Production \
  -e PORT=8080 \
  -e DATABASE_URL="votre_connection_string" \
  brazilburger-test

# Tester
curl http://localhost:8080
```

### Option B: Avec .NET SDK local

```bash
cd aSPBrazilBurger

# Restaurer
dotnet restore

# Build
dotnet build -c Release

# Vérifier le framework cible
dotnet build -c Release -v detailed | grep TargetFramework
# Devrait afficher: net9.0
```

## Compatibilité des Versions

| Composant | Version | Status |
|-----------|---------|--------|
| Target Framework | .NET 9.0 | ✅ Corrigé |
| SDK Docker | .NET 9.0 | ✅ Compatible |
| Runtime Docker | ASP.NET 9.0 | ✅ Compatible |
| EF Core | 9.0.0 | ✅ Compatible |
| Npgsql | 9.0.2 | ✅ Compatible |

## Fichiers Modifiés

Voici la liste complète des fichiers qui ont été créés/modifiés:

### Fichiers Modifiés:
1. ✅ `aSPBrazilBurger.csproj` - Changement de net10.0 → net9.0
2. ✅ `Program.cs` - Support port dynamique et DATABASE_URL

### Fichiers Créés:
1. ✅ `Dockerfile` - Configuration Docker multi-stage
2. ✅ `.dockerignore` - Optimisation du build
3. ✅ `appsettings.Production.json` - Configuration production
4. ✅ `docker-compose.yml` - Tests locaux
5. ✅ `render.yaml` - Infrastructure as Code
6. ✅ `build-and-test.sh` - Script de test Linux/Mac
7. ✅ `build-and-test.ps1` - Script de test Windows
8. ✅ `RENDER_DEPLOYMENT.md` - Documentation complète
9. ✅ `DOCKER_QUICKSTART.md` - Guide de démarrage rapide
10. ✅ `FIX_NET_VERSION.md` - Ce fichier

### Fichiers Supprimés (nettoyage):
- `bin/` - Builds obsolètes
- `obj/` - Fichiers objets
- `publish/` - Publications obsolètes

## Prochaines Étapes

1. ✅ Les corrections ont été appliquées
2. 🔄 Faites un commit et push des modifications
3. 🚀 Render va automatiquement redéployer (si Auto-Deploy activé)
4. ⏱️ Attendez 4-6 minutes pour le build
5. ✅ Vérifiez que l'application fonctionne

## Vérification Post-Déploiement

Une fois déployé avec succès:

### 1. Vérifier le build
```bash
# Les logs Render devraient montrer:
✓ Successfully built image
✓ Image size: ~250-300 MB
✓ Starting web service...
✓ Application started
```

### 2. Tester l'application
```bash
# Remplacez par votre URL Render
curl https://votre-app.onrender.com

# Ou visitez dans le navigateur:
https://votre-app.onrender.com
```

### 3. Vérifier la base de données
- Testez la page de login
- Créez un compte test
- Vérifiez qu'une commande peut être créée

## Support

Si vous rencontrez toujours des problèmes:

1. **Vérifiez les logs Render en temps réel**
2. **Consultez RENDER_DEPLOYMENT.md** section Troubleshooting
3. **Testez localement** avec Docker pour isoler le problème
4. **Vérifiez les variables d'environnement** dans Render Dashboard

## Résumé de la Correction

| Aspect | Avant | Après |
|--------|-------|-------|
| Target Framework | net10.0 ❌ | net9.0 ✅ |
| Compatibilité SDK | Incompatible | Compatible |
| Build Render | Échec | Réussite attendue |
| Taille image | N/A | ~250-300 MB |

---

**Date de correction**: 21 Décembre 2025
**Problème**: NETSDK1045 - .NET 10.0 non supporté
**Solution**: Migration vers .NET 9.0
**Status**: ✅ Résolu - Prêt pour redéploiement

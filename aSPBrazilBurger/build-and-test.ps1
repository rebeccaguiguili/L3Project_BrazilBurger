# ============================================
# Script PowerShell de build et test pour BrazilBurger
# Simule l'environnement de production Render (Version Windows)
# ============================================

# Fonction pour afficher les messages colorés
function Write-Info {
    param([string]$Message)
    Write-Host "[INFO] $Message" -ForegroundColor Blue
}

function Write-Success {
    param([string]$Message)
    Write-Host "[SUCCESS] $Message" -ForegroundColor Green
}

function Write-Warning {
    param([string]$Message)
    Write-Host "[WARNING] $Message" -ForegroundColor Yellow
}

function Write-Error {
    param([string]$Message)
    Write-Host "[ERROR] $Message" -ForegroundColor Red
}

# Banner
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "   BrazilBurger - Build & Test Script" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Vérifier que Docker est installé
Write-Info "Vérification de Docker..."
try {
    docker --version | Out-Null
    Write-Success "Docker est installé"
} catch {
    Write-Error "Docker n'est pas installé. Veuillez l'installer d'abord."
    exit 1
}

# Vérifier que Docker Compose est installé
Write-Info "Vérification de Docker Compose..."
try {
    docker-compose --version | Out-Null
} catch {
    Write-Warning "Docker Compose n'est pas installé. Certaines fonctionnalités seront limitées."
}

# Menu de choix
Write-Host ""
Write-Host "Que voulez-vous faire ?"
Write-Host "1) Build de l'image Docker uniquement"
Write-Host "2) Build et run de l'application (sans base de données)"
Write-Host "3) Build et run complet avec Docker Compose (avec PostgreSQL)"
Write-Host "4) Nettoyer les conteneurs et images"
Write-Host "5) Test de l'image Docker (simulation Render)"
Write-Host ""
$choice = Read-Host "Votre choix (1-5)"

switch ($choice) {
    "1" {
        Write-Info "Build de l'image Docker..."
        docker build -t brazilburger-app:latest .
        Write-Success "Image Docker créée avec succès!"
        Write-Info "Taille de l'image:"
        docker images brazilburger-app:latest
    }

    "2" {
        Write-Info "Build de l'image Docker..."
        docker build -t brazilburger-app:latest .

        Write-Info "Arrêt des conteneurs existants..."
        docker stop brazilburger-app 2>$null
        docker rm brazilburger-app 2>$null

        Write-Info "Démarrage de l'application..."
        docker run -d `
            --name brazilburger-app `
            -p 8080:8080 `
            -e ASPNETCORE_ENVIRONMENT=Production `
            -e PORT=8080 `
            -e "DATABASE_URL=Host=ep-raspy-glitter-a417bp77-pooler.us-east-1.aws.neon.tech;Database=neondb;Username=neondb_owner;Password=npg_vLxQ5bukUE3B;SSL Mode=Require" `
            brazilburger-app:latest

        Write-Success "Application démarrée!"
        Write-Info "Attendez quelques secondes que l'application démarre..."
        Start-Sleep -Seconds 5

        Write-Info "Logs de l'application:"
        docker logs brazilburger-app

        Write-Host ""
        Write-Success "Application accessible sur: http://localhost:8080"
        Write-Info "Pour voir les logs en temps réel: docker logs -f brazilburger-app"
        Write-Info "Pour arrêter: docker stop brazilburger-app"
    }

    "3" {
        try {
            docker-compose --version | Out-Null
        } catch {
            Write-Error "Docker Compose est requis pour cette option."
            exit 1
        }

        Write-Info "Démarrage avec Docker Compose..."
        docker-compose up -d --build

        Write-Info "Attente du démarrage de la base de données..."
        Start-Sleep -Seconds 10

        Write-Success "Tous les services sont démarrés!"
        Write-Host ""
        Write-Info "Services disponibles:"
        Write-Info "  - Application Web: http://localhost:8080"
        Write-Info "  - PgAdmin: http://localhost:5050"
        Write-Info "    Email: admin@brazilburger.com"
        Write-Info "    Password: admin"
        Write-Host ""
        Write-Info "Pour voir les logs: docker-compose logs -f"
        Write-Info "Pour arrêter: docker-compose down"
    }

    "4" {
        Write-Warning "Nettoyage des conteneurs et images..."

        Write-Info "Arrêt de Docker Compose..."
        docker-compose down -v 2>$null

        Write-Info "Arrêt des conteneurs isolés..."
        docker stop brazilburger-app 2>$null
        docker rm brazilburger-app 2>$null

        Write-Info "Suppression de l'image..."
        docker rmi brazilburger-app:latest 2>$null

        Write-Success "Nettoyage terminé!"
    }

    "5" {
        Write-Info "Test de l'image Docker (simulation Render)..."

        # Build
        Write-Info "Étape 1/4: Build de l'image..."
        docker build -t brazilburger-app:latest .
        Write-Success "Build réussi!"

        # Taille
        Write-Info "Étape 2/4: Vérification de la taille de l'image..."
        $imageSize = docker images brazilburger-app:latest --format "{{.Size}}"
        Write-Info "Taille de l'image: $imageSize"

        # Run avec variables d'environnement Render
        Write-Info "Étape 3/4: Démarrage avec configuration Render..."
        docker stop brazilburger-app 2>$null
        docker rm brazilburger-app 2>$null

        docker run -d `
            --name brazilburger-app `
            -p 8080:8080 `
            -e ASPNETCORE_ENVIRONMENT=Production `
            -e PORT=8080 `
            -e ASPNETCORE_URLS=http://+:8080 `
            -e "DATABASE_URL=Host=ep-raspy-glitter-a417bp77-pooler.us-east-1.aws.neon.tech;Database=neondb;Username=neondb_owner;Password=npg_vLxQ5bukUE3B;SSL Mode=Require" `
            -e DOTNET_RUNNING_IN_CONTAINER=true `
            -e DOTNET_SYSTEM_GLOBALIZATION_INVARIANT=false `
            brazilburger-app:latest

        Write-Info "Attente du démarrage (10 secondes)..."
        Start-Sleep -Seconds 10

        # Health check
        Write-Info "Étape 4/4: Test de santé de l'application..."
        try {
            $response = Invoke-WebRequest -Uri "http://localhost:8080" -UseBasicParsing -ErrorAction Stop
            Write-Success "✓ Application répond correctement!"
        } catch {
            Write-Error "✗ L'application ne répond pas"
            Write-Info "Logs de l'application:"
            docker logs brazilburger-app
            exit 1
        }

        # Logs
        Write-Host ""
        Write-Info "Logs de démarrage:"
        docker logs brazilburger-app | Select-Object -Last 20

        Write-Host ""
        Write-Success "=========================================="
        Write-Success "  Test réussi! L'application fonctionne."
        Write-Success "=========================================="
        Write-Info "Application: http://localhost:8080"
        Write-Info "Logs: docker logs -f brazilburger-app"
        Write-Info "Arrêter: docker stop brazilburger-app"
    }

    default {
        Write-Error "Choix invalide."
        exit 1
    }
}

Write-Host ""
Write-Success "Script terminé!"

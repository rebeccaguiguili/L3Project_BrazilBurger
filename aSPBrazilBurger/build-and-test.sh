#!/bin/bash

# ============================================
# Script de build et test local pour BrazilBurger
# Simule l'environnement de production Render
# ============================================

set -e  # Arrêter en cas d'erreur

# Couleurs pour les messages
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Fonction pour afficher les messages
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Banner
echo "============================================"
echo "   BrazilBurger - Build & Test Script"
echo "============================================"
echo ""

# Vérifier que Docker est installé
log_info "Vérification de Docker..."
if ! command -v docker &> /dev/null; then
    log_error "Docker n'est pas installé. Veuillez l'installer d'abord."
    exit 1
fi
log_success "Docker est installé"

# Vérifier que Docker Compose est installé
log_info "Vérification de Docker Compose..."
if ! command -v docker-compose &> /dev/null; then
    log_warning "Docker Compose n'est pas installé. Certaines fonctionnalités seront limitées."
fi

# Menu de choix
echo ""
echo "Que voulez-vous faire ?"
echo "1) Build de l'image Docker uniquement"
echo "2) Build et run de l'application (sans base de données)"
echo "3) Build et run complet avec Docker Compose (avec PostgreSQL)"
echo "4) Nettoyer les conteneurs et images"
echo "5) Test de l'image Docker (simulation Render)"
echo ""
read -p "Votre choix (1-5): " choice

case $choice in
    1)
        log_info "Build de l'image Docker..."
        docker build -t brazilburger-app:latest .
        log_success "Image Docker créée avec succès!"
        log_info "Taille de l'image:"
        docker images brazilburger-app:latest
        ;;

    2)
        log_info "Build de l'image Docker..."
        docker build -t brazilburger-app:latest .

        log_info "Arrêt des conteneurs existants..."
        docker stop brazilburger-app 2>/dev/null || true
        docker rm brazilburger-app 2>/dev/null || true

        log_info "Démarrage de l'application..."
        docker run -d \
            --name brazilburger-app \
            -p 8080:8080 \
            -e ASPNETCORE_ENVIRONMENT=Production \
            -e PORT=8080 \
            -e DATABASE_URL="Host=ep-raspy-glitter-a417bp77-pooler.us-east-1.aws.neon.tech;Database=neondb;Username=neondb_owner;Password=npg_vLxQ5bukUE3B;SSL Mode=Require" \
            brazilburger-app:latest

        log_success "Application démarrée!"
        log_info "Attendez quelques secondes que l'application démarre..."
        sleep 5

        log_info "Logs de l'application:"
        docker logs brazilburger-app

        echo ""
        log_success "Application accessible sur: http://localhost:8080"
        log_info "Pour voir les logs en temps réel: docker logs -f brazilburger-app"
        log_info "Pour arrêter: docker stop brazilburger-app"
        ;;

    3)
        if ! command -v docker-compose &> /dev/null; then
            log_error "Docker Compose est requis pour cette option."
            exit 1
        fi

        log_info "Démarrage avec Docker Compose..."
        docker-compose up -d --build

        log_info "Attente du démarrage de la base de données..."
        sleep 10

        log_success "Tous les services sont démarrés!"
        echo ""
        log_info "Services disponibles:"
        log_info "  - Application Web: http://localhost:8080"
        log_info "  - PgAdmin: http://localhost:5050"
        log_info "    Email: admin@brazilburger.com"
        log_info "    Password: admin"
        echo ""
        log_info "Pour voir les logs: docker-compose logs -f"
        log_info "Pour arrêter: docker-compose down"
        ;;

    4)
        log_warning "Nettoyage des conteneurs et images..."

        log_info "Arrêt de Docker Compose..."
        docker-compose down -v 2>/dev/null || true

        log_info "Arrêt des conteneurs isolés..."
        docker stop brazilburger-app 2>/dev/null || true
        docker rm brazilburger-app 2>/dev/null || true

        log_info "Suppression de l'image..."
        docker rmi brazilburger-app:latest 2>/dev/null || true

        log_success "Nettoyage terminé!"
        ;;

    5)
        log_info "Test de l'image Docker (simulation Render)..."

        # Build
        log_info "Étape 1/4: Build de l'image..."
        docker build -t brazilburger-app:latest .
        log_success "Build réussi!"

        # Taille
        log_info "Étape 2/4: Vérification de la taille de l'image..."
        IMAGE_SIZE=$(docker images brazilburger-app:latest --format "{{.Size}}")
        log_info "Taille de l'image: $IMAGE_SIZE"

        # Run avec variables d'environnement Render
        log_info "Étape 3/4: Démarrage avec configuration Render..."
        docker stop brazilburger-app 2>/dev/null || true
        docker rm brazilburger-app 2>/dev/null || true

        docker run -d \
            --name brazilburger-app \
            -p 8080:8080 \
            -e ASPNETCORE_ENVIRONMENT=Production \
            -e PORT=8080 \
            -e ASPNETCORE_URLS=http://+:8080 \
            -e DATABASE_URL="Host=ep-raspy-glitter-a417bp77-pooler.us-east-1.aws.neon.tech;Database=neondb;Username=neondb_owner;Password=npg_vLxQ5bukUE3B;SSL Mode=Require" \
            -e DOTNET_RUNNING_IN_CONTAINER=true \
            -e DOTNET_SYSTEM_GLOBALIZATION_INVARIANT=false \
            brazilburger-app:latest

        log_info "Attente du démarrage (10 secondes)..."
        sleep 10

        # Health check
        log_info "Étape 4/4: Test de santé de l'application..."
        if curl -f http://localhost:8080 > /dev/null 2>&1; then
            log_success "✓ Application répond correctement!"
        else
            log_error "✗ L'application ne répond pas"
            log_info "Logs de l'application:"
            docker logs brazilburger-app
            exit 1
        fi

        # Logs
        echo ""
        log_info "Logs de démarrage:"
        docker logs brazilburger-app | tail -n 20

        echo ""
        log_success "=========================================="
        log_success "  Test réussi! L'application fonctionne."
        log_success "=========================================="
        log_info "Application: http://localhost:8080"
        log_info "Logs: docker logs -f brazilburger-app"
        log_info "Arrêter: docker stop brazilburger-app"
        ;;

    *)
        log_error "Choix invalide."
        exit 1
        ;;
esac

echo ""
log_success "Script terminé!"

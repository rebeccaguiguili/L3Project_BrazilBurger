# ============================================
# Dockerfile multi-stage pour aSPBrazilBurger
# Optimisé pour déploiement sur Render.com
# ============================================

# ============================================
# Stage 1: Build - Compilation de l'application
# ============================================
FROM mcr.microsoft.com/dotnet/sdk:9.0 AS build
WORKDIR /src

# Copier le fichier de projet et restaurer les dépendances
# Ceci est fait en premier pour optimiser le cache Docker
COPY ["aSPBrazilBurger.csproj", "./"]
RUN dotnet restore "aSPBrazilBurger.csproj"

# Copier tout le code source
COPY . .

# Compiler l'application en mode Release
RUN dotnet build "aSPBrazilBurger.csproj" -c Release -o /app/build

# ============================================
# Stage 2: Publish - Publication de l'application
# ============================================
FROM build AS publish
RUN dotnet publish "aSPBrazilBurger.csproj" \
    -c Release \
    -o /app/publish \
    /p:UseAppHost=false \
    --no-restore

# ============================================
# Stage 3: Runtime - Image finale légère
# ============================================
FROM mcr.microsoft.com/dotnet/aspnet:9.0 AS final

# Informations sur l'image
LABEL maintainer="BrazilBurger Team"
LABEL description="Application ASP.NET Core MVC pour BrazilBurger"
LABEL version="1.0"

# Définir le répertoire de travail
WORKDIR /app

# Créer un utilisateur non-root pour la sécurité
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copier les fichiers publiés depuis le stage publish
COPY --from=publish /app/publish .

# Définir les permissions appropriées
RUN chown -R appuser:appuser /app

# Changer vers l'utilisateur non-root
USER appuser

# Exposer le port (Render utilise la variable d'environnement PORT)
# Par défaut 8080, mais sera overridé par Render
EXPOSE 8080

# Variables d'environnement par défaut
ENV ASPNETCORE_ENVIRONMENT=Production \
    ASPNETCORE_URLS=http://+:8080 \
    DOTNET_RUNNING_IN_CONTAINER=true \
    DOTNET_SYSTEM_GLOBALIZATION_INVARIANT=false

# Point d'entrée de l'application
ENTRYPOINT ["dotnet", "aSPBrazilBurger.dll"]

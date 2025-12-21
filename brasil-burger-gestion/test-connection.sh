#!/bin/bash

echo "=========================================="
echo " TEST DE CONNEXION NEON POSTGRESQL"
echo "=========================================="
echo ""

echo "Compilation du projet..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo ""
    echo "Erreur lors de la compilation!"
    exit 1
fi

echo ""
echo "Test de la connexion a Neon PostgreSQL..."
echo ""

mvn exec:java -Dexec.mainClass="com.brasilburger.TestConnection"

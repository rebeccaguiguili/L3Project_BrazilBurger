#!/bin/bash

echo "=========================================="
echo " BRASIL BURGER - Application Console"
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
echo "Lancement de l'application console..."
echo ""

mvn exec:java -Dexec.mainClass="com.brasilburger.ConsoleApplication"

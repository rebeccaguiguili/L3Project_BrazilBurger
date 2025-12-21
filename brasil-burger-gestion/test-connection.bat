@echo off
echo ==========================================
echo  TEST DE CONNEXION NEON POSTGRESQL
echo ==========================================
echo.

echo Compilation du projet...
call mvn clean compile

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Erreur lors de la compilation!
    pause
    exit /b 1
)

echo.
echo Test de la connexion a Neon PostgreSQL...
echo.

call mvn exec:java -Dexec.mainClass="com.brasilburger.TestConnection"

pause

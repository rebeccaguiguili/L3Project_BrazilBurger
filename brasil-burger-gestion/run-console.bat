@echo off
echo ==========================================
echo  BRASIL BURGER - Application Console
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
echo Lancement de l'application console...
echo.

call mvn exec:java -Dexec.mainClass="com.brasilburger.ConsoleApplication"

pause

@echo off
REM Script de ejecucion de tests JUnit
REM Autor: Estudiante
REM Version: 1.0

echo ========================================
echo Ejecutando tests de la Escoba...
echo ========================================
echo.

REM Verificamos que exista el directorio bin
if not exist bin (
    echo ERROR: No se encuentra el directorio bin.
    echo Por favor, ejecuta primero compilar.bat
    pause
    exit /b 1
)

REM Compilamos los tests
echo Compilando tests...
javac -encoding UTF-8 -d bin -cp "bin;lib\*" test\escoba\*.java test\escoba\modelo\*.java test\escoba\control\*.java

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: La compilacion de tests ha fallado.
    pause
    exit /b 1
)

echo.
echo Ejecutando tests...
echo.

REM Ejecutamos la suite completa de tests
java -jar lib\junit-platform-console-standalone-1.13.4.jar -cp "bin;lib\*" --scan-classpath --details=tree

echo.
echo ========================================
echo Fin de la ejecucion de tests
echo ========================================

pause

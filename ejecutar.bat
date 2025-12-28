@echo off
REM Script de ejecucion del juego de la Escoba
REM Autor: Estudiante
REM Version: 1.0

echo ========================================
echo Ejecutando el juego de la Escoba...
echo ========================================
echo.

REM Verificamos que exista el directorio bin
if not exist bin (
    echo ERROR: No se encuentra el directorio bin.
    echo Por favor, ejecuta primero compilar.bat
    pause
    exit /b 1
)

REM Ejecutamos la aplicacion
java -cp "bin;lib\JColor-5.5.1.jar" escoba.AplicacionEscoba

echo.
echo ========================================
echo Fin de la ejecucion
echo ========================================

pause

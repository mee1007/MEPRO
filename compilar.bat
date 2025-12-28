@echo off
REM Script de compilacion para el juego de la Escoba
REM Autor: Estudiante
REM Version: 1.0

echo ========================================
echo Compilando el juego de la Escoba...
echo ========================================

REM Limpiamos el directorio bin si existe
if exist bin (
    echo Limpiando directorio bin...
    rmdir /s /q bin
)

REM Creamos el directorio bin
mkdir bin

REM Compilamos el codigo fuente
echo Compilando archivos fuente...
javac -encoding UTF-8 -d bin -cp "lib\*" src\escoba\*.java src\escoba\modelo\*.java src\escoba\vista\*.java src\escoba\vista\util\*.java src\escoba\control\*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Compilacion exitosa!
    echo ========================================
) else (
    echo.
    echo ========================================
    echo ERROR: La compilacion ha fallado.
    echo ========================================
    exit /b 1
)

pause

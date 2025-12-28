@echo off
REM Script de generacion de documentacion JavaDoc
REM Autor: Estudiante
REM Version: 1.0

echo ========================================
echo Generando documentacion JavaDoc...
echo ========================================

REM Limpiamos el directorio doc si existe
if exist doc (
    echo Limpiando directorio doc...
    rmdir /s /q doc
)

REM Creamos el directorio doc
mkdir doc

REM Generamos la documentacion con javadoc
echo Generando documentacion HTML...
javadoc -encoding UTF-8 -docencoding UTF-8 -charset UTF-8 -d doc -sourcepath src -subpackages escoba -private -author -version

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Documentacion generada exitosamente!
    echo Puedes verla abriendo: doc\index.html
    echo ========================================
) else (
    echo.
    echo ========================================
    echo ERROR: La generacion de documentacion ha fallado.
    echo ========================================
    exit /b 1
)

pause

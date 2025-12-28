@echo off
REM Script de verificacion pre-entrega
REM Autor: Estudiante
REM Version: 1.0

echo ================================================================================
echo VERIFICACION PRE-ENTREGA - PRACTICA ESCOBA
echo ================================================================================
echo.

set ERROR=0

echo [1/10] Verificando estructura de directorios...
if not exist src (
    echo    ERROR: Falta el directorio /src
    set ERROR=1
) else (
    echo    OK: Directorio /src existe
)

if not exist lib (
    echo    ERROR: Falta el directorio /lib
    set ERROR=1
) else (
    echo    OK: Directorio /lib existe
)

if not exist test (
    echo    ERROR: Falta el directorio /test
    set ERROR=1
) else (
    echo    OK: Directorio /test existe
)

if not exist images (
    echo    ERROR: Falta el directorio /images
    set ERROR=1
) else (
    echo    OK: Directorio /images existe
)

echo.
echo [2/10] Verificando archivos obligatorios...

if not exist leeme.txt (
    echo    ERROR: Falta el archivo leeme.txt
    set ERROR=1
) else (
    echo    OK: Archivo leeme.txt existe
)

if not exist documentar.bat (
    echo    ERROR: Falta el archivo documentar.bat
    set ERROR=1
) else (
    echo    OK: Archivo documentar.bat existe
)

if not exist ejecutar.bat (
    echo    ERROR: Falta el archivo ejecutar.bat
    set ERROR=1
) else (
    echo    OK: Archivo ejecutar.bat existe
)

echo.
echo [3/10] Verificando clases Java obligatorias...

if not exist src\escoba\modelo\Baza.java (
    echo    ERROR: Falta Baza.java
    set ERROR=1
) else (
    echo    OK: Baza.java existe
)

if not exist src\escoba\modelo\Jugador.java (
    echo    ERROR: Falta Jugador.java
    set ERROR=1
) else (
    echo    OK: Jugador.java existe
)

if not exist src\escoba\control\Controlador.java (
    echo    ERROR: Falta Controlador.java
    set ERROR=1
) else (
    echo    OK: Controlador.java existe
)

echo.
echo [4/10] Verificando package-info.java...

if not exist src\escoba\package-info.java (
    echo    ADVERTENCIA: Falta escoba\package-info.java
) else (
    echo    OK: escoba\package-info.java existe
)

if not exist src\escoba\modelo\package-info.java (
    echo    ADVERTENCIA: Falta escoba\modelo\package-info.java
) else (
    echo    OK: escoba\modelo\package-info.java existe
)

if not exist src\escoba\control\package-info.java (
    echo    ADVERTENCIA: Falta escoba\control\package-info.java
) else (
    echo    OK: escoba\control\package-info.java existe
)

echo.
echo [5/10] Verificando que NO existe module-info.java...

if exist src\module-info.java (
    echo    ERROR CRITICO: Existe module-info.java - DEBE SER ELIMINADO
    set ERROR=1
) else (
    echo    OK: No existe module-info.java
)

echo.
echo [6/10] Verificando bibliotecas...

if not exist lib\hamcrest-all-1.3.jar (
    echo    ERROR: Falta hamcrest-all-1.3.jar
    set ERROR=1
) else (
    echo    OK: hamcrest-all-1.3.jar existe
)

if not exist lib\junit-platform-console-standalone-1.13.4.jar (
    echo    ERROR: Falta junit-platform-console-standalone-1.13.4.jar
    set ERROR=1
) else (
    echo    OK: junit-platform-console-standalone-1.13.4.jar existe
)

if not exist lib\JColor-5.5.1.jar (
    echo    ERROR: Falta JColor-5.5.1.jar
    set ERROR=1
) else (
    echo    OK: JColor-5.5.1.jar existe
)

echo.
echo [7/10] Verificando capturas en /images...

set CAPTURAS=0

if exist images\*.png set CAPTURAS=1
if exist images\*.jpg set CAPTURAS=1

if %CAPTURAS%==0 (
    echo    ADVERTENCIA: No hay capturas en /images
    echo    Recuerda hacer las 5 capturas obligatorias
) else (
    echo    OK: Se han encontrado archivos de imagen
)

echo.
echo [8/10] Intentando compilar...

if exist bin rmdir /s /q bin
mkdir bin

javac -encoding UTF-8 -d bin -cp "lib\*" src\escoba\*.java src\escoba\modelo\*.java src\escoba\vista\*.java src\escoba\vista\util\*.java src\escoba\control\*.java 2>nul

if %ERRORLEVEL% EQU 0 (
    echo    OK: El codigo compila correctamente
) else (
    echo    ERROR: El codigo NO compila
    set ERROR=1
)

echo.
echo [9/10] Intentando generar documentacion...

if exist doc rmdir /s /q doc
mkdir doc

javadoc -encoding UTF-8 -docencoding UTF-8 -charset UTF-8 -d doc -sourcepath src -subpackages escoba -private -author -version >nul 2>nul

if %ERRORLEVEL% EQU 0 (
    echo    OK: La documentacion se genera correctamente
    if exist doc\index.html (
        echo    OK: Archivo doc\index.html generado
    )
) else (
    echo    ADVERTENCIA: Hubo problemas al generar la documentacion
)

echo.
echo [10/10] Verificando tests...

javac -encoding UTF-8 -d bin -cp "bin;lib\*" test\escoba\*.java test\escoba\modelo\*.java test\escoba\control\*.java 2>nul

if %ERRORLEVEL% EQU 0 (
    echo    OK: Los tests compilan correctamente
) else (
    echo    ERROR: Los tests NO compilan
    set ERROR=1
)

echo.
echo ================================================================================
echo RESUMEN DE VERIFICACION
echo ================================================================================

if %ERROR%==0 (
    echo.
    echo    *** TODO CORRECTO ***
    echo.
    echo    Puedes proceder a:
    echo    1. Revisar el archivo leeme.txt y completar tus datos
    echo    2. Realizar las 5 capturas siguiendo INSTRUCCIONES_CAPTURAS.txt
    echo    3. Comprimir todo en un archivo .zip con el formato indicado
    echo    4. Subir a UBUVirtual
    echo.
) else (
    echo.
    echo    *** SE HAN DETECTADO ERRORES ***
    echo.
    echo    Revisa los mensajes anteriores y corrige los errores antes de entregar.
    echo.
)

echo ================================================================================

pause

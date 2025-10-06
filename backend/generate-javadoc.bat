@echo off
REM Script para generar documentación Javadoc del proyecto TurismoUY Backend
REM Autor: TurismoUY Team
REM Uso: generate-javadoc.bat [opción]

setlocal enabledelayedexpansion

cd /d "%~dp0"

echo ==========================================
echo   TurismoUY - Generador de Javadoc
echo ==========================================
echo.

REM Verificar que Maven esté instalado
where mvn >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Error: Maven no está instalado o no está en el PATH
    echo    Por favor instala Maven antes de continuar
    exit /b 1
)

REM Obtener argumento (por defecto: html)
set "OPTION=%~1"
if "%OPTION%"=="" set "OPTION=html"

REM Procesar opciones
if /I "%OPTION%"=="html" goto generate_html
if /I "%OPTION%"=="jar" goto generate_jar
if /I "%OPTION%"=="clean" goto clean_javadoc
if /I "%OPTION%"=="open" goto open_javadoc
if /I "%OPTION%"=="help" goto show_help
if /I "%OPTION%"=="--help" goto show_help
if /I "%OPTION%"=="-h" goto show_help

echo ❌ Opción desconocida: %OPTION%
echo.
goto show_help

:show_help
echo Uso: generate-javadoc.bat [OPCIÓN]
echo.
echo Opciones:
echo   html        Genera documentación HTML (por defecto)
echo   jar         Genera documentación HTML y empaqueta en JAR
echo   clean       Limpia documentación previamente generada
echo   open        Genera y abre la documentación en el navegador
echo   help        Muestra este mensaje de ayuda
echo.
echo Ejemplos:
echo   generate-javadoc.bat           # Genera HTML
echo   generate-javadoc.bat jar       # Genera y empaqueta en JAR
echo   generate-javadoc.bat open      # Genera y abre en navegador
echo.
exit /b 0

:clean_javadoc
echo 🧹 Limpiando documentación anterior...
call mvn clean
if %ERRORLEVEL% EQU 0 (
    echo ✅ Limpieza completada
) else (
    echo ❌ Error al limpiar
    exit /b 1
)
exit /b 0

:generate_html
echo 📚 Generando documentación Javadoc HTML...
call mvn javadoc:javadoc

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Documentación generada exitosamente
    echo 📂 Ubicación: target\site\apidocs\index.html
    echo.
    echo Para visualizar:
    echo   - Abrir: %CD%\target\site\apidocs\index.html
    echo   - O ejecutar: generate-javadoc.bat open
) else (
    echo ❌ Error al generar la documentación
    exit /b 1
)
exit /b 0

:generate_jar
echo 📦 Generando documentación Javadoc y empaquetando en JAR...
call mvn javadoc:jar

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Javadoc JAR generado exitosamente
    echo 📂 Ubicación: target\turismouy.Backend-1.0.0-javadoc.jar
    echo.
    echo El JAR contiene toda la documentación y puede distribuirse
) else (
    echo ❌ Error al generar el JAR de documentación
    exit /b 1
)
exit /b 0

:open_javadoc
call :generate_html

set "JAVADOC_PATH=%CD%\target\site\apidocs\index.html"

if not exist "%JAVADOC_PATH%" (
    echo ❌ Error: No se encontró el archivo de documentación
    exit /b 1
)

echo.
echo 🌐 Abriendo documentación en el navegador...
start "" "%JAVADOC_PATH%"
exit /b 0

endlocal

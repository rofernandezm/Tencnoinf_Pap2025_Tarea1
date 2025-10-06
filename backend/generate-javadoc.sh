#!/bin/bash

# Script para generar documentación Javadoc del proyecto TurismoUY Backend
# Autor: TurismoUY Team
# Uso: ./generate-javadoc.sh [opción]

set -e

BACKEND_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$BACKEND_DIR"

echo "=========================================="
echo "  TurismoUY - Generador de Javadoc"
echo "=========================================="
echo ""

# Verificar que Maven esté instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven no está instalado o no está en el PATH"
    echo "   Por favor instala Maven antes de continuar"
    exit 1
fi

# Función para mostrar ayuda
show_help() {
    cat << EOF
Uso: ./generate-javadoc.sh [OPCIÓN]

Opciones:
  html        Genera documentación HTML (por defecto)
  jar         Genera documentación HTML y empaqueta en JAR
  clean       Limpia documentación previamente generada
  open        Genera y abre la documentación en el navegador
  help        Muestra este mensaje de ayuda

Ejemplos:
  ./generate-javadoc.sh           # Genera HTML
  ./generate-javadoc.sh jar       # Genera y empaqueta en JAR
  ./generate-javadoc.sh open      # Genera y abre en navegador

EOF
}

# Función para limpiar
clean_javadoc() {
    echo "🧹 Limpiando documentación anterior..."
    mvn clean
    echo "✅ Limpieza completada"
}

# Función para generar HTML
generate_html() {
    echo "📚 Generando documentación Javadoc HTML..."
    mvn javadoc:javadoc
    
    if [ $? -eq 0 ]; then
        echo ""
        echo "✅ Documentación generada exitosamente"
        echo "📂 Ubicación: target/site/apidocs/index.html"
        echo ""
        echo "Para visualizar:"
        echo "  - Abrir: file://$BACKEND_DIR/target/site/apidocs/index.html"
        echo "  - O ejecutar: ./generate-javadoc.sh open"
    else
        echo "❌ Error al generar la documentación"
        exit 1
    fi
}

# Función para generar JAR
generate_jar() {
    echo "📦 Generando documentación Javadoc y empaquetando en JAR..."
    mvn javadoc:jar
    
    if [ $? -eq 0 ]; then
        echo ""
        echo "✅ Javadoc JAR generado exitosamente"
        echo "📂 Ubicación: target/turismouy.Backend-1.0.0-javadoc.jar"
        echo ""
        echo "El JAR contiene toda la documentación y puede distribuirse"
    else
        echo "❌ Error al generar el JAR de documentación"
        exit 1
    fi
}

# Función para abrir en navegador
open_javadoc() {
    generate_html
    
    JAVADOC_PATH="$BACKEND_DIR/target/site/apidocs/index.html"
    
    if [ ! -f "$JAVADOC_PATH" ]; then
        echo "❌ Error: No se encontró el archivo de documentación"
        exit 1
    fi
    
    echo ""
    echo "🌐 Abriendo documentación en el navegador..."
    
    # Detectar sistema operativo y abrir navegador
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        xdg-open "$JAVADOC_PATH" 2>/dev/null
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        open "$JAVADOC_PATH"
    elif [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" ]]; then
        start "$JAVADOC_PATH"
    else
        echo "⚠️  No se pudo detectar el sistema operativo"
        echo "   Por favor abre manualmente: $JAVADOC_PATH"
    fi
}

# Procesar argumentos
case "${1:-html}" in
    html)
        generate_html
        ;;
    jar)
        generate_jar
        ;;
    clean)
        clean_javadoc
        ;;
    open)
        open_javadoc
        ;;
    help|--help|-h)
        show_help
        ;;
    *)
        echo "❌ Opción desconocida: $1"
        echo ""
        show_help
        exit 1
        ;;
esac

exit 0

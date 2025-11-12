# Changelog: Revisión Completa de README

## Fecha: Actualización Reciente
## Resumen: Renovación integral de documentación para clarificar arquitectura Web Services SOAP

---

## Cambios Principales

### 1. ✅ Sección "Requisitos del Sistema" - SIMPLIFICADA
**Antes**: 40 líneas con detalles de versiones
**Después**: 25 líneas enfocadas en Java 17, Maven embebido, Eclipse

**Cambios**:
- Eliminada la tabla comparativa "Versión Mínima vs Recomendada"
- Agregada tabla clara de PUERTOS: 8007, 8080, 8005, 9001
- Énfasis en Maven embebido en `/resources/apache-maven-3.9.11-bin.zip`
- Referencia a documentación oficial sin pasos OS-específicos

---

### 2. ✅ Sección "Setup Inicial" (ex "Instalación Paso a Paso") - REVOLUCIONADA
**Antes**: 200+ líneas con instrucciones Linux/Windows/macOS para cada tecnología
**Después**: 80 líneas: solo verificar Java 17, extraer Maven embebido, descargar Eclipse

**Cambios**:
- ❌ Eliminadas instrucciones detalladas para Windows (Adoptium, Oracle, setx)
- ❌ Eliminadas instrucciones para Linux (apt, dnf, brew)
- ❌ Eliminadas instrucciones para macOS (brew, PATH exports)
- ✅ Agregada nota: "Eclipse es la plataforma recomendada (NO ejecutar desde terminal)"
- ✅ Simplificado: solo 3 comandos para extraer Maven embebido

**Impacto**: Reducción de ~120 líneas de instrucciones innecesarias

---

### 3. ✅ Sección "Compilación del Proyecto" - ARQUITECTURA ACLARADA
**Antes**: Descripción JAR incluido en WAR (arquitectura antigua)
**Después**: Arquitectura SOAP con comunicación remota (arquitectura actual)

**Cambios**:
- ❌ Removido: "Instala el JAR en repositorio Maven local"
- ❌ Removido: "El Frontend descarga el Backend desde repositorio Maven"
- ❌ Removido: "Incluye el JAR del Backend dentro del WAR"
- ✅ Agregado: "Backend JAR usado solo por Publisher"
- ✅ Agregado: "Frontend usa stubs SOAP via wsimport (NO depende de JAR)"
- ✅ Agregado: "Frontend descarga WSDLs del Publisher (puerto 8007)"
- ✅ Aclarado: "NO hay dependencia JAR entre módulos (es comunicación SOAP remota)"

**Impacto**: Corrige malentendido fundamental sobre arquitectura

---

### 4. ✅ Quick Start - CLARIFICACIÓN DE PUERTO 8007
**Antes**: Énfasis en Tomcat primero
**Después**: Énfasis en Publisher (puerto 8007) como PREREQUISITO

**Cambios**:
- ✅ Orden crítico: 1) Backend → 2) **Publisher 8007** → 3) Frontend → 4) Tomcat 8080
- ✅ Diagrama ASCII de flujo: 8007 → wsimport → stubs → servlets
- ✅ Nota de puerto 8007 en cada ejemplo
- ✅ Advertencia: "Dejar Publisher corriendo mientras compiles frontend"

**Impacto**: Elimina confusión sobre qué se ejecuta cuándo y dónde

---

### 5. ✅ Sección "Ejecución del Proyecto" - SOLO ECLIPSE (CON FALLBACK TERMINAL)
**Antes**: Opción A (Terminal), Opción B (Eclipse), Opción C (Manual) = ~250 líneas duplicadas
**Después**: 1 opción recomendada (Eclipse) + 1 fallback (Terminal)

**Cambios**:
- ❌ Eliminada: Opción A (Terminal: 100 líneas)
- ❌ Eliminada: Opción C (Manual: 50 líneas)
- ✅ Expandida: Opción B (Eclipse: 150 líneas detalladas)
- ✅ Agregada: Configuración VM Arguments CRÍTICA (db.path, db.name, db.port)
- ✅ Agregado: Checklist pre-ejecución (8 items)
- ✅ Agregado: Fallback de terminal con advertencia "NO RECOMENDADO"

**Nuevo: Pasos 0a, 0b, 0c para setup de Eclipse ANTES de ejecutar**:
- Importar proyectos Maven
- Agregar servidor Tomcat 11
- Configurar VM Arguments (CRÍTICO para HSQLDB)

**Impacto**: Reduce de 250 líneas a 200 pero mucho más claro

---

### 6. ✅ Advertencia de Maven Profiles
**Antes**: Mencionados sin advertencia
**Después**: Claramente marcados como "NO RECOMENDADOS"

**Cambios**:
- ✅ Nueva sección "⚠️ Nota Importante: Profiles No Recomendados"
- ✅ Aclarado: `run-publisher` y `run-desktop` son untested
- ✅ Recomendación: Usar Eclipse en su lugar
- ✅ Ubicación: Junto a "Comandos Maven Útiles"

**Impacto**: Evita que desarrolladores intenten usar profiles problemáticos

---

### 7. ✅ Backend README.md - EXPANDIDO DE 3 A 278 LÍNEAS
**Antes**:
```
# Tencnoinf_Pap2025_Tarea1
Proyecto de Java, GUI (Swing) y persistencia de datos con EclipseLink y Hsqldb
```

**Después**: Documento completo con:
- ✅ Tabla de servicios SOAP (User, Activity, OutingAndInscription)
- ✅ Estructura de directorios detallada
- ✅ Explicación de Publisher.java como punto de entrada
- ✅ Interfaces con @WebService y @SOAPBinding
- ✅ Implementaciones con ejemplo de código
- ✅ DTOs con JAXB (@XmlRootElement)
- ✅ Persistencia JPA en persistence.xml
- ✅ Entidades y relaciones
- ✅ Diagrama ASCII de flujo de invocación SOAP
- ✅ Opciones de ejecución (Eclipse, Terminal, GUI Swing)
- ✅ Troubleshooting específico

**Impacto**: Proporciona documentación de referencia completa para módulo backend

---

## Cifras de Cambio

| Métrica | Antes | Después | Cambio |
|---------|-------|---------|--------|
| Líneas README.md | ~1900 | 1780 | -120 (simplificación) |
| Líneas Backend README.md | 3 | 278 | +275 (documentación nueva) |
| Secciones con opciones redundantes | 3 (A, B, C) | 1 (Eclipse + fallback) | -2 |
| Secciones sobre Puerto 8007 | 2 | 5+ | +3 (ahora clarificado) |
| Advertencias sobre Maven Profiles | 0 | 1 | +1 (seguridad) |
| VM Arguments ejemplos | 0 | 3 (Windows, Linux, macOS) | +3 |
| Líneas de instrucciones OS-específicas | ~300 | ~20 | -280 |

---

## Cambios por Sección del README

### Requisitos del Sistema
- ✅ Simplificado: tabla clara de puertos
- ✅ Agregado: énfasis en Maven embebido

### Setup Inicial
- ✅ Reducido de 200 líneas a 80
- ❌ Eliminadas instrucciones OS-específicas
- ✅ Agregado: "Eclipse es recomendado (NO terminal)"

### Compilación
- ✅ Aclarada arquitectura SOAP (NO JAR en WAR)
- ✅ Agregado: Puerto 8007 como prerequisito
- ✅ Aclarado: Frontend usa stubs SOAP remotos

### Quick Start
- ✅ Nuevo diagrama ASCII de flujo
- ✅ Énfasis en orden: Publisher → Frontend → Tomcat
- ✅ Agregado: Puerto 8007 en título de sección

### Ejecución
- ✅ Opción única: Eclipse (con 8 pasos detallados)
- ✅ Fallback: Terminal (con advertencia)
- ✅ Nuevo: Configuración VM Arguments CRÍTICA
- ✅ Nuevo: Checklist pre-ejecución

### Comandos Maven
- ✅ Nuevo: Sección "⚠️ Profiles No Recomendados"
- ✅ Aclarado: run-publisher y run-desktop NO son recomendados

### Backend README.md
- ✅ 275 líneas nuevas de documentación
- ✅ Arquitectura Web Services explicada
- ✅ Código de ejemplo JAX-WS

---

## Problemas Resueltos

### 1. ❌ Confusión: ¿Es JAR embebido en WAR?
**Solucionado**: Nuevo README explica que es arquitectura SOAP remota

### 2. ❌ ¿Por qué ejecutar Publisher antes que Frontend?
**Solucionado**: Quick Start y diagramas clarifican: wsimport necesita puerto 8007

### 3. ❌ ¿Qué opción de ejecución usar: Terminal, Eclipse, Manual?
**Solucionado**: Eclipse es la única recomendada; terminal es fallback

### 4. ❌ ¿Cómo configurar VM Arguments?
**Solucionado**: 3 ejemplos completos (Windows, Linux, macOS) con rutas reales

### 5. ❌ ¿Funcionan los Maven profiles run-publisher y run-desktop?
**Solucionado**: Marcados claramente como NO recomendados (untested)

### 6. ❌ Backend README es demasiado mínimo
**Solucionado**: 278 líneas de documentación completa de Web Services

---

## Recomendaciones para Próximas Mejoras

1. **Agregar screenshots de Eclipse** (Servers view, Launch Configuration)
2. **Crear video tutorial** de setup inicial
3. **Expandir Frontend README** (similar a Backend README)
4. **Agregar ejercicios de laboratorio** para estudiantes
5. **Documentar pasos de debugging** en Eclipse
6. **Crear documento de "Errores Frecuentes"** separado
7. **Agregar diagrama UML** de entidades y servicios

---

## Validación

- ✅ README.md se lee de forma clara y secuencial
- ✅ Puerto 8007 se menciona en cada paso relevante
- ✅ Eclipse es presentada como opción única recomendada
- ✅ Arquitectura SOAP está claramente explicada
- ✅ VM Arguments están documentados con ejemplos
- ✅ Backend README es documento de referencia completo
- ✅ Profiles problemáticos están marcados

---

## Conclusión

La renovación del README transforma documentación obsoleta (arquitectura JAR) en documentación actual (arquitectura Web Services SOAP). Se elimina confusión y se proporciona guía clara con ejemplos específicos. El Backend README ahora es referencia completa de Web Services.

**Estado**: ✅ COMPLETO


🌐 Language: **Español** | [English](SETUP_EN.md)

# Guía de instalación y ejecución (SETUP)

Este documento explica cómo instalar y ejecutar **TurismoUY** de forma reproducible, con foco en el orden correcto de ejecución (crítico por la generación de stubs SOAP) y en los puertos requeridos.

> Nota: Por requisito del curso, el proyecto está pensado para trabajarse en **Eclipse**. Existen scripts por sistema operativo como apoyo, pero la ruta recomendada para ejecutar y desarrollar es Eclipse.

---

## Tabla de contenidos

- [Requisitos](#requisitos)
- [Puertos requeridos](#puertos-requeridos)
- [Quick Start](#quick-start)
- [Ejecución recomendada en Eclipse](#ejecución-recomendada-en-eclipse)
- [Ejecución alternativa desde terminal](#ejecución-alternativa-desde-terminal)
- [Verificación rápida](#verificación-rápida)
- [Troubleshooting](#troubleshooting)
- [Notas de portabilidad](#notas-de-portabilidad)

---

## Requisitos

- Java JDK 17
- Maven 3.8+ (el repositorio incluye Maven dentro de `resources/` como opción portable)
- Eclipse IDE for Enterprise Java and Web Developers (recomendado)
- Puertos libres (ver sección siguiente)

Verificar Java:

```bash
java -version
```

Verificar Maven:

```bash
mvn -version
```

---

## Puertos requeridos

Asegúrese de tener disponibles estos puertos (configurables, pero se asumen por defecto):

- `8007` → Publisher de Web Services SOAP (Backend)
- `8080` → Tomcat (Frontend web)
- `9001` → HSQLDB (base de datos en modo servidor)
- `8005` → Tomcat Shutdown (interno de Tomcat)

---

## Quick Start

Este es el flujo mínimo correcto. **Respetar el orden es obligatorio**.

### 1) Compilar Backend

```bash
cd backend
mvn clean install
```

### 2) Publicar Web Services SOAP (dejar corriendo)

En una terminal (o Run Configuration en Eclipse), ejecutar:

```bash
cd backend
mvn exec:java -Prun-publisher
```

Cuando esté correcto, debería ver algo como:

- `http://localhost:8007/ws/user?wsdl`
- `http://localhost:8007/ws/activity?wsdl`
- `http://localhost:8007/ws/outingAndInscription?wsdl`

> No cierres este proceso: el frontend necesita estos WSDL disponibles para generar stubs.

### 3) Compilar Frontend (con Publisher activo)

En otra terminal:

```bash
cd frontend
mvn clean package
```

Esto:
- descarga los WSDL del Publisher (8007)
- genera stubs en `frontend/target/generated-sources/wsimport/`
- empaqueta el WAR

### 4) Iniciar Tomcat (sirve la aplicación web)

Recomendado desde Eclipse. Alternativamente desde terminal:

Linux/macOS:

```bash
cd server/apache-tomcat-11.0.11/bin
./startup.sh
```

Windows:

```bat
cd server\apache-tomcat-11.0.11\bin
startup.bat
```

### 5) Abrir la aplicación

```text
http://localhost:8080/turismouy.UI/
```

---

## Ejecución recomendada en Eclipse

### 1) Importar proyectos Maven

1. `File → Import → Maven → Existing Maven Projects`
2. Seleccionar la carpeta raíz del repo
3. Importar:
   - `backend/pom.xml`
   - `frontend/pom.xml`

### 2) Configurar Tomcat 11 en Eclipse

1. `Window → Show View → Servers`
2. `New → Server → Apache → Tomcat v11.0 Server`
3. En **Tomcat installation directory**, apuntar a:

```text
<repo>/server/apache-tomcat-11.0.11
```

4. Asegurar que el runtime use **Java 17**

### 3) Configurar VM Arguments para HSQLDB (crítico)

En el servidor Tomcat dentro de Eclipse:
- abrir `Open launch configuration`
- pestaña `Arguments`
- en `VM arguments` pegar (ajustar la ruta absoluta):

```text
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=RUTA_ABSOLUTA_AL_REPO/server/apache-tomcat-11.0.11/data/turismoUyDB
```

> En Windows, usar `/` (slash) en la ruta incluso si el sistema utiliza `\`.

### 4) Crear Run Configuration para el Publisher (Backend SOAP)

1. `Run → Run Configurations…`
2. `Java Application → New`
3. Configurar:
   - Project: `turismouy.Backend`
   - Main class: `turismouyapp.webservices.Publisher`
   - VM arguments:
     ```text
     -Dcom.sun.xml.ws.spi.db.BindingContextFactory=com.sun.xml.ws.db.glassfish.JAXBRIContextFactory
     ```

Ejecutar y **dejar corriendo**. Verificar endpoints en consola (puerto 8007).

### 5) Compilar Frontend desde Eclipse (con Publisher activo)

Con el Publisher corriendo:
- Click derecho en proyecto frontend → `Run As` / `Maven build…`
- Goals:

```text
clean package
```

### 6) Deploy del WAR en Tomcat

En la vista Servers:
- `Add and Remove…`
- agregar `turismouy.UI`
- iniciar el servidor

---

## Ejecución alternativa desde terminal

> Esta opción existe, pero el camino recomendado para el proyecto (y más estable por requisito) es Eclipse.

### 1) Backend

```bash
cd backend
mvn clean install
```

### 2) Publisher (dejar corriendo)

```bash
cd backend
mvn exec:java -Prun-publisher
```

### 3) Frontend (con Publisher activo)

```bash
cd frontend
mvn clean package
```

### 4) Tomcat

Linux/macOS:

```bash
cd server/apache-tomcat-11.0.11/bin
./startup.sh
```

Windows:

```bat
cd server\apache-tomcat-11.0.11\bin
startup.bat
```

### 5) Abrir aplicación

```text
http://localhost:8080/turismouy.UI/
```

---

## Verificación rápida

### Verificar WSDLs (Publisher activo)

```bash
curl -s http://localhost:8007/ws/user?wsdl | head -n 20
```

Si devuelve XML, está OK.

### Verificar stubs generados

```bash
ls frontend/target/generated-sources/wsimport/turismouyapp/webservices/
```

Debería ver clases como:

- `UserService.java`
- `ActivityService.java`
- `OutingAndInscriptionService.java`

---

## Troubleshooting

### Error: `Connection refused: 127.0.0.1:8007` al compilar frontend

Causa: el Publisher no está corriendo cuando `wsimport` intenta bajar los WSDL.

Solución:

```bash
# Terminal 1
cd backend && mvn exec:java -Prun-publisher

# Terminal 2 (luego de ver endpoints)
cd frontend && mvn clean package
```

---

### Error: `wsimport WSDL download failed`

Causa: Publisher inaccesible o puertos bloqueados.

Solución:
- confirmar que el Publisher está activo (8007)
- confirmar que el puerto está libre
- reintentar compilación del frontend

---

### Error: `Address already in use: 8007 / 8080 / 9001`

Causa: otro proceso ya usa el puerto.

Solución:
- cerrar procesos previos (Tomcat/Publisher/HSQLDB)
- o liberar el puerto manualmente

---

### Stubs no aparecen en `generated-sources/wsimport`

Causa: el frontend compiló sin poder descargar WSDLs.

Solución:

```bash
cd frontend
mvn clean package
```

(asegurando que el Publisher esté activo).

---

## Notas de portabilidad

Este repositorio fue estructurado como **monorepo portable** (no fue requisito del curso) para reducir fricción al ejecutar el proyecto en distintos sistemas operativos:

- Tomcat incluido y preconfigurado dentro del repo (`/server`)
- configuración de base de datos orientada a compartir un directorio estable (`/server/.../data`)
- soporte para Windows/Linux manteniendo el mismo stack y rutas de ejecución

El objetivo es que cualquier persona pueda clonar el repo, respetar el orden de ejecución y correr el proyecto con mínima fricción.
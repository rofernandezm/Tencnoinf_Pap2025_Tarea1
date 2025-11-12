# TurismoUY - Sistema de Gestión Turística

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![Tomcat](https://img.shields.io/badge/Tomcat-11.0.11-yellow.svg)](https://tomcat.apache.org/)
[![HSQLDB](https://img.shields.io/badge/HSQLDB-2.7.2-green.svg)](http://hsqldb.org/)

Sistema de gestión turística desarrollado en Java con arquitectura en capas, utilizando Jakarta EE, JPA (EclipseLink), HSQLDB y Apache Tomcat. El proyecto consta de tres módulos principales: backend (lógica de negocio), frontend (interfaz web) y servidor (Tomcat configurado).

---

## 📋 Tabla de Contenidos

- [🚀 Quick Start](#-quick-start)
- [Requisitos del Sistema](#-requisitos-del-sistema)
- [Instalación Paso a Paso](#-instalación-paso-a-paso)
  - [1. Java JDK 17](#1-java-jdk-17)
  - [2. Maven](#2-maven)
  - [3. Eclipse IDE](#3-eclipse-ide)
- [Compilación del Proyecto](#-compilación-del-proyecto)
  - [Orden de Compilación](#orden-de-compilación)
  - [Verificación](#verificación)
- [🌐 Web Services SOAP](#-web-services-soap)
  - [Flujo de Ejecución Crítico](#flujo-de-ejecución-crítico)
  - [Endpoints SOAP](#endpoints-soap)
  - [Regeneración de Stubs (wsimport)](#regeneración-de-stubs-wsimport)
- **[👉 Ver WEBSERVICES.md para guía completa de WS](#webservicesmd-guía-especializada)**
- [Configuración en Eclipse IDE](#-configuración-en-eclipse-ide)
- [Ejecución del Proyecto](#-ejecución-del-proyecto)
- [✅ Checklist de Configuración](#-checklist-de-configuración)
- [Arquitectura del Proyecto](#-arquitectura-del-proyecto)
- [Documentación Javadoc](#-documentación-javadoc)
- [Estructura de Directorios](#-estructura-de-directorios)
- [Base de Datos](#-base-de-datos)
- [Solución de Problemas](#-solución-de-problemas)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Comandos Maven Útiles](#-comandos-maven-útiles)

---

## 🚀 Quick Start

Para comenzar rápidamente:

1. **Compilar Backend** (genera JAR con servicios):
   ```bash
   cd backend && mvn clean install
   ```

2. **Publicar Web Services en puerto 8007** (desde Eclipse o terminal):
   ```bash
   mvn exec:java -Prun-publisher
   # Verás en consola:
   # [UserWebService] http://localhost:8007/ws/user?wsdl
   # [ActivityWebService] http://localhost:8007/ws/activity?wsdl
   # [OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription?wsdl
   ```
   ⚠️ **Dejar corriendo** - Frontend lo necesita para compilar

3. **Compilar Frontend** (descarga WSDLs del paso 2 y genera stubs):
   ```bash
   cd frontend && mvn clean package
   ```

4. **Iniciar Tomcat desde Eclipse** (puerto 8080 + HSQLDB 9001):
   - Eclipse → Servers view → Start

5. **Acceder a aplicación**:
   ```
   http://localhost:8080/turismouy.UI/
   ```

### Diagrama de Flujo

```
┌─────────────────────────────────────────────────────────┐
│ COMPILACIÓN Y EJECUCIÓN                                 │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  1. mvn clean install                                   │
│     (Backend JAR)                                       │
│           ⬇                                             │
│  2. mvn exec:java -Prun-publisher                       │
│     (Publica WSDL en localhost:8007)                   │
│           ⬇ (wsimport descarga WSDLs)                  │
│  3. mvn clean package                                   │
│     (Frontend WAR con stubs generados)                 │
│           ⬇                                             │
│  4. Eclipse: Start Tomcat (puerto 8080)                │
│     (Tomcat iniciado + HSQLDB en 9001)                 │
│           ⬇                                             │
│  5. Browser: http://localhost:8080/turismouy.UI/       │
│                                                         │
│ PUERTOS:                                                │
│ · 8007: Publisher (Backend WS SOAP)                    │
│ · 8080: Tomcat (Frontend HTTP)                         │
│ · 9001: HSQLDB (Database)                              │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

⚠️ **Orden crítico**: Pasos 2 y 3 deben ser en ese orden exacto.

Para configuración detallada, ver [📋 Tabla de Contenidos](#-tabla-de-contenidos).

---

## 💻 Requisitos del Sistema

### Software Requerido

| Componente | Versión | Descripción |
|------------|---------|-------------|
| **Java JDK** | 17+ | [OpenJDK](https://adoptium.net/) o [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) |
| **Apache Maven** | 3.9.11 | Incluido en `/resources/apache-maven-3.9.11-bin.zip` |
| **Eclipse IDE** | 2024-03+ | [Enterprise Java and Web Developers](https://www.eclipse.org/downloads/packages/) |

### Puertos Requeridos (Configurables)

| Puerto | Servicio | Configuración |
|--------|----------|-------------|
| **8007** | Web Services SOAP (Backend) | `backend/pom.xml` (exec-maven-plugin) |
| **8080** | Apache Tomcat (Frontend) | `server/apache-tomcat-11.0.11/conf/server.xml` |
| **9001** | HSQLDB Database Server | `server/apache-tomcat-11.0.11/bin/setenv.sh` (DB_PORT) |
| **8005** | Tomcat Shutdown | `server/apache-tomcat-11.0.11/conf/server.xml` |

⚠️ **Asegurar que estos puertos estén disponibles antes de iniciar.**

---

## 📦 Setup Inicial

### Requisito: Java 17

Verifica que Java 17 esté instalado:

```bash
java -version
# Debe mostrar: openjdk version "17.x.x" o java version "17.x.x"
```

Si no lo tienes, instala desde [Adoptium](https://adoptium.net/) o [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

### Maven (Incluido en el Repositorio)

Este proyecto incluye Apache Maven 3.9.11 en `/resources/apache-maven-3.9.11-bin.zip`.

**Para usar Maven incluido:**

**Linux/macOS:**
```bash
cd resources
unzip apache-maven-3.9.11-bin.zip
export PATH=$PWD/apache-maven-3.9.11/bin:$PATH
mvn -version
```

**Windows (PowerShell):**
```powershell
cd resources
tar -xf apache-maven-3.9.11-bin.zip
$env:PATH = "$PWD\apache-maven-3.9.11\bin;$env:PATH"
mvn -version
```

Alternativamente, instala Maven desde el sistema (ver [maven.apache.org](https://maven.apache.org/download.cgi)).

### Eclipse IDE

Descarga e instala [Eclipse IDE for Enterprise Java and Web Developers](https://www.eclipse.org/downloads/packages/) (recomendado: 2024-03 o superior).

**⚠️ Eclipse es la plataforma recomendada para ejecutar este proyecto. No se recomienda ejecutar desde terminal.**

---

## 🔨 Compilación del Proyecto

⚠️ **IMPORTANTE**: El Frontend **NO depende del Backend JAR**. 

El sistema usa **Web Services SOAP** para comunicación. El flujo es:
1. Backend se compila y expone servicios en puerto 8007
2. Frontend descarga WSDLs y genera stubs (wsimport)
3. Los stubs invocan servicios SOAP remotos (no son locales)

### Compilación Paso a Paso

#### 1️⃣ Compilar Backend

```bash
cd backend
mvn clean install
```

**Resultado**: `backend/target/turismouy.Backend-1.0.0.jar` con clases de servicios.

#### 2️⃣ Publicar Web Services (Terminal 1 - DEJAR ABIERTA)

```bash
cd backend
mvn exec:java -Prun-publisher
```

Verás en la consola:
```
[UserWebService] http://localhost:8007/ws/user?wsdl
[ActivityWebService] http://localhost:8007/ws/activity?wsdl
[OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription?wsdl
```

⚠️ **Dejar esta terminal abierta mientras compiles frontend.**

#### 3️⃣ Compilar Frontend (Terminal 2)

```bash
cd frontend
mvn clean package
```

**¿Qué hace?**
- Descarga WSDLs del Publisher (puerto 8007)
- Genera stubs en `target/generated-sources/wsimport/`
- Empaqueta todo en `frontend/target/turismouy.UI.war`

**Resultado**: `frontend/target/turismouy.UI.war` desplegable en Tomcat.

### Resumen de Artefactos

| Archivo | Ubicación | Propósito |
|---------|-----------|----------|
| Backend JAR | `backend/target/turismouy.Backend-1.0.0.jar` | Clases de servicios (usado por Publisher) |
| Frontend WAR | `frontend/target/turismouy.UI.war` | Aplicación web (desplegada en Tomcat) |
| Generated Stubs | `frontend/target/generated-sources/wsimport/` | Proxies SOAP (NO editar) |

### Troubleshooting de Compilación

**Error**: `Connection refused: 127.0.0.1:8007`
```
Causa: Publisher no está corriendo
Solución: Ejecutar en Terminal 1 ANTES de compilar frontend
cd backend && mvn exec:java -Prun-publisher
```

**Error**: `Could not resolve dependencies for turismouy.Backend`
```
Causa: Backend no fue compilado
Solución: cd backend && mvn clean install
```

---

## � Web Services SOAP

### ⚠️ Flujo de Ejecución Crítico

El proyecto TurismoUY utiliza **Web Services SOAP (JAX-WS 4.0.3)** para comunicación backend-frontend. El flujo de ejecución es **ESTRICTO** y debe respetarse para evitar errores:

```
┌─────────────────────────────────────────────────────────────┐
│ 1. COMPILAR BACKEND (genera clases webservices)             │
│    $ cd backend && mvn clean install                        │
│    ✅ Crea: turismouy.Backend-1.0.0.jar                    │
└─────────────────────────────────────────────────────────────┘
                            ⬇️
┌─────────────────────────────────────────────────────────────┐
│ 2. PUBLICAR WEB SERVICES (enciende puerto 8007)             │
│    $ cd backend && mvn exec:java -Prun-publisher           │
│    ✅ Publica WSDLs en http://localhost:8007/ws/*?wsdl    │
└─────────────────────────────────────────────────────────────┘
                            ⬇️
┌─────────────────────────────────────────────────────────────┐
│ 3. COMPILAR FRONTEND (wsimport descarga WSDLs)             │
│    $ cd frontend && mvn clean package                       │
│    ✅ Genera stubs en: frontend/target/generated-sources/  │
└─────────────────────────────────────────────────────────────┘
                            ⬇️
┌─────────────────────────────────────────────────────────────┐
│ 4. INICIAR TOMCAT (sirve aplicación web)                   │
│    $ cd server/apache-tomcat-11.0.11/bin                   │
│    $ ./startup.sh                                           │
│    ✅ HSQLDB en puerto 9001, Tomcat en 8080                │
└─────────────────────────────────────────────────────────────┘
                            ⬇️
┌─────────────────────────────────────────────────────────────┐
│ 5. SERVLETS INVOCAN STUBS (para conectar a WS)             │
│    Los servlets usan: ActivityService, UserService, etc.   │
│    ✅ Comunican con Publisher en puerto 8007               │
└─────────────────────────────────────────────────────────────┘
```

### ❌ ¿Qué pasa si NO respetas el flujo?

| Error | Causa | Solución |
|-------|-------|----------|
| `Connection refused: 127.0.0.1:8007` | Publisher no está corriendo | Ejecutar `mvn exec:java -Prun-publisher` en terminal separada |
| `wsimport WSDL download failed` | Publisher no disponible durante compilación | Publicar WS ANTES de compilar frontend |
| `ClassNotFoundException: UserService` | Stubs no regenerados | Compilar frontend DESPUÉS de publicar |
| Datos no persisten en BD | Tomcat no iniciado con BD | Usar `./startup.sh` desde `server/apache-tomcat-11.0.11/bin` |

### Endpoints SOAP

Cuando el **Publisher está activo** (puerto 8007):

| Servicio | URL WSDL | Interfaz | Métodos |
|----------|----------|----------|---------|
| **UserService** | `http://localhost:8007/ws/user?wsdl` | `UserPortType` | consultUserData, dataEntryUser, dataEntryTourist, dataEntrySupplier, modifyUserData, listUsers, updateProfileImageUser |
| **ActivityService** | `http://localhost:8007/ws/activity?wsdl` | `ActivityPortType` | listTouristActivityData, listTouristActivities, activityDataEntry, modifyActivity, consultTouristActivityData, listTouristActivitiesByStatus, listTouristActivitiesBySupplierNickname |
| **OutingAndInscriptionService** | `http://localhost:8007/ws/outingAndInscription?wsdl` | `OutingAndInscriptionPortType` | outingDataEntry, consultTouristOutingData, listOutingInscription, inscriptionDataEntry, listDtInscriptionTouristOutingByTouristNickname |

**Namespace**: `http://ws.turismouyapp/schema`

### Publicar Web Services Manualmente

#### Desde Terminal (Recomendado para Desarrollo)

```bash
cd backend

# Terminal 1: Publicar WS (deja corriendo)
mvn exec:java -Prun-publisher

# Verás en consola (mantén esta terminal abierta):
# [UserWebService] http://localhost:8007/ws/user
# [ActivityWebService] http://localhost:8007/ws/activity
# [OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription
```

#### Desde Eclipse

1. Click derecho en proyecto `backend`
2. **Run As → Run Configurations...**
3. Nueva configuración Java:
   - **Name**: `Publisher WS`
   - **Project**: `turismouy.Backend`
   - **Main class**: `turismouyapp.webservices.Publisher`
   - **VM arguments**: `-Dcom.sun.xml.ws.spi.db.BindingContextFactory=com.sun.xml.ws.db.glassfish.JAXBRIContextFactory`
4. Click **Run** → La consola mostrará los endpoints publicados
5. **Dejar la ejecución activa** mientras desarrollas

#### Verificar Publicación

```bash
# Ver si el puerto 8007 está escuchando
netstat -ano | findstr :8007  # Windows
lsof -i :8007                 # Linux/Mac

# Descarga el WSDL (desde otra terminal)
curl -s http://localhost:8007/ws/user?wsdl | head -20
```

### Regeneración de Stubs (wsimport)

El **frontend automáticamente regenera stubs** cuando compila, siempre y cuando el Publisher esté disponible.

#### Regenerar Stubs Manualmente

```bash
cd frontend

# Los stubs se generan en:
mvn clean package

# Ubicación: frontend/target/generated-sources/wsimport/turismouyapp/webservices/

# Ver stubs generados:
ls target/generated-sources/wsimport/turismouyapp/webservices/

# Esperas ver:
# UserService.java
# UserPortType.java
# ActivityService.java
# ActivityPortType.java
# OutingAndInscriptionService.java
# OutingAndInscriptionPortType.java
# DtUser.java, DtTourist.java, DtSupplier.java, etc.
```

#### ¿Dónde están los Stubs?

| Ubicación | Descripción |
|-----------|-------------|
| `frontend/target/generated-sources/wsimport/` | Stubs generados por wsimport (NO editar) |
| `frontend/src/main/java/turismouyapp/servlets/` | Servlets que USAN los stubs |
| `frontend/pom.xml` | Configuración de wsimport con URLs |

#### Configuración de wsimport en pom.xml

```xml
<plugin>
  <groupId>com.sun.xml.ws</groupId>
  <artifactId>jaxws-maven-plugin</artifactId>
  <version>3.0.0</version>
  <executions>
    <execution>
      <goals>
        <goal>wsimport</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <wsdlUrls>
      <!-- ⚠️ DEBE haber un Publisher activo en estos puertos -->
      <wsdlUrl>http://localhost:8007/ws/user?wsdl</wsdlUrl>
      <wsdlUrl>http://localhost:8007/ws/activity?wsdl</wsdlUrl>
      <wsdlUrl>http://localhost:8007/ws/outingAndInscription?wsdl</wsdlUrl>
    </wsdlUrls>
    <packageName>turismouyapp.webservices</packageName>
    <keep>true</keep>
  </configuration>
</plugin>
```

#### Troubleshooting: Stubs No Se Generan

**Error**: `Connection refused` o `Failed to download WSDL`

**Solución**:
```bash
# 1. Verificar Publisher activo
cd backend && mvn exec:java -Prun-publisher

# 2. Esperar 5 segundos
sleep 5

# 3. En otra terminal, compilar frontend
cd frontend && mvn clean package
```

### Arquitectura de Web Services

```
┌──────────────────────────────────────────────────────────┐
│ BACKEND (puerto 8007)                                    │
├──────────────────────────────────────────────────────────┤
│  IUserWebService (interfaz)                              │
│  UserWebService (implementación)                         │
│  └─ UserController (lógica negocio)                     │
│                                                          │
│  IActivityWebService (interfaz)                          │
│  ActivityWebService (implementación)                     │
│  └─ TouristActivityController (lógica negocio)          │
│                                                          │
│  IOutingAndInscriptionWebService (interfaz)             │
│  OutingAndInscriptionWebService (implementación)        │
│  └─ TouristOutingAndInscriptionController               │
│                                                          │
│  Publisher.java (main que publica los 3 servicios)      │
└──────────────────────────────────────────────────────────┘
         ⬅️ consume (via SOAP) ➡️
┌──────────────────────────────────────────────────────────┐
│ FRONTEND (puerto 8080, servlets)                         │
├──────────────────────────────────────────────────────────┤
│  UserService.java (stub generado por wsimport)          │
│  ActivityService.java (stub generado por wsimport)      │
│  OutingAndInscriptionService.java (generado)            │
│                                                          │
│  Activities.java (servlet)                              │
│  └─ activityWebService.listTouristActivities() (invoca  │
│     stub que llama a backend via SOAP)                  │
│                                                          │
│  ConsultUser.java (servlet)                             │
│  └─ userWebService.consultUserData() (invoca stub)      │
│                                                          │
│  Inscriptions.java (servlet)                            │
│  └─ outingAndInscriptionWebService.inscriptionDataEntry │
└──────────────────────────────────────────────────────────┘
```

---

## �🏗️ Arquitectura del Proyecto

El proyecto TurismoUY está organizado en tres módulos principales:

### **Backend** (`/backend`)
- **Propósito**: Contiene toda la lógica de negocio, controladores, entidades JPA y acceso a datos
- **Empaquetado**: JAR (`turismouy.Backend-1.0.0.jar`)
- **Artefacto**: `backend/target/turismouy.Backend-1.0.0.jar`
- **Características**:
  - Controladores de dominio (usuarios, actividades, salidas, inscripciones)
  - Entidades JPA con EclipseLink
  - DTOs (Data Transfer Objects)
  - Factory pattern para gestión de dependencias
  - Listener para iniciar servidor HSQLDB automáticamente
  - Interfaz Swing para aplicación de escritorio

### **Frontend** (`/frontend`)
- **Propósito**: Interfaz web con servlets Jakarta
- **Empaquetado**: WAR (`turismouy.UI.war`)
- **Artefacto**: `frontend/target/turismouy.UI.war`
- **Características**:
  - Servlets para manejo de peticiones HTTP
  - Dependencia del módulo backend
  - Listener para inicialización de base de datos
  - Configuración web.xml Jakarta EE 5.0

### **Server** (`/server`)
- **Propósito**: Apache Tomcat 11 preconfigurado
- **Características**:
  - Scripts `setenv.sh` y `setenv.bat` para configuración de variables
  - Listener `HsqldbServerListener` registrado en `server.xml`
  - Librerías compartidas en `/lib`: HSQLDB, EclipseLink, Jakarta Persistence API
  - Base de datos HSQLDB almacenada en `/data`



## 📚 Documentación Javadoc

### Documentación del Backend

La documentación Javadoc del backend incluye todas las clases, interfaces, DTOs, entidades JPA y controladores.

#### Ubicación de la Documentación

```
backend/target/site/apidocs/
├── index.html                          # Página principal
├── overview-tree.html                  # Árbol de jerarquía
├── allclasses-index.html              # Índice de todas las clases
├── deprecated-list.html               # Elementos deprecados
├── desktop/                       # Paquete GUI Swing
│   ├── Main.html
│   └── ...
└── turismouyapp/core/
    ├── controller/                    # Controladores de negocio
    │   ├── UserController.html
    │   ├── TouristActivityController.html
    │   └── TouristOutingAndInscriptionController.html
    ├── entity/                        # Entidades JPA
    │   ├── User.html
    │   ├── Tourist.html
    │   ├── Supplier.html
    │   ├── TouristActivity.html
    │   ├── TouristOuting.html
    │   └── Inscription.html
    ├── dto/                           # Data Transfer Objects
    │   ├── DtUser.html
    │   ├── DtTourist.html
    │   ├── DtSupplier.html
    │   └── ...
    ├── interfaces/                    # Interfaces de controladores
    │   ├── IUserController.html
    │   ├── ITouristActivityController.html
    │   └── ITouristOutingAndInscriptionController.html
    ├── exceptions/                    # Excepciones personalizadas
    ├── factory/                       # Factory pattern
    ├── handler/                       # Handlers de persistencia
    └── db/                           # Gestión base de datos
```

#### Visualizar la Documentación

Abrir el archivo `backend/target/site/apidocs/index.html` en un navegador web.

### Contenido de la Documentación del Backend

La documentación Javadoc incluye:

#### **Página de Overview** (`overview.html`)
- Descripción general del sistema
- Arquitectura en capas
- Patrones de diseño utilizados
- Modelo de dominio con relaciones
- Flujos de trabajo típicos
- Ejemplos de código

#### **Documentación de Paquetes**

Cada paquete tiene su archivo `package-info.java` con documentación detallada:

| Paquete | Descripción | Componentes Clave |
|---------|-------------|-------------------|
| `desktop` | Interfaz Swing (GUI escritorio) | Main, CreateUser, CreateActivity |
| `turismouyapp.core` | Core del sistema | Raíz de todos los componentes backend |
| `core.controller` | Lógica de negocio | UserController, TouristActivityController |
| `core.entity` | Entidades JPA | User, Tourist, Supplier, TouristActivity |
| `core.dto` | Data Transfer Objects | DtUser, DtTourist, DtActivity |
| `core.interfaces` | Contratos de controladores | IUserController, ITouristActivityController |
| `core.handler` | Persistencia y DAO | UserHandler, TouristActivityHandler |
| `core.factory` | Factory pattern | FactoryUyTourism |
| `core.exceptions` | Excepciones de negocio | RepeatedUserNicknameException, etc. |
| `core.db` | Gestión de base de datos | HsqldbServerListener |

#### **Documentación de Clases**

Cada clase incluye:
- ✅ Descripción de propósito y responsabilidades
- ✅ Documentación de métodos públicos y protegidos
- ✅ Parámetros con tipos y descripciones
- ✅ Valores de retorno documentados
- ✅ Excepciones que pueden lanzarse
- ✅ Ejemplos de uso (cuando aplica)
- ✅ Referencias cruzadas (@see, @link)

#### **Índices y Referencias**

- **All Classes**: Índice alfabético de todas las clases
- **Class Hierarchy**: Árbol de herencia de clases
- **Deprecated List**: Elementos deprecados (si los hay)
- **Index**: Índice de todos los elementos

---

### Documentación del Frontend

La documentación Javadoc del frontend está disponible en el directorio `frontend/doc/`.

#### Ubicación de la Documentación

```
frontend/doc/
├── index.html                          # Página principal (redirige al paquete)
├── allclasses-index.html              # Índice de todas las clases
├── overview-tree.html                 # Árbol de jerarquía
└── turismouyapp/servlets/
    ├── package-summary.html           # Descripción completa del paquete servlets
    ├── Login.html                     # Servlet principal (página de inicio)
    └── DbServerPublish.html           # Listener de HSQLDB
```

#### Documentación del Paquete `turismouyapp.servlets`

El paquete contiene la capa de presentación web con arquitectura en tres capas:

| Componente | Tipo | Descripción |
|------------|------|-------------|
| **Login** | Servlet | Servlet principal mapeado a "/" que muestra listado de turistas y proveedores |
| **DbServerPublish** | Listener | ServletContextListener que gestiona el ciclo de vida de HSQLDB embebido |
| **package-info** | Documentación | Descripción completa del paquete, arquitectura, patrones y dependencias |

#### Visualizar la Documentación

Abrir el archivo `frontend/doc/index.html` en un navegador web.

---

## 🖥️ Configuración del Servidor

### Estructura del Servidor Tomcat

```
server/apache-tomcat-11.0.11/
├── bin/                      # Scripts de inicio/parada
│   ├── setenv.sh            # Variables de entorno (Linux/Mac)
│   ├── setenv.bat           # Variables de entorno (Windows)
│   ├── startup.sh           # Iniciar servidor (Linux/Mac)
│   ├── startup.bat          # Iniciar servidor (Windows)
│   ├── shutdown.sh          # Detener servidor (Linux/Mac)
│   └── shutdown.bat         # Detener servidor (Windows)
├── conf/                     # Configuración
│   ├── server.xml           # Configuración principal (puerto 8080, listener HSQLDB)
│   ├── context.xml          # Contexto de aplicaciones
│   └── tomcat-users.xml     # Usuarios y roles
├── lib/                      # Librerías compartidas
│   ├── hsqldb.jar           # Driver HSQLDB 2.7.2
│   ├── eclipselink-4.0.2.jar
│   ├── jakarta.persistence-api-3.1.0.jar
│   └── HsqldbServerListener.jar  # Listener personalizado
├── data/                     # Base de datos HSQLDB
│   └── turismoUyDB.*        # Archivos de BD
├── logs/                     # Logs del servidor
├── webapps/                  # Aplicaciones desplegadas (deployment automático)
└── wtpwebapps/              # Deployment de Eclipse WTP
```

### Variables de Entorno Configurables

Los scripts `setenv.sh` y `setenv.bat` definen variables personalizables:

| Variable | Valor por Defecto | Descripción |
|----------|-------------------|-------------|
| `DB_PORT` | `9001` | Puerto del servidor HSQLDB |
| `DB_NAME` | `turismoUyDB` | Nombre de la base de datos |
| `DB_PATH` | `<CATALINA_BASE>/data/turismoUyDB` | Ruta donde se almacenan los archivos de BD |


### Listener HSQLDB en Tomcat

El archivo `server.xml` incluye el listener que inicia HSQLDB automáticamente:

```xml
<Listener className="turismouyapp.core.db.HsqldbServerListener"/>
```

Este listener:
- ✅ Inicia el servidor HSQLDB en el puerto 9001 cuando Tomcat arranca
- ✅ Verifica si el puerto ya está en uso (evita duplicados)
- ✅ Detiene HSQLDB automáticamente cuando Tomcat se detiene
- ✅ Usa las variables de entorno configuradas en `setenv.sh/bat`

---

## 🌐 Configuración en Eclipse IDE

### Paso 1: Importar Proyectos Maven

1. Abrir Eclipse
2. **File → Import → Maven → Existing Maven Projects**
3. Seleccionar el directorio raíz del repositorio clonado
4. Marcar ambos proyectos:
   - `backend/pom.xml`
   - `frontend/pom.xml`
5. Click en **Finish**

### Paso 2: Configurar Apache Tomcat en Eclipse

#### Agregar Servidor Tomcat

1. Ir a **Window → Show View → Servers**
2. En la vista Servers, click derecho → **New → Server**
3. Seleccionar:
   - **Server type**: Apache → Tomcat v11.0 Server
   - **Server runtime environment**: Click en "Add..."
4. Configurar Runtime:
   - **Name**: `Apache Tomcat 11`
   - **Tomcat installation directory**: Navegar a `<proyecto>/server/apache-tomcat-11.0.11`
   - **JRE**: Java 17
5. Click **Finish**

#### Configurar Servidor para el Proyecto

1. Doble click en el servidor creado para abrir configuración
2. **Server Locations**:
   - Seleccionar: ⚫ **Use Tomcat installation (takes control of Tomcat installation)**
   - **Deploy path**: `wtpwebapps`
3. **Server Options**:
   - ✅ **Publish module contexts to separate XML files**
4. **Ports**:
   - HTTP/1.1: `8080`
   - Shutdown: `8005`
5. **Timeouts**:
   - Start: `90` segundos
   - Stop: `30` segundos
6. Guardar (`Ctrl+S`)

### Paso 3: Desplegar Aplicación Frontend

1. Click derecho en el servidor → **Add and Remove...**
2. Seleccionar `turismouy.UI` del lado izquierdo
3. Click **Add >**
4. Click **Finish**

### Paso 4: Configurar VM Arguments (CRÍTICO)

Los VM Arguments son necesarios para que el servidor HSQLDB encuentre la base de datos.

#### ¿Por Qué Son Necesarios?

Eclipse ejecuta Tomcat en un entorno aislado. Sin estas variables:
- ❌ HSQLDB no sabrá dónde guardar los datos
- ❌ La BD se creará en ubicación temporal
- ❌ Los datos se perderán al reiniciar

#### Configuración Paso a Paso

1. **Vista Servers** → Doble click en "Tomcat v11.0 Server"
2. Click en **"Open launch configuration"**
3. Pestaña **"Arguments"**
4. En el campo **"VM arguments"**, PEGAR lo siguiente:

**Template (COPIAR Y MODIFICAR):**
```
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=RUTA_COMPLETA_AL_PROYECTO/server/apache-tomcat-11.0.11/data/turismoUyDB
```

#### ¿Cómo Obtener la Ruta Completa?

**Windows (PowerShell/CMD):**
```cmd
cd server\apache-tomcat-11.0.11
cd
REM Copiar la ruta mostrada y agregar \data\turismoUyDB
```

**Linux/macOS:**
```bash
cd server/apache-tomcat-11.0.11
pwd
# Copiar la ruta mostrada y agregar /data/turismoUyDB
```

#### Ejemplos Completos

**Windows:**
```
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=C:/Users/juan/eclipse-workspace/turismouyApp/server/apache-tomcat-11.0.11/data/turismoUyDB
```

⚠️ **Nota**: Usar `/` (slash) en lugar de `\` (backslash) incluso en Windows.

**Linux:**
```
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=/home/maria/workspace/turismouyApp/server/apache-tomcat-11.0.11/data/turismoUyDB
```

**macOS:**
```
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=/Users/carlos/Documents/turismouyApp/server/apache-tomcat-11.0.11/data/turismoUyDB
```

5. Click **OK**
6. **Guardar** la configuración del servidor (File → Save o Ctrl+S)

#### Verificación

Al iniciar el servidor, deberías ver en la consola de Eclipse:
```
[setenv.sh] db.port=9001
[setenv.sh] db.name=turismoUyDB
[setenv.sh] db.path=/ruta/completa/...
[DB] HSQLDB iniciado por Tomcat en puerto 9001
```

Si NO ves estos mensajes, los VM Arguments no están configurados correctamente.

### Paso 5: Verificar Context Path

El `server.xml` de Tomcat debe contener una línea como esta (Eclipse la genera automáticamente):

```xml
<Context docBase="<workspace>/turismouyApp/server/apache-tomcat-11.0.11/wtpwebapps/turismouy.UI" 
         path="/turismouy.UI" 
         reloadable="true" 
         source="org.eclipse.jst.jee.server:turismouy.UI"/>
```

Si necesitas editarlo manualmente:
- **Ruta**: `server/apache-tomcat-11.0.11/conf/server.xml`
- **Ajustar**: El atributo `docBase` debe apuntar a tu workspace

### Paso 6: Configuración de JRE en Proyectos

Asegurarse que ambos proyectos usen Java 17:

1. Click derecho en proyecto → **Properties**
2. **Java Build Path → Libraries**:
   - Verificar que JRE System Library sea Java 17
3. **Java Compiler**:
   - Compiler compliance level: **17**

### Resumen de Configuración en Eclipse

**Checklist antes de iniciar el servidor:**

- ✅ Proyectos backend y frontend importados correctamente
- ✅ Servidor Tomcat 11 agregado y configurado
- ✅ **VM Arguments configurados** (`-Ddb.port`, `-Ddb.name`, `-Ddb.path`)
- ✅ Proyecto `turismouy.UI` desplegado en el servidor
- ✅ Puerto 8080 y 9001 disponibles
- ✅ JRE Java 17 configurado en ambos proyectos

---

## ✅ Checklist de Configuración

Antes de ejecutar por primera vez, verifica:

### Requisitos Previos
- [ ] Java 17 instalado (`java -version`)
- [ ] Maven instalado (`mvn -version`)
- [ ] Eclipse IDE instalado (opcional)
- [ ] **Puerto 8007 libre** (Web Services SOAP)
- [ ] **Puerto 8080 libre** (Tomcat HTTP)
- [ ] **Puerto 9001 libre** (HSQLDB)

### Compilación
- [ ] Backend compilado exitosamente (`mvn clean install`)
- [ ] Archivo `backend/target/turismouy.Backend-1.0.0.jar` existe
- [ ] Frontend compilado exitosamente (`mvn clean package`)
- [ ] Archivo `frontend/target/turismouy.UI.war` existe
- [ ] Stubs generados en `frontend/target/generated-sources/wsimport/`

### Web Services SOAP (CRÍTICO)
- [ ] Publisher puede iniciar sin errores (`mvn exec:java -Prun-publisher`)
- [ ] **Publisher inicia EN TERMINAL SEPARADA** (antes de compilar frontend)
- [ ] Frontend se compila DESPUÉS de publicar WS
- [ ] Stubs se generaron desde WSDL (verificar `target/generated-sources/wsimport/`)
- [ ] URLs en `frontend/pom.xml` apuntan a `localhost:8007` 

### Configuración Eclipse (si usas Eclipse)
- [ ] Proyectos importados como Maven projects
- [ ] Servidor Tomcat 11 agregado
- [ ] Tomcat apunta a `server/apache-tomcat-11.0.11`
- [ ] **VM Arguments configurados** (`-Ddb.port`, `-Ddb.name`, `-Ddb.path`)
- [ ] Proyecto `turismouy.UI` desplegado en el servidor
- [ ] Java 17 configurado en ambos proyectos
- [ ] Launch Configuration para Publisher creado

### Ejecución (ORDEN IMPORTANTE)
- [ ] **Terminal 1**: Publisher activo (`mvn exec:java -Prun-publisher`)
- [ ] **Terminal 2**: Frontend compilado CON Publisher activo
- [ ] **Terminal 3**: Tomcat iniciado (`./startup.sh` o `startup.bat`)
- [ ] HSQLDB iniciado automáticamente por Tomcat (ver logs)
- [ ] Aplicación accesible en `http://localhost:8080/turismouy.UI/`
- [ ] Logs muestran:
  ```
  [UserWebService] http://localhost:8007/ws/user
  [ActivityWebService] http://localhost:8007/ws/activity
  [OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription
  [DB] HSQLDB iniciado por Tomcat en puerto 9001
  Server startup in [xxxx] milliseconds
  ```

**Si todos los checkboxes están marcados, ¡estás listo!** 🎉

---

## ▶️ Ejecución del Proyecto

### ⚠️ FLUJO RECOMENDADO: ECLIPSE (RECOMENDADO) + Terminals para Compila

### Paso a Paso: Ejecución Recomendada (Eclipse)

#### Paso 0a: Importar Proyectos en Eclipse

1. **File → Import → Maven → Existing Maven Projects**
2. Seleccionar directorio raíz del proyecto
3. Marcar:
   - `backend/pom.xml`
   - `frontend/pom.xml`
4. **Finish**

#### Paso 0b: Configurar Tomcat en Eclipse

1. **Window → Show View → Servers**
2. Click derecho → **New → Server**
3. **Server type**: Apache → Tomcat v11.0 Server
4. **Tomcat installation directory**: `<proyecto>/server/apache-tomcat-11.0.11`
5. **JRE**: Java 17
6. **Finish**

#### Paso 0c: Configurar VM Arguments para HSQLDB (CRÍTICO)

1. Vista **Servers** → Doble click en "Tomcat v11.0 Server"
2. Click **"Open launch configuration"**
3. Pestaña **Arguments** → Campo **VM arguments**
4. Pegar (reemplazar rutas):

```
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=/RUTA_ABSOLUTA/server/apache-tomcat-11.0.11/data/turismoUyDB
```

**Ejemplo Windows:**
```
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=C:/Users/juan/eclipse-workspace/turismouyApp/server/apache-tomcat-11.0.11/data/turismoUyDB
```

**Ejemplo Linux:**
```
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=/home/juan/workspace/turismouyApp/server/apache-tomcat-11.0.11/data/turismoUyDB
```

5. **OK** → Guardar configuración

⚠️ **Verificación**: En los logs de Tomcat deberías ver:
```
[setenv.sh] db.port=9001
[DB] HSQLDB iniciado por Tomcat en puerto 9001
```

#### Paso 1: Compilar Backend

```bash
cd backend
mvn clean install
```

#### Paso 2: Crear Launch Configuration para Publisher

1. **Run → Run Configurations...**
2. **New Java Application**:
   - **Name**: `TurismoUY Publisher WS`
   - **Project**: `turismouy.Backend`
   - **Main class**: `turismouyapp.webservices.Publisher`
   - **VM arguments**: `-Dcom.sun.xml.ws.spi.db.BindingContextFactory=com.sun.xml.ws.db.glassfish.JAXBRIContextFactory`
3. **Run** → Verás en consola:
   ```
   [UserWebService] http://localhost:8007/ws/user?wsdl
   [ActivityWebService] http://localhost:8007/ws/activity?wsdl
   [OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription?wsdl
   ```

⚠️ **Dejar esta ejecución activa** (no cerrar consola)

#### Paso 3: Compilar Frontend (con Publisher activo)

1. Click derecho en proyecto `turismouy.UI`
2. **Maven → Maven Build**:
   - **Goals**: `clean package`
3. **Run** → wsimport descargará WSDLs y generará stubs

#### Paso 4: Desplegar en Tomcat

1. Vista **Servers** → Click derecho en servidor → **Add and Remove...**
2. Seleccionar `turismouy.UI` → Click **Add >**
3. **Finish**

#### Paso 5: Iniciar Tomcat

1. Vista **Servers** → Click botón **Start** (▶️)
2. Espera en la consola de Eclipse:
   ```
   [DB] HSQLDB iniciado por Tomcat en puerto 9001
   INFO: Server startup in [xxxx] milliseconds
   ```

#### Paso 6: Acceder a Aplicación

```
http://localhost:8080/turismouy.UI/
```

#### Resumen Visual (Estado de Ejecución)

```
┌─────────────────────────────────────────────────────┐
│ Console Tab 1 [Publisher WS]                        │
│ [UserWebService] http://localhost:8007/ws/user     │
│ (CORRIENDO - NO CERRAR)                            │
├─────────────────────────────────────────────────────┤
│ Console Tab 2 [Tomcat v11.0 Server]                │
│ [DB] HSQLDB iniciado por Tomcat en puerto 9001     │
│ (CORRIENDO - Ver cambios en vivo)                  │
├─────────────────────────────────────────────────────┤
│ Serv Vista: Tomcat v11.0 Server [Started]          │
│ http://localhost:8080/turismouy.UI/  ← Acceder aquí │
└─────────────────────────────────────────────────────┘
```

#### Detener Aplicación

Vista **Servers** → Click botón **Stop** (Stop button)

### Alternativa: Ejecución desde Terminal (NO Recomendado - Solo Fallback)

Si Eclipse no funciona, puedes usar terminal (pero con configuración manual):

```bash
# Terminal 1: Compilar Backend
cd backend
mvn clean install

# Terminal 1: Publicar Publisher (DEJAR ABIERTA)
mvn exec:java -Prun-publisher
# Verás endpoints en localhost:8007

# Terminal 2: Compilar Frontend (DESPUÉS de ver endpoints)
cd frontend
mvn clean package

# Terminal 3: Iniciar Tomcat
cd server/apache-tomcat-11.0.11
./bin/startup.sh    # Linux/macOS
# o startup.bat en Windows

# Acceder a http://localhost:8080/turismouy.UI/
```

⚠️ **Nota**: Esta opción requiere:
- Configurar manualmente `setenv.sh` o `setenv.bat` con DB_PATH
- Perder la capacidad de Eclipse de hot-reload cambios
- Se recomienda fuertemente usar Eclipse en su lugar

## 📁 Estructura de Directorios

### Backend (`/backend`)

```
backend/
├── pom.xml                          # Maven: dependencias EclipseLink, HSQLDB, Jakarta
├── src/main/java/
│   ├── desktop/                # Interfaz Swing (aplicación escritorio)
│   │   ├── Main.java               # Punto de entrada GUI
│   │   ├── CreateUser.java
│   │   ├── CreateActivity.java
│   │   └── ...                     # Formularios Swing
│   └── turismouyapp/core/
│       ├── controller/             # Lógica de negocio
│       ├── db/                     # Gestión de base de datos
│       │   └── HsqldbServerListener.java  # Listener Tomcat para HSQLDB
│       ├── dto/                    # Data Transfer Objects
│       ├── entity/                 # Entidades JPA (@Entity)
│       ├── exceptions/             # Excepciones personalizadas
│       ├── factory/                # Factory pattern (FactoryUyTourism)
│       ├── handler/                # Manejadores de eventos
│       └── interfaces/             # Interfaces de controladores
├── src/main/resources/
│   ├── META-INF/
│   │   └── persistence.xml         # Configuración JPA (HSQLDB en puerto 9001)
│   └── texts.properties            # i18n para GUI Swing
├── data/db/                         # Base de datos embebida (desarrollo local)
│   ├── turismouydb.script
│   ├── turismouydb.properties
│   └── turismouydb.log
└── target/
    ├── turismouy.Backend-1.0.0.jar  # JAR compilado
    └── original-turismouy.Backend-1.0.0.jar  # JAR sin shade plugin
```

### Frontend (`/frontend`)

```
frontend/
├── pom.xml                          # Maven: dependencias backend, Jakarta Servlet, maven-javadoc-plugin
├── src/main/java/
│   └── turismouyapp/servlets/
│       ├── Login.java              # Servlet principal (mapeo "/")
│       ├── DbServerPublish.java    # Listener alternativo para iniciar HSQLDB
│       └── package-info.java       # Documentación del paquete servlets
├── src/main/webapp/
│   ├── META-INF/
│   │   └── MANIFEST.MF
│   └── WEB-INF/
│       ├── web.xml                 # Descriptor Jakarta EE 5.0
│       └── lib/                    # Librerías empaquetadas en WAR
├── doc/                             # Documentación Javadoc (versionada)
│   ├── index.html                  # Página principal de Javadoc
│   └── turismouyapp/servlets/      # Documentación del paquete servlets
│       ├── package-summary.html   # Descripción completa del paquete
│       ├── Login.html             # Documentación del servlet Login
│       └── DbServerPublish.html   # Documentación del listener
└── target/
    ├── turismouy.UI.war            # WAR desplegable
    └── site/apidocs/               # Javadoc recién generada (gitignored)
```

### Server (`/server`)

```
server/apache-tomcat-11.0.11/
├── bin/
│   ├── setenv.sh                   # Variables de entorno (Linux/Mac)
│   ├── setenv.bat                  # Variables de entorno (Windows)
│   ├── startup.sh / .bat           # Scripts de inicio
│   └── shutdown.sh / .bat          # Scripts de parada
├── conf/
│   ├── server.xml                  # Puerto 8080, listener HSQLDB, context del WAR
│   ├── context.xml                 # Configuración global de contexto
│   └── web.xml                     # Configuración global de servlets
├── lib/
│   ├── hsqldb.jar                  # Driver HSQLDB 2.7.2
│   ├── eclipselink-4.0.2.jar       # Implementación JPA
│   ├── jakarta.persistence-api-3.1.0.jar
│   └── HsqldbServerListener.jar    # Listener compilado
├── data/
│   └── turismoUyDB.*               # Base de datos HSQLDB (producción)
├── logs/                            # Logs de Tomcat
│   ├── catalina.out                # Log principal
│   ├── catalina.YYYY-MM-DD.log
│   └── localhost_access_log.*.txt
├── webapps/                         # Deployment automático (drop WARs aquí)
└── wtpwebapps/                      # Deployment de Eclipse WTP
```

---

## 🗄️ Base de Datos

### HSQLDB - Configuración

**Tipo**: Base de datos relacional embebida en Java  
**Modo de Operación**: Servidor en memoria/archivo (híbrido)  
**Puerto**: `9001`  
**Nombre**: `turismoUyDB`  
**Usuario**: `SA` (System Administrator)  
**Contraseña**: (vacía)

### Modos de Conexión

#### 1. Modo Servidor (Server Mode) - **USADO EN PRODUCCIÓN**
```
jdbc:hsqldb:hsql://localhost:9001/turismoUyDB
```
- ✅ Permite múltiples conexiones concurrentes
- ✅ Usado por aplicación web y escritorio simultáneamente
- ✅ Iniciado automáticamente por `HsqldbServerListener` en Tomcat

#### 2. Modo Archivo (File Mode) - **DESARROLLO/TESTING**
```
jdbc:hsqldb:file:./data/db/turismoUyDB;shutdown=true
```
- ⚠️ Solo una conexión a la vez
- ✅ Útil para testing unitario
- ⚠️ Comentado en `persistence.xml` por defecto

### Archivos de Base de Datos

| Archivo | Descripción |
|---------|-------------|
| `turismoUyDB.script` | DDL (CREATE TABLE, etc.) y datos iniciales |
| `turismoUyDB.properties` | Metadatos y versión |
| `turismoUyDB.log` | Transacciones no commiteadas |
| `turismoUyDB.data` | Datos binarios (si existen datos extensos) |

### Gestión Manual de BD

#### Conectar con Cliente HSQLDB Manager

```bash
java -cp server/apache-tomcat-11.0.11/lib/hsqldb.jar org.hsqldb.util.DatabaseManagerSwing \
  --url jdbc:hsqldb:hsql://localhost:9001/turismoUyDB \
  --user SA \
  --password ""
```

#### Backup de BD

**Linux/macOS:**
```bash
cp -r server/apache-tomcat-11.0.11/data/turismoUyDB.* ~/backups/db_$(date +%Y%m%d)/
```

**Windows:**
```cmd
xcopy server\apache-tomcat-11.0.11\data\turismoUyDB.* C:\backups\db_%date:~-4,4%%date:~-10,2%%date:~-7,2%\ /Y
```

#### Resetear BD (⚠️ Elimina todos los datos)

```bash
# Detener Tomcat primero
cd server/apache-tomcat-11.0.11
rm -f data/turismoUyDB.*  # Linux/Mac
del data\turismoUyDB.*    # Windows

# Reiniciar Tomcat - EclipseLink recreará las tablas vacías
```

### Configuración JPA (persistence.xml)

```xml
<persistence-unit name="turismoUyDB-Server" transaction-type="RESOURCE_LOCAL">
  <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
  
  <properties>
    <property name="jakarta.persistence.jdbc.driver" value="org.hsqldb.jdbc.JDBCDriver"/>
    <property name="jakarta.persistence.jdbc.url" value="jdbc:hsqldb:hsql://localhost:9001/turismoUyDB"/>
    <property name="jakarta.persistence.jdbc.user" value="SA"/>
    <property name="jakarta.persistence.jdbc.password" value=""/>
    
    <!-- EclipseLink crea/actualiza tablas automáticamente -->
    <property name="eclipselink.ddl-generation" value="create-or-extend-tables"/>
    <property name="eclipselink.ddl-generation.output-mode" value="database"/>
    <property name="eclipselink.logging.level" value="INFO"/>
  </properties>
</persistence-unit>
```

---

## 🔧 Solución de Problemas

### ⚠️ Problemas de Web Services SOAP (Leer Primero)

#### Problema 0a: wsimport Falla - "Connection refused: 127.0.0.1:8007"

**Síntomas:**
```
[ERROR] Failed to download WSDL: http://localhost:8007/ws/user?wsdl
[ERROR] Connection refused: 127.0.0.1:8007
[ERROR] BUILD FAILURE
```

**Causa**: El Publisher NO está corriendo cuando intentas compilar frontend.

**Solución**:
```bash
# Terminal 1: Asegúrate que el Publisher esté corriendo
cd backend
mvn exec:java -Prun-publisher

# Espera a ver en la consola:
# [UserWebService] http://localhost:8007/ws/user
# [ActivityWebService] http://localhost:8007/ws/activity
# [OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription

# Terminal 2: DESPUÉS de ver los endpoints, compila frontend
cd frontend
mvn clean package
```

⚠️ **Nunca compiles frontend sin que Publisher esté activo**

#### Problema 0b: Stubs No Se Generan en `generated-sources/wsimport/`

**Síntomas**:
```
[INFO] Building war: .../frontend/target/turismouy.UI.war
[WARNING] No artifacts to generate SOAP stubs from
```

**Causa**: El plugin wsimport no pudo descargar los WSDLs.

**Verificación**:
```bash
# Verificar que Publisher esté corriendo
curl http://localhost:8007/ws/user?wsdl

# Si ves XML, está bien. Si ves error, inicia Publisher:
cd backend && mvn exec:java -Prun-publisher
```

**Solución**:
```bash
# 1. Asegurar Publisher activo (ver arriba)
# 2. Limpiar caché de Maven
rm -rf ~/.m2/repository/turismouyapp/

# 3. Recompilar frontend
cd frontend
mvn clean package

# 4. Verificar stubs generados
ls target/generated-sources/wsimport/turismouyapp/webservices/
# Debe listar: UserService.java, ActivityService.java, etc.
```

#### Problema 0c: Servlets Dicen "ClassNotFoundException: UserService"

**Síntomas**:
```
java.lang.ClassNotFoundException: turismouyapp.webservices.UserService
```

**Causa**: Los stubs no fueron generados (ver Problema 0b).

**Solución**:
```bash
# 1. Verificar que los stubs existen:
find frontend/target -name "UserService.java" -type f

# Si no existen:
# - Publisher debe estar activo
# - Compilar frontend: mvn clean package

# 2. En Eclipse: Maven → Update Project (limpia caché)
```

#### Problema 0d: "Address already in use: port 8007"

**Síntomas**:
```
java.net.BindException: Address already in use: 127.0.0.1:8007
```

**Causa**: Ya hay un Publisher corriendo en ese puerto.

**Solución**:
```bash
# Linux/macOS: Ver qué está usando el puerto
lsof -i :8007

# Windows: Ver proceso
netstat -ano | findstr :8007

# Matar el proceso (reemplaza PID)
kill -9 <PID>  # Linux/macOS
taskkill /PID <PID> /F  # Windows

# O simplemente inicia Publisher en otra terminal
```

#### Problema 0e: Publisher Inicia pero "No logs de endpoints"

**Síntomas**:
```
# Solo ves esto en la consola:
[UserWebService] java.net.BindException: Address already in use
```

**Solución**:
```bash
# 1. Matar proceso en puerto 8007 (ver arriba)
# 2. Intentar de nuevo
cd backend
mvn exec:java -Prun-publisher
```

---

### Problema 1: Puerto 8080 o 9001 Ya en Uso

**Síntomas:**
```
java.net.BindException: Address already in use
```

**Diagnóstico:**

**Linux/macOS:**
```bash
# Ver proceso usando puerto 8080
sudo lsof -i :8080

# Ver proceso usando puerto 9001
sudo lsof -i :9001

# Matar proceso (ejemplo)
kill -9 <PID>
```

**Windows:**
```cmd
REM Ver proceso usando puerto 8080
netstat -ano | findstr :8080

REM Ver proceso usando puerto 9001
netstat -ano | findstr :9001

REM Matar proceso
taskkill /PID <PID> /F
```

---

### Problema 2: "ClassNotFoundException: HsqldbServerListener"

**Síntomas:**
```
java.lang.ClassNotFoundException: turismouyapp.core.db.HsqldbServerListener
```

**Solución:**

1. Verificar que `HsqldbServerListener.jar` esté en `server/apache-tomcat-11.0.11/lib/`

2. Si no existe, compilar el backend y copiar:
   ```bash
   cd backend
   mvn clean install
   
   # Extraer clase del JAR y reempaquetar
   jar xf target/turismouy.Backend-1.0.0.jar turismouyapp/core/db/HsqldbServerListener.class
   jar cf ../server/apache-tomcat-11.0.11/lib/HsqldbServerListener.jar turismouyapp/
   ```

3. Reiniciar Tomcat

---

### Problema 3: Frontend No Encuentra Clases del Backend

**Síntomas:**
```
java.lang.NoClassDefFoundError: turismouyapp/core/factory/FactoryUyTourism
```

**Solución:**

1. Asegurarse de instalar backend en repositorio Maven local:
   ```bash
   cd backend
   mvn clean install
   ```

2. Limpiar y recompilar frontend:
   ```bash
   cd frontend
   mvn clean package
   ```

3. En Eclipse: Click derecho en proyecto → **Maven → Update Project**

---

### Problema 4: Error de Persistencia JPA

**Síntomas:**
```
Exception [EclipseLink-4002]: No Persistence provider for EntityManager
```

**Solución:**

1. Verificar que `persistence.xml` esté en `backend/src/main/resources/META-INF/`

2. Verificar que la URL de BD sea correcta:
   ```xml
   jdbc:hsqldb:hsql://localhost:9001/turismoUyDB
   ```

3. Confirmar que HSQLDB esté corriendo:
   ```bash
   telnet localhost 9001
   # o
   nc -zv localhost 9001
   ```

---

### Problema 5: Permisos en Linux/macOS

**Síntomas:**
```
Permission denied: ./bin/startup.sh
```

**Solución:**
```bash
cd server/apache-tomcat-11.0.11
chmod +x bin/*.sh
```

---

### Problema 6: Eclipse No Reconoce Tomcat 11

**Síntomas:**
No aparece "Apache Tomcat v11.0" en lista de servidores

**Solución:**

1. Instalar "Eclipse Web Developer Tools" desde Eclipse Marketplace
2. O usar servidor genérico:
   - New Server → **Basic → HTTP Server**
   - Configurar URLs manualmente

---

### Problema 7: Hot Reload No Funciona en Eclipse

**Solución:**

1. Doble click en servidor en vista Servers
2. **Publishing**:
   - ⚫ **Automatically publish when resources change**
3. Asegurarse de que `reloadable="true"` en `<Context>` del `server.xml`

---

### Problema 8: HSQLDB No Inicia en Eclipse - VM Arguments Faltantes

**Síntomas:**
```
NullPointerException en HsqldbServerListener
Base de datos no se crea en la ruta esperada
```

**Solución:**

1. En vista **Servers**, doble click en el servidor
2. Click en **Open launch configuration**
3. Pestaña **Arguments** → Sección **VM arguments**
4. Agregar:
   ```
   -Ddb.port=9001
   -Ddb.name=turismoUyDB
   -Ddb.path=<RUTA_ABSOLUTA>/server/apache-tomcat-11.0.11/data/turismoUyDB
   ```
5. Reemplazar `<RUTA_ABSOLUTA>` con la ruta completa a tu proyecto

**Verificar VM Arguments:**
```bash
# En los logs de Tomcat deberías ver:
[setenv.sh] db.port=9001
[setenv.sh] db.name=turismoUyDB
[setenv.sh] db.path=/ruta/completa/...
[DB] HSQLDB iniciado por Tomcat en puerto 9001
```

---

## 🛠️ Tecnologías Utilizadas

### Backend

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java SE** | 17 | Lenguaje base |
| **Jakarta Persistence API (JPA)** | 3.1.0 | Especificación ORM |
| **EclipseLink** | 4.0.4 | Implementación JPA |
| **HSQLDB** | 2.7.2 | Base de datos embebida |
| **Apache Tomcat (Catalina API)** | 11.0.11 | Listener lifecycle |
| **Swing** | Java 17 | GUI de escritorio |

### Frontend

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Jakarta Servlet** | 6.0.0 | API para servlets web |
| **Jakarta EE** | 10 | Plataforma empresarial |
| **Maven WAR Plugin** | 3.4.0 | Empaquetado WAR |

### Servidor

| Componente | Versión | Descripción |
|------------|---------|-------------|
| **Apache Tomcat** | 11.0.11 | Servidor de aplicaciones Jakarta EE |
| **HSQLDB Server** | 2.7.2 | Servidor de BD embebido |

### Herramientas de Desarrollo

- **Maven** 3.8+ - Gestión de dependencias y build
- **Git** - Control de versiones
- **Eclipse IDE** - IDE para Jakarta EE

---

## 📝 Notas Adicionales

### Persistencia de Datos

- Los datos se persisten en `server/apache-tomcat-11.0.11/data/turismoUyDB.*`
- Para datos de prueba en desarrollo, usar `backend/data/db/` (modo archivo)

### Portabilidad

- ✅ Rutas de BD configurables mediante variables de entorno
- ✅ Scripts `setenv` detectan automáticamente `CATALINA_BASE`
- ✅ Compatible con sistemas de archivos Windows y Unix

### Contribuciones

Este proyecto es parte de la Tarea 1 - 2025 del curso Taller de Tecnologías de Información.

Para contribuir:
1. Fork del repositorio
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request


## 🆘 Soporte

Si encuentras problemas no cubiertos en esta documentación:

1. Revisar logs en `server/apache-tomcat-11.0.11/logs/catalina.out`
2. Verificar consola de Eclipse (si aplica)
3. Consultar documentación oficial:
   - [Apache Tomcat 11](https://tomcat.apache.org/tomcat-11.0-doc/)
   - [HSQLDB](http://hsqldb.org/doc/2.0/guide/)
   - [EclipseLink](https://eclipse.dev/eclipselink/)

---

## 🎯 Comandos Maven Útiles

### Compilación y Empaquetado

```bash
# Compilar todo desde la raíz (recomendado)
mvn clean install

# Solo compilar Backend
cd backend && mvn clean install

# Solo compilar Frontend
cd frontend && mvn clean package

# Compilar sin ejecutar tests
mvn clean install -DskipTests

# Limpiar archivos generados
mvn clean
```

### Servidor (desde raíz del proyecto)

```bash
# Iniciar Tomcat (Linux/macOS)
mvn validate -Pstart-server

# Detener Tomcat (Linux/macOS)
mvn validate -Pstop-server
```

### Documentación

```bash
# Generar Javadoc del Backend
cd backend && mvn javadoc:javadoc

# Generar Javadoc del Frontend
cd frontend && mvn javadoc:javadoc
```

### Información y Debugging

```bash
# Ver árbol de dependencias
mvn dependency:tree

# Verificar estructura del proyecto
mvn validate

# Ejecutar con debug verbose
mvn clean install -X

# Ver propiedades del proyecto
mvn help:effective-pom
```

### ⚠️ Nota Importante: Profiles No Recomendados

El archivo `backend/pom.xml` contiene dos profiles que **NO son recomendados**:

```bash
# ❌ NO USAR - Comportamiento no probado
mvn exec:java -Prun-desktop    # Swing GUI (untested)
mvn exec:java -Prun-publisher  # Publisher (use Eclipse instead)
```

**Alternativas recomendadas:**
- **Publisher**: Usar Eclipse con Launch Configuration (ver [Ejecución del Proyecto](#-ejecución-del-proyecto))
- **Desktop GUI**: Ejecutar desde Eclipse → click derecho en `desktop/Main.java` → Run As → Java Application

### Equivalencias con npm (para desarrolladores Node.js)

| npm | Maven | Descripción |
|-----|-------|-------------|
| `npm install` | `mvn install` | Instala dependencias y compila |
| `npm run build` | `mvn clean package` | Compila el proyecto |
| `npm run clean` | `mvn clean` | Limpia archivos generados |
| `npm start` | `mvn validate -Pstart-server` | Inicia el servidor |
| `npm test` | `mvn test` | Ejecuta tests |
| `npm run docs` | `mvn javadoc:javadoc` | Genera documentación |

Para más detalles, consultar [COMANDOS.md](COMANDOS.md).

---

## 🚀 Flujo de Desarrollo Rápido (Referencia Rápida)

### Setup Inicial (Una sola vez)

```bash
# 1. Compilar backend
mvn clean install

# 2. Publicar WS (Terminal 1 - DEJAR ABIERTA)
cd backend
mvn exec:java -Prun-publisher
# Verás: [UserWebService] http://localhost:8007/ws/user

# 3. Compilar frontend (Terminal 2)
cd frontend
mvn clean package

# 4. Iniciar Tomcat (Terminal 3 - DEJAR ABIERTA)
cd server/apache-tomcat-11.0.11
./bin/startup.sh  # o startup.bat en Windows
```

### Desarrollo Iterativo

```bash
# Cambio en backend → Recompilar Backend
cd backend && mvn clean install

# Cambio en frontend → Recompilar Frontend
# (Publisher y Tomcat siguen corriendo)
cd frontend && mvn clean package

# Cambio en JSP → Solo refrescar navegador
# (Tomcat autodeploya cambios)
```

### Hot Reload

- **Archivos JSP/CSS/JS**: Refrescar navegador (Tomcat autodeploya)
- **Java**: Recompilar módulo correspondiente
- **Configuración**: Reiniciar Tomcat

---

**¡Gracias por usar TurismoUY!** 🌍✈️

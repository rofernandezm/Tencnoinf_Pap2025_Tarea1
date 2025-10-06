# TurismoUY - Sistema de Gestión Turística

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![Tomcat](https://img.shields.io/badge/Tomcat-11.0.11-yellow.svg)](https://tomcat.apache.org/)
[![HSQLDB](https://img.shields.io/badge/HSQLDB-2.7.2-green.svg)](http://hsqldb.org/)

Sistema de gestión turística desarrollado en Java con arquitectura en capas, utilizando Jakarta EE, JPA (EclipseLink), HSQLDB y Apache Tomcat. El proyecto consta de tres módulos principales: backend (lógica de negocio), frontend (interfaz web) y servidor (Tomcat configurado).

---

## 📋 Tabla de Contenidos

- [Arquitectura del Proyecto](#-arquitectura-del-proyecto)
- [Requisitos del Sistema](#-requisitos-del-sistema)
- [Instalación y Configuración](#-instalación-y-configuración)
  - [1. Instalación de Maven](#1-instalación-de-maven)
  - [2. Compilación del Proyecto](#2-compilación-del-proyecto)
- [Documentación Javadoc](#-documentación-javadoc)
  - [Backend](#documentación-del-backend)
  - [Frontend](#documentación-del-frontend)
- [Configuración del Servidor](#-configuración-del-servidor)
- [Configuración en Eclipse IDE](#-configuración-en-eclipse-ide)
- [Ejecución del Proyecto](#-ejecución-del-proyecto)
- [Estructura de Directorios](#-estructura-de-directorios)
- [Base de Datos](#-base-de-datos)
- [Solución de Problemas](#-solución-de-problemas)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)

---

## 🏗️ Arquitectura del Proyecto

El proyecto TurismoUY está organizado en tres módulos principales:

### **Backend** (`/backend`)
- **Propósito**: Contiene toda la lógica de negocio, controladores, entidades JPA y acceso a datos
- **Empaquetado**: JAR (`turismouy.Backend-1.0.0.jar`)
- **Características**:
  - Controladores de dominio (usuarios, actividades, salidas, inscripciones)
  - Entidades JPA con EclipseLink
  - DTOs (Data Transfer Objects)
  - Factory pattern para gestión de dependencias
  - Listener para iniciar servidor HSQLDB automáticamente
  - Interfaz Swing para aplicación de escritorio

### **Frontend** (`/frontend`)
- **Propósito**: Interfaz web con servlets Jakarta
- **Empaquetado**: WAR (`turismouy-ui.war`)
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

---

## 💻 Requisitos del Sistema

### Software Necesario

| Componente | Versión Mínima | Recomendada | Descripción |
|------------|----------------|-------------|-------------|
| **Java JDK** | 17 | 17 o 21 | OpenJDK o Oracle JDK |
| **Apache Maven** | 3.8.0 | 3.9.x | Gestor de dependencias |
| **Eclipse IDE** | 2023-06 | 2024-03+ | (Opcional) IDE con soporte Jakarta EE |
| **Git** | 2.x | Última | Control de versiones |

---

## 🚀 Instalación y Configuración

### 1. Instalación de Maven

#### Windows
1. Descargar Maven desde [maven.apache.org](https://maven.apache.org/download.cgi)
2. Extraer en `C:\Program Files\Apache\maven`
3. Configurar variables de entorno:
   ```cmd
   setx M2_HOME "C:\Program Files\Apache\maven"
   setx PATH "%M2_HOME%\bin;%PATH%"
   ```
4. Verificar:
   ```cmd
   mvn -version
   ```

#### Linux
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install maven -y

# Fedora
sudo dnf install maven -y

# Verificar
mvn -version
```

#### macOS
```bash
# Con Homebrew
brew install maven

# Verificar
mvn -version
```

---

### 2. Compilación del Proyecto

#### Compilación Completa (Recomendado)

**Linux/macOS:**
```bash
cd backend
mvn clean install

cd ../frontend
mvn clean package

cd ..
```

**Windows:**
```cmd
cd backend
mvn clean install

cd ..\frontend
mvn clean package

cd ..
```

#### ¿Qué hace cada comando?

| Comando | Módulo | Descripción |
|---------|--------|-------------|
| `mvn clean install` | Backend | Compila, ejecuta tests e instala el JAR en repositorio local Maven (~/.m2) |
| `mvn clean package` | Frontend | Compila y genera el WAR incluyendo dependencias del backend |

#### Verificación de Compilación

Después de compilar exitosamente, deberías ver:

```
backend/target/turismouy.Backend-1.0.0.jar  ← JAR del backend
frontend/target/turismouy-ui.war            ← WAR del frontend
```

---

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
├── presentation/                       # Paquete GUI Swing
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
| `presentation` | Interfaz Swing (GUI escritorio) | Main, CreateUser, CreateActivity |
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

### Requisitos Previos

- Eclipse IDE for Enterprise Java and Web Developers (2023-06 o superior)
- Plugins instalados:
  - Eclipse Web Tools Platform (WTP)
  - Maven Integration (m2e)
  - Server Adapters

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

### Paso 4: Configurar VM Arguments para Tomcat

**Esta configuración es CRÍTICA para que HSQLDB funcione correctamente.**

1. En la vista **Servers**, doble click en el servidor Tomcat
2. Click en **Open launch configuration**
3. Ir a la pestaña **Arguments**
4. En **VM arguments**, agregar las siguientes líneas:

```
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=/ruta/completa/al/proyecto/server/apache-tomcat-11.0.11/data/turismoUyDB
```

**Ejemplo completo Windows:**
```
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=C:\Users\usuario\workspace\turismouyApp\server\apache-tomcat-11.0.11\data\turismoUyDB
```

**Ejemplo completo Linux/macOS:**
```
-Ddb.port=9001
-Ddb.name=turismoUyDB
-Ddb.path=/home/rodrigo/blds/turismouyApp/server/apache-tomcat-11.0.11/data/turismoUyDB
```

5. Click **OK** para guardar
6. Click **File → Save** o `Ctrl+S` en la configuración del servidor

⚠️ **Importante**: 
- Reemplazar `/ruta/completa/al/proyecto` con la ruta absoluta real de tu workspace
- Estas variables son leídas por `setenv.sh/bat` y el `HsqldbServerListener`
- Sin estas variables, HSQLDB usará valores por defecto que pueden no coincidir

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

## ▶️ Ejecución del Proyecto

### Opción 1: Desde Eclipse

1. En la vista **Servers**, seleccionar el servidor
2. Click en el botón **Start** (▶️) o click derecho → **Start**
3. Esperar a que se inicie (verás en la consola):
   ```
   [DB] HSQLDB iniciado por Tomcat en puerto 9001
   Server startup in [xxxx] milliseconds
   ```
4. Abrir navegador en: `http://localhost:8080/turismouy.UI/`

### Opción 2: Desde Terminal/Línea de Comandos

#### Linux/macOS

```bash
# Navegar al directorio del servidor
cd server/apache-tomcat-11.0.11

# Iniciar Tomcat
./bin/startup.sh

# Ver logs en tiempo real (opcional)
tail -f logs/catalina.out

# Detener servidor
./bin/shutdown.sh
```

#### Windows

```cmd
REM Navegar al directorio del servidor
cd server\apache-tomcat-11.0.11

REM Iniciar Tomcat
bin\startup.bat

REM Ver logs (en otra terminal)
type logs\catalina.YYYY-MM-DD.log

REM Detener servidor
bin\shutdown.bat
```

### Opción 3: Despliegue Manual del WAR

Si prefieres NO usar Eclipse:

1. Copiar el WAR generado:
   ```bash
   cp frontend/target/turismouy-ui.war server/apache-tomcat-11.0.11/webapps/
   ```

2. Iniciar Tomcat (método anterior)

3. Tomcat desplegará automáticamente el WAR en `webapps/turismouy-ui/`

4. Acceder a: `http://localhost:8080/turismouy-ui/`

### Aplicación de Escritorio (Swing)

Para ejecutar la interfaz gráfica de escritorio:

```bash
cd backend
java -jar target/turismouy.Backend-1.0.0.jar
```

O desde Eclipse:
1. Navegar a `backend/src/main/java/presentation/Main.java`
2. Click derecho → **Run As → Java Application**

---

## 📁 Estructura de Directorios

### Backend (`/backend`)

```
backend/
├── pom.xml                          # Maven: dependencias EclipseLink, HSQLDB, Jakarta
├── src/main/java/
│   ├── presentation/                # Interfaz Swing (aplicación escritorio)
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
    ├── turismouy-ui.war            # WAR desplegable
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
- **Eclipse IDE** - IDE para Jakarta EE (opcional)

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

**¡Gracias por usar TurismoUY!** 🌍✈️

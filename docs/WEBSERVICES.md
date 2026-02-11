🌐 Language: **Español** | [English](WEBSERVICES_EN.md)

# Web Services SOAP (TurismoUY)

Este documento describe la capa de **Web Services SOAP** del proyecto **TurismoUY**, incluyendo el flujo de ejecución requerido, los endpoints disponibles, el proceso de generación de stubs del cliente web y el rol que cumple esta integración dentro de la arquitectura general del sistema.

---

## Tabla de Contenidos

- [Web Services SOAP (TurismoUY)](#web-services-soap-turismouy)
  - [Tabla de Contenidos](#tabla-de-contenidos)
  - [Contexto](#contexto)
  - [Rol de SOAP en el Proyecto](#rol-de-soap-en-el-proyecto)
  - [Componentes Involucrados](#componentes-involucrados)
  - [Puertos Utilizados](#puertos-utilizados)
  - [Flujo Crítico de Ejecución](#flujo-crítico-de-ejecución)
    - [Orden requerido](#orden-requerido)
    - [Diagrama de ejecución](#diagrama-de-ejecución)
  - [Servicios SOAP Disponibles](#servicios-soap-disponibles)
  - [UserService](#userservice)
    - [WSDL](#wsdl)
    - [Responsabilidad](#responsabilidad)
  - [ActivityService](#activityservice)
    - [WSDL](#wsdl-1)
    - [Responsabilidad](#responsabilidad-1)
  - [OutingAndInscriptionService](#outingandinscriptionservice)
    - [WSDL](#wsdl-2)
    - [Responsabilidad](#responsabilidad-2)
  - [Publicación de los Servicios (Publisher)](#publicación-de-los-servicios-publisher)
    - [Ejecución recomendada](#ejecución-recomendada)
  - [Generación de Stubs (wsimport)](#generación-de-stubs-wsimport)
    - [¿Qué hace wsimport?](#qué-hace-wsimport)
    - [Generación automática mediante Maven](#generación-automática-mediante-maven)
    - [Ubicación de los stubs generados](#ubicación-de-los-stubs-generados)
  - [Cómo Consume el Frontend Web los Servicios](#cómo-consume-el-frontend-web-los-servicios)
    - [Flujo conceptual típico](#flujo-conceptual-típico)
  - [Notas Técnicas Relevantes](#notas-técnicas-relevantes)
    - [1. Dependencia del frontend al Publisher en tiempo de build](#1-dependencia-del-frontend-al-publisher-en-tiempo-de-build)
    - [2. Stubs como contrato estable](#2-stubs-como-contrato-estable)
    - [3. Separación real de capas](#3-separación-real-de-capas)
    - [4. Multi-interfaz sobre un backend común](#4-multi-interfaz-sobre-un-backend-común)
  - [Referencias](#referencias)

---

## Contexto

TurismoUY implementa una arquitectura distribuida donde el **Frontend Web (Servlets/JSP)** no accede directamente a las clases del backend, sino que se comunica con él a través de **Web Services SOAP**.

Esto permite desacoplar la lógica de negocio del cliente web y habilita que el sistema sea consumido por distintas interfaces.

---

## Rol de SOAP en el Proyecto

El uso de SOAP cumple un rol central:

- Expone funcionalidades del backend como contratos formales (WSDL).
- Permite comunicación remota mediante HTTP.
- Obliga a mantener una separación clara entre capas.
- Habilita que el frontend web funcione como cliente independiente del backend.

Este enfoque fue el resultado final de una evolución progresiva del proyecto, logrando que el backend funcione como un componente reutilizable.

---

## Componentes Involucrados

En el flujo SOAP intervienen principalmente:

- **Backend**: publica los servicios SOAP.
- **Frontend Web**: genera stubs SOAP y realiza llamadas remotas.
- **Servidor Tomcat**: ejecuta el frontend web y aloja la base de datos.
- **HSQLDB**: base de datos utilizada por el backend a través de JPA/EclipseLink.

> Nota: La aplicación Desktop Swing forma parte del módulo backend y opera directamente sobre la lógica del sistema, no mediante SOAP.

---

## Puertos Utilizados

| Puerto | Componente | Rol |
|--------|------------|-----|
| **8007** | Backend SOAP Publisher | Publicación de WSDL y endpoints SOAP |
| **8080** | Tomcat | Servidor HTTP para el frontend web |
| **9001** | HSQLDB Server | Base de datos embebida en Tomcat |

Antes de ejecutar el sistema, estos puertos deben estar disponibles.

---

## Flujo Crítico de Ejecución

El flujo de ejecución es **estricto**, especialmente por la generación de stubs SOAP.

### Orden requerido

1. Compilar backend
2. Publicar servicios SOAP (dejar corriendo)
3. Compilar frontend (descarga WSDLs y genera stubs)
4. Iniciar Tomcat (ejecuta frontend web + base de datos)

### Diagrama de ejecución

```
┌───────────────────────────────────────────────┐
│ 1) Compilar Backend                           │
│    cd backend && mvn clean install            │
└───────────────────────────────────────────────┘
                    │
                    ▼
┌───────────────────────────────────────────────┐
│ 2) Publicar Web Services SOAP (Puerto 8007)   │
│    cd backend && mvn exec:java -Prun-publisher│
│    (mantener ejecutando)                      │
└───────────────────────────────────────────────┘
                    │
                    ▼
┌───────────────────────────────────────────────┐
│ 3) Compilar Frontend Web                      │
│    cd frontend && mvn clean package           │
│    (wsimport descarga WSDL y genera stubs)    │
└───────────────────────────────────────────────┘
                    │
                    ▼
┌───────────────────────────────────────────────┐
│ 4) Iniciar Tomcat (Puerto 8080 + DB en 9001)  │
│    Ejecutar desde Eclipse (recomendado)       │
└───────────────────────────────────────────────┘
```

---

## Servicios SOAP Disponibles

El backend expone tres servicios SOAP principales, cada uno asociado a una parte del dominio del sistema.

Todos los servicios comparten el mismo namespace:

- **Namespace**: `http://ws.turismouyapp/schema`

Cuando el Publisher está activo, los WSDL quedan disponibles en:

```
http://localhost:8007/ws/<service>?wsdl
```

---

## UserService

### WSDL

```
http://localhost:8007/ws/user?wsdl
```

### Responsabilidad

Gestión de usuarios y perfiles del sistema.

Incluye operaciones como:

- Registro de turistas y proveedores
- Consulta de usuarios
- Modificación de datos
- Gestión de imágenes de perfil

---

## ActivityService

### WSDL

```
http://localhost:8007/ws/activity?wsdl
```

### Responsabilidad

Gestión de actividades turísticas.

Incluye operaciones como:

- Alta de actividades
- Modificación de actividades
- Consultas por proveedor o estado
- Listados de actividades y salidas asociadas

---

## OutingAndInscriptionService

### WSDL

```
http://localhost:8007/ws/outingAndInscription?wsdl
```

### Responsabilidad

Gestión de salidas turísticas e inscripciones.

Incluye operaciones como:

- Alta de salidas
- Consulta de salidas
- Inscripción de turistas a salidas
- Listado de inscripciones

---

## Publicación de los Servicios (Publisher)

El backend incluye un componente Publisher que publica los tres servicios SOAP en el puerto configurado.

### Ejecución recomendada

En una terminal (o Run Configuration de Eclipse):

```bash
cd backend
mvn exec:java -Prun-publisher
```

Al ejecutarse correctamente, se espera ver en consola algo similar a:

```
[UserWebService] http://localhost:8007/ws/user?wsdl
[ActivityWebService] http://localhost:8007/ws/activity?wsdl
[OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription?wsdl
```

Este proceso debe permanecer ejecutándose mientras el frontend se compila y durante la ejecución del sistema.

---

## Generación de Stubs (wsimport)

El frontend web no incluye manualmente las clases de integración SOAP, sino que las genera automáticamente utilizando `wsimport`.

### ¿Qué hace wsimport?

- Descarga los WSDL desde el Publisher (puerto 8007)
- Genera stubs SOAP en Java (cliente)
- Genera DTOs basados en los contratos XML

### Generación automática mediante Maven

Con el Publisher activo:

```bash
cd frontend
mvn clean package
```

Esto generará automáticamente las clases necesarias.

### Ubicación de los stubs generados

Los stubs se generan en:

```
frontend/target/generated-sources/wsimport/
```

Estas clases no deben editarse manualmente, ya que se regeneran en cada compilación.

---

## Cómo Consume el Frontend Web los Servicios

El frontend web utiliza Servlets Jakarta EE como capa de presentación.

Cada servlet actúa como un controlador HTTP que:

1. recibe la petición del usuario
2. invoca el stub SOAP correspondiente
3. obtiene DTOs como respuesta
4. renderiza la vista JSP

### Flujo conceptual típico

```
Browser
   |
   ▼
Servlet (Frontend Web)
   |
   ▼
Stub SOAP generado (wsimport)
   |
   ▼
Backend Web Service Implementation
   |
   ▼
Controller (lógica de negocio)
   |
   ▼
Persistencia (JPA / EclipseLink)
   |
   ▼
HSQLDB
```

---

## Notas Técnicas Relevantes

### 1. Dependencia del frontend al Publisher en tiempo de build

El frontend no puede compilar correctamente si el Publisher no está corriendo, ya que wsimport necesita acceder a los WSDL.

Esto convierte el build del frontend en un proceso dependiente del backend en tiempo de compilación.

---

### 2. Stubs como contrato estable

El uso de SOAP fuerza una estructura contractual clara.

Si se modifica un método del servicio (firma, parámetros o DTOs), el frontend debe recompilarse para regenerar los stubs.

---

### 3. Separación real de capas

La implementación con SOAP garantiza que el frontend web no accede directamente a la lógica de negocio ni a la base de datos.

Toda interacción pasa por los contratos definidos en los servicios.

---

### 4. Multi-interfaz sobre un backend común

La arquitectura final permite que el backend sea reutilizado por:

- Frontend Web (vía SOAP)
- Desktop Swing (acceso directo al backend, dentro del mismo módulo)

Esto habilita flujos de interacción donde el usuario opera desde web y ciertas tareas administrativas se gestionan desde escritorio.

---

## Referencias

- [README.md](../README.md)
- [SETUP.md](SETUP.md)
- [ARCHITECTURE.md](ARCHITECTURE.md)

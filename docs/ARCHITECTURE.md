🌐 Language: **Español** | [English](ARCHITECTURE_EN.md)

# Arquitectura - TurismoUY (ES)

Este documento describe la arquitectura general del proyecto **TurismoUY**, incluyendo sus componentes principales, la forma en que se comunican entre sí y las decisiones técnicas relevantes que explican su estructura.

> Nota: Este proyecto fue desarrollado con fines académicos, pero se diseñó siguiendo conceptos reales de arquitectura de software y separación de responsabilidades, simulando un sistema distribuido con múltiples aplicaciones que interactúan entre sí.

---

## Tabla de Contenidos

- [Arquitectura - TurismoUY (ES)](#arquitectura---turismouy-es)
  - [Tabla de Contenidos](#tabla-de-contenidos)
  - [Visión General](#visión-general)
  - [Componentes del Sistema](#componentes-del-sistema)
    - [1. Backend (Web Services SOAP)](#1-backend-web-services-soap)
    - [2. Frontend Web (WAR en Tomcat)](#2-frontend-web-war-en-tomcat)
    - [3. Desktop Client (Swing)](#3-desktop-client-swing)
    - [4. Base de Datos (HSQLDB)](#4-base-de-datos-hsqldb)
    - [5. Servidor Portable (Tomcat + Librerías)](#5-servidor-portable-tomcat--librerías)
  - [Diagrama de Arquitectura (Alto Nivel)](#diagrama-de-arquitectura-alto-nivel)
  - [Comunicación entre Componentes](#comunicación-entre-componentes)
  - [Arquitectura Interna del Backend](#arquitectura-interna-del-backend)
    - [Capas principales](#capas-principales)
    - [Flujo típico de una operación](#flujo-típico-de-una-operación)
      - [Desde la interfaz web (frontend)](#desde-la-interfaz-web-frontend)
      - [Desde la interfaz desktop (Swing)](#desde-la-interfaz-desktop-swing)
  - [Arquitectura Interna del Frontend Web](#arquitectura-interna-del-frontend-web)
    - [Responsabilidad principal](#responsabilidad-principal)
    - [Componentes típicos](#componentes-típicos)
    - [Flujo típico de request](#flujo-típico-de-request)
  - [Aplicación Desktop (Swing)](#aplicación-desktop-swing)
    - [Propósito](#propósito)
  - [Base de Datos y Persistencia](#base-de-datos-y-persistencia)
    - [Configuración de base de datos](#configuración-de-base-de-datos)
    - [Gestión de ciclo de vida](#gestión-de-ciclo-de-vida)
  - [Patrones de Diseño Aplicados](#patrones-de-diseño-aplicados)
    - [Factory + Singleton](#factory--singleton)
    - [DTO (Data Transfer Object)](#dto-data-transfer-object)
    - [Layered Architecture (Arquitectura en Capas)](#layered-architecture-arquitectura-en-capas)
    - [Listener Pattern](#listener-pattern)
  - [Decisiones Técnicas Destacadas](#decisiones-técnicas-destacadas)
    - [1. Separación real de procesos](#1-separación-real-de-procesos)
    - [2. Uso de SOAP para integración](#2-uso-de-soap-para-integración)
    - [3. Portabilidad del entorno (Monorepo + Tomcat incluido)](#3-portabilidad-del-entorno-monorepo--tomcat-incluido)
  - [Documentación Relacionada](#documentación-relacionada)

---

## Visión General

**TurismoUY** está diseñado como un sistema turístico con arquitectura distribuida y modular, donde múltiples aplicaciones consumen un mismo backend central.

El proyecto se organiza en **tres componentes principales** (con una interfaz desktop incluida dentro del backend):

- **Backend (módulo `backend/`)**: contiene la lógica de negocio y la persistencia (JPA/EclipseLink) y expone funcionalidades del sistema mediante **Web Services SOAP** (Publisher en `:8007`).  
  - Incluye además una **aplicación desktop Swing** (administrativa) que utiliza el mismo core del backend de forma **directa** (no consume SOAP).
- **Frontend Web (módulo `frontend/`)**: aplicación web (Servlets/JSP) desplegable en Tomcat que consume el backend mediante SOAP (stubs generados con `wsimport`).
- **Servidor Tomcat + Base de Datos (módulo `server/`)**: Tomcat portable preconfigurado y un servidor HSQLDB embebido (iniciado por un listener) que centraliza la base de datos para los distintos clientes.


Este diseño permite representar un escenario realista donde:

- usuarios finales interactúan mediante Web,
- administradores utilizan una aplicación de escritorio,
- ambos comparten reglas de negocio y datos persistidos.

---

## Componentes del Sistema

El sistema se compone de los siguientes elementos principales:

### 1. Backend (Web Services SOAP)

Aplicación Java ejecutada como proceso independiente que publica servicios SOAP utilizando **JAX-WS (Metro)**.

- Expone endpoints en `localhost:8007`
- Contiene la lógica de negocio, persistencia y validaciones
- Implementa controladores y handlers para acceso a datos

### 2. Frontend Web (WAR en Tomcat)

Aplicación web desplegada en Tomcat, implementada con:

- Jakarta Servlets
- JSP para vistas
- Bootstrap para estilos
- stubs SOAP generados automáticamente mediante `wsimport`

Se ejecuta en `localhost:8080`.

### 3. Desktop Client (Swing)

Aplicación Java Swing que actúa como cliente de administración.

- Permite ejecutar acciones administrativas y de gestión
- Comparte lógica de negocio a través del backend central
- Se integra naturalmente en el flujo del sistema (por ejemplo, aprobaciones)

### 4. Base de Datos (HSQLDB)

Base de datos relacional embebida configurada en modo servidor.

- Se ejecuta en `localhost:9001`
- Permite conexiones concurrentes desde backend y aplicaciones clientes
- Puede iniciarse automáticamente al levantar Tomcat

### 5. Servidor Portable (Tomcat + Librerías)

El repositorio incluye una instalación completa de Tomcat configurada para ser ejecutada directamente desde el proyecto, permitiendo:

- configuración rápida y reproducible,
- uso consistente en Windows y Linux,
- almacenamiento persistente de la base de datos en un directorio común.

## Diagrama de Arquitectura (Alto Nivel)

La arquitectura puede visualizarse de la siguiente manera:

```
┌──────────────────────────────────────────────────────────┐
│                    FRONTEND WEB (WAR)                    │
│          Servlets/JSP + Bootstrap (Tomcat :8080)         │
│                                                          │ 
│      - Cliente SOAP (stubs generados por wsimport)       │
└──────────────────────────────┬───────────────────────────┘
                               │ SOAP (HTTP) :8007
                               ▼
┌──────────────────────────────────────────────────────────┐
│              BACKEND (Web Services SOAP :8007)           │
│                                                          │
│  Controllers + Handlers + Entities JPA + DTOs JAXB       │
│                                                          │
│  Expone: UserService, ActivityService, OutingService     │
└──────────────────────────────┬───────────────────────────┘
                               │ JPA / JDBC
                               ▼
┌──────────────────────────────────────────────────────────┐
│           HSQLDB SERVER (embebido en Tomcat :9001)       │
│  Persistencia relacional en modo servidor                │
└──────────────────────────────────────────────────────────┘


┌──────────────────────────────────────────────────────────┐
│                DESKTOP (Swing, en backend/)              │
│  - Aplicación administrativa                             │
│  - Usa el core del backend de forma directa (sin SOAP)   │
└──────────────────────────────┬───────────────────────────┘
                               │ JPA / JDBC
                               ▼
                    (misma HSQLDB en :9001)
```

Notas:

- La **comunicación SOAP** aplica al **frontend web** (desacople entre interfaz web y backend).
- La **aplicación Swing** vive dentro del módulo backend y reutiliza el core directamente, compartiendo la misma base de datos en modo servidor.

---

## Comunicación entre Componentes

La comunicación entre componentes está pensada para **evitar duplicación de lógica** y permitir **múltiples interfaces** sobre el mismo dominio.

- El **backend** publica WSDLs en `http://localhost:8007/ws/*?wsdl`.
- El **frontend web** consume el backend mediante SOAP, generando stubs con `wsimport` durante el build.
- La **aplicación desktop (Swing)** no consume SOAP: utiliza el core del backend directamente y se conecta a la misma base de datos HSQLDB (modo servidor).

En otras palabras: SOAP es el contrato de integración para la interfaz web; Swing y los Web Services comparten el mismo core y persisten en la misma base.

---

## Arquitectura Interna del Backend

El backend se organiza siguiendo una arquitectura en capas:

### Capas principales

1. **Web Services Layer**
   - Interfaces y clases anotadas con `@WebService`
   - Publicación mediante `Endpoint.publish()`
   - Serialización XML mediante JAXB

2. **Controllers (Business Logic Layer)**
   - Implementan reglas de negocio
   - Orquestan validaciones y operaciones
   - Evitan lógica directa en los Web Services

3. **Handlers / Persistence Layer**
   - Gestionan operaciones JPA
   - Encapsulan interacción con EntityManager
   - Abstracción de consultas y transacciones

4. **Entities (JPA)**
   - Modelo relacional persistente
   - Relaciones `@OneToMany`, `@ManyToOne`, herencia, etc.

5. **DTOs**
   - Objetos de transferencia entre backend y clientes SOAP
   - Usan anotaciones JAXB (`@XmlRootElement`, `@XmlType`, etc.)

### Flujo típico de una operación

#### Desde la interfaz web (frontend)

```
Servlet / JSP (Tomcat)
        |
        ▼
SOAP Stub (wsimport)
        |
        ▼
WebService (implementación en backend :8007)
        |
        ▼
Controller (lógica de negocio)
        |
        ▼
Handler / Persistencia (JPA)
        |
        ▼
HSQLDB (server :9001)
```

#### Desde la interfaz desktop (Swing)

```
Swing UI (ejecución local)
        |
        ▼
Controller (lógica de negocio)
        |
        ▼
Handler / Persistencia (JPA)
        |
        ▼
HSQLDB (server :9001)
```



## Arquitectura Interna del Frontend Web

El frontend web está construido con Jakarta Servlets + JSP.

### Responsabilidad principal

- manejar interacción con el usuario
- renderizar vistas
- gestionar sesiones HTTP
- invocar servicios SOAP del backend.

### Componentes típicos

- **Servlets**: reciben requests HTTP, validan parámetros, invocan backend SOAP.
- **JSP**: renderizan la interfaz.
- **Assets estáticos**: CSS, JS e imágenes.
- **Stubs SOAP**: generados por Maven en tiempo de compilación.

### Flujo típico de request

```
Browser
   |
   ▼
Servlet (HTTP request)
   |
   ▼
SOAP Client Stub
   |
   ▼
Backend SOAP
   |
   ▼
Respuesta DTO / datos
   |
   ▼
Servlet procesa resultado
   |
   ▼
JSP renderiza respuesta HTML
```

---

## Aplicación Desktop (Swing)

El sistema incluye una aplicación de escritorio implementada en Swing.

### Propósito

- simular un rol administrativo del sistema
- permitir operaciones que no están pensadas para usuarios finales
- participar en flujos completos donde una acción iniciada en web puede requerir validación o aprobación desde desktop.

Este enfoque permite representar un caso real de coexistencia entre:

- sistema web para usuarios finales
- sistema administrativo interno para personal autorizado.

---

## Base de Datos y Persistencia

La persistencia se implementa mediante **JPA (EclipseLink)** utilizando HSQLDB como motor relacional.

### Configuración de base de datos

- Puerto: `9001`
- Modo servidor: `jdbc:hsqldb:hsql://localhost:9001/turismoUyDB`
- Tablas generadas automáticamente por EclipseLink (`create-or-extend-tables`)

### Gestión de ciclo de vida

La base de datos puede iniciarse automáticamente mediante un Listener asociado al ciclo de vida de Tomcat (`LifecycleListener`).

Esto permite que Tomcat actúe como punto central de inicialización del sistema.

---

## Patrones de Diseño Aplicados

El proyecto aplica varios patrones de diseño típicos en sistemas empresariales:

### Factory + Singleton

- `FactoryUyTourism` centraliza instanciación y acceso a controladores.
- Se utiliza como punto único de acceso a dependencias.

### DTO (Data Transfer Object)

- Uso intensivo de objetos DTO para transportar datos hacia los clientes SOAP.
- Facilita desacoplamiento entre entidades JPA y exposición pública.

### Layered Architecture (Arquitectura en Capas)

Separación clara de responsabilidades:

- presentación
- lógica
- persistencia

### Listener Pattern

- Uso de listeners para inicialización y gestión de recursos (por ejemplo, base de datos).

---

## Decisiones Técnicas Destacadas

### 1. Separación real de procesos

El backend SOAP se ejecuta como proceso separado del servidor web (Tomcat), lo cual permite:

- independencia de despliegue
- separación conceptual backend/frontend
- simulación realista de arquitectura distribuida

### 2. Uso de SOAP para integración

SOAP fue elegido por su capacidad de definir contratos formales (WSDL) y permitir generación automática de clientes.

### 3. Portabilidad del entorno (Monorepo + Tomcat incluido)

Una de las decisiones destacadas del proyecto es la inclusión del servidor Tomcat configurado dentro del repositorio.

Esto permite:

- reducir fricción de setup
- garantizar versión de servidor consistente
- reutilizar la misma base de datos en distintos entornos
- evitar inconsistencias entre Windows y Linux
- facilitar ejecución rápida en equipos distintos

Esta solución no fue un requisito del proyecto, sino una decisión orientada a mejorar la reproducibilidad y portabilidad del sistema.

---

## Documentación Relacionada

Para más detalles técnicos se recomienda consultar:

- [Guía de instalación y ejecución](SETUP.md)
- [Web Services SOAP](WEBSERVICES.md)
- [Evolución del proyecto](PROJECT_EVOLUTION.md)
- [Funcionalidades](FEATURES.md)
- [Stack Tecnológico](TECH_STACK.md)

---

**Fin del documento.**
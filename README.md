🌐 Idioma: **Español** | [Inglés](README_EN.md)

# TurismoUY - Sistema de Gestión Turística

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![Tomcat](https://img.shields.io/badge/Tomcat-11.0.11-yellow.svg)](https://tomcat.apache.org/)
[![HSQLDB](https://img.shields.io/badge/HSQLDB-2.7.2-green.svg)](http://hsqldb.org/)

Sistema de gestión turística desarrollado en Java con arquitectura en capas, utilizando Jakarta EE, JPA (EclipseLink), HSQLDB y Apache Tomcat.

El proyecto implementa un entorno **multi-cliente** con comunicación distribuida basada en **Web Services SOAP**, permitiendo interacción tanto desde una aplicación web como desde una aplicación desktop.

---

## Tabla de Contenidos

- [TurismoUY - Sistema de Gestión Turística](#turismouy---sistema-de-gestión-turística)
  - [Tabla de Contenidos](#tabla-de-contenidos)
  - [Descripción General](#descripción-general)
  - [Componentes del Sistema](#componentes-del-sistema)
  - [Portabilidad y Enfoque Cross-Platform](#portabilidad-y-enfoque-cross-platform)
  - [Arquitectura General](#arquitectura-general)
  - [Quick Start](#quick-start)
    - [1) Compilar Backend](#1-compilar-backend)
    - [2) Publicar Web Services SOAP (dejar corriendo)](#2-publicar-web-services-soap-dejar-corriendo)
    - [3) Compilar Frontend (genera stubs SOAP)](#3-compilar-frontend-genera-stubs-soap)
    - [4) Iniciar Tomcat desde Eclipse](#4-iniciar-tomcat-desde-eclipse)
  - [Documentación Complementaria](#documentación-complementaria)
  - [Tecnologías Utilizadas](#tecnologías-utilizadas)

---

## Descripción General

TurismoUY es un sistema de gestión turística que permite administrar usuarios, actividades turísticas, salidas (outings) e inscripciones.  
Incluye soporte para perfiles de usuario diferenciados (turistas, proveedores y administradores), además de gestión de imágenes y consultas centralizadas.

El objetivo del proyecto es aplicar conceptos de ingeniería de software e integración de sistemas, utilizando un stack Java empresarial clásico (Jakarta EE, SOAP, JPA) y una arquitectura distribuida.

---

## Componentes del Sistema

El sistema está compuesto por tres módulos principales:

- **Backend (Servidor Central)**  
  Contiene la lógica de negocio, entidades JPA y controladores. Expone Web Services SOAP para ser consumidos por clientes externos.

- **Frontend Web (Aplicación Web en Tomcat)**  
  Interfaz web basada en servlets y JSP. Consume el backend a través de stubs SOAP generados automáticamente mediante `wsimport`.

- **Desktop Client (Aplicación Swing)**  
  Cliente de escritorio desarrollado en Swing. Permite ejecutar flujos administrativos y operar sobre la misma lógica central del sistema.

Un aspecto importante es que el sistema está diseñado para soportar flujos multi-plataforma, por ejemplo:

- un proveedor inicia una solicitud desde la interfaz web
- un administrador gestiona o valida información desde el cliente desktop
- el resultado impacta directamente en la aplicación web

Esto permite una experiencia integrada entre distintos tipos de usuario.

---

## Portabilidad y Enfoque Cross-Platform

Una parte importante del diseño del repositorio fue garantizar portabilidad y reproducibilidad.

Además de cumplir con los requisitos académicos (uso de Eclipse), se incorporó un enfoque tipo **monorepo** incluyendo una distribución portable de Apache Tomcat y Maven dentro del proyecto, con el objetivo de facilitar el setup en distintos sistemas operativos (Windows/Linux) y reducir problemas de configuración.

Esta decisión permite que distintos entornos trabajen con el mismo stack y estructura, asegurando consistencia durante la ejecución del sistema.

---

## Arquitectura General

El sistema utiliza una arquitectura distribuida cliente-servidor con comunicación SOAP:

- Backend publica Web Services en el puerto **8007**
- Frontend Web consume esos servicios desde Tomcat en el puerto **8080**
- La base de datos HSQLDB corre en modo servidor en el puerto **9001**

```
┌─────────────────────────────────────────────────────────┐
│ FRONTEND WEB (Tomcat:8080)                              │
│ - Servlets + JSP + Bootstrap                            │
│ - SOAP Stubs generados por wsimport                     │
└──────────────────┬──────────────────────────────────────┘
                   │ SOAP (HTTP)
                   ▼ localhost:8007
┌─────────────────────────────────────────────────────────┐
│ BACKEND / WEB SERVICES (Publisher:8007)                 │
│ - Controllers / Handlers / DTOs                         │
│ - Persistencia JPA (EclipseLink)                        │
└──────────────────┬──────────────────────────────────────┘
                   │ JDBC
                   ▼ localhost:9001
┌─────────────────────────────────────────────────────────┐
│ HSQLDB Server (Base de Datos)                           │
│ - BD relacional embebida en modo servidor               │
└─────────────────────────────────────────────────────────┘
```

---

## Quick Start

> Nota: el curso requería ejecución mediante **Eclipse IDE**, por lo cual el proyecto está orientado principalmente a ese flujo.

### 1) Compilar Backend
```bash
cd backend
mvn clean install
```
### 2) Publicar Web Services SOAP (dejar corriendo)
```bash
cd backend
mvn exec:java -Prun-publisher
```
Esto publica los endpoints SOAP en:
- http://localhost:8007/ws/user?wsdl
- http://localhost:8007/ws/activity?wsdl
- http://localhost:8007/ws/outingAndInscription?wsdl

### 3) Compilar Frontend (genera stubs SOAP)
```bash
cd frontend
mvn clean package
```
### 4) Iniciar Tomcat desde Eclipse 

http://localhost:8080/turismouy.UI/

## Documentación Complementaria

Este README actúa como introducción general al proyecto.
La documentación detallada se encuentra en el directorio /docs.

- [Guía de instalación y ejecución](docs/SETUP.md)
- [Arquitectura](docs/ARCHITECTURE.md)
- [Web Services SOAP](docs/WEBSERVICES.md)
- [Evolución del proyecto](docs/PROJECT_EVOLUTION.md)
- [Funcionalidades](docs/FEATURES.md)
- [Stack Tecnológico](docs/TECH_STACK.md)

[Ver documentación completa](docs/)

## Tecnologías Utilizadas

- Java 17
- Maven
- Apache Tomcat 11
- Jakarta EE 10
- Jakarta Servlets 6.0
- JPA (EclipseLink)
- HSQLDB
- Web Services SOAP (JAX-WS Metro)
- JAXB (XML Binding)
- Swing (Desktop Client)
- JSP + Bootstrap (Web UI)
- Git

---

Proyecto académico desarrollado como parte de la carrera
[Tecnólogo en Informática](https://eduterciaria.utu.edu.uy/2024/06/18/tecnologo-en-informatica/)
(UDELAR / UTEC / DGETP-UTU).

🌐 Language: [Spanish](ARCHITECTURE.md) | **English**

# TurismoUY - Architecture Overview

This document provides a technical overview of the architecture of **TurismoUY**, focusing on how the system is structured, how its components communicate, and what design decisions were applied to keep the solution modular and portable.

The goal is to offer a clear view of the system from a software engineering perspective, rather than acting as a setup guide.

---

## Table of Contents

- [TurismoUY - Architecture Overview](#turismouy---architecture-overview)
  - [Table of Contents](#table-of-contents)
  - [High-Level Architecture](#high-level-architecture)
  - [Core Components](#core-components)
    - [Backend](#backend)
    - [Web Frontend](#web-frontend)
    - [Desktop Application (Swing)](#desktop-application-swing)
    - [Portable Server (Tomcat + HSQLDB)](#portable-server-tomcat--hsqldb)
  - [Communication Between Components](#communication-between-components)
  - [Layered Design](#layered-design)
  - [Data Persistence](#data-persistence)
  - [Portability as a Design Goal](#portability-as-a-design-goal)
  - [Summary](#summary)

---

## High-Level Architecture

TurismoUY is designed as a **distributed client-server system** with multiple interfaces connected to a shared business core.

It includes:

- A **central backend** that contains business logic and persistence.
- A **web frontend** (Servlets/JSP) that consumes the backend through SOAP.
- A **desktop Swing application** that directly uses the backend controllers for administrative tasks.
- A **portable application server package** (Tomcat + embedded HSQLDB server).

This structure enables the same backend domain logic to support both web-based and desktop workflows.

---

## Core Components

### Backend

The backend is the central part of the system.

It contains:

- Domain entities (JPA)
- Business controllers
- Persistence handlers
- DTOs
- Custom business exceptions
- Shared logic for both web and desktop clients
- SOAP Web Services endpoints for remote communication with the web frontend

The backend is packaged as a JAR and can be executed in multiple modes depending on the workflow (publisher mode, desktop mode, etc.).

---

### Web Frontend

The web frontend is a Jakarta EE application deployed as a WAR inside Tomcat.

It contains:

- Servlets as the controller layer
- JSP pages as the view layer
- Static resources (CSS, JS, images)
- SOAP client stubs generated using `wsimport`

Its main responsibility is to provide a browser-accessible interface for system users (tourists and suppliers), relying on the backend for all business operations.

---

### Desktop Application (Swing)

The Swing application is not an external system.

It is implemented as part of the backend project and shares the same codebase.

Its main purpose is to provide administrative functionality and support workflows where certain actions can be performed locally, complementing web usage scenarios.

This is particularly relevant in the project evolution, where the desktop interface was implemented first and later expanded with a web interface.

---

### Portable Server (Tomcat + HSQLDB)

The repository includes a preconfigured Tomcat server bundled directly in the project structure.

This server contains:

- Apache Tomcat configured for Jakarta EE
- An embedded HSQLDB server running in server mode
- Required libraries and runtime configuration
- A shared database storage directory

This approach provides a consistent execution environment across different machines and operating systems.

---

## Communication Between Components

The main integration mechanism is based on **SOAP Web Services (JAX-WS)**.

- The backend publishes SOAP services on port `8007`
- The web frontend generates stubs using Maven (`wsimport`)
- Servlets invoke the backend remotely through the generated stubs

This makes the web frontend independent from the backend implementation details, relying instead on WSDL contracts.

Important clarification:

- The **desktop Swing application does not communicate via SOAP**.
- Swing interacts directly with the backend controllers since it is part of the same backend codebase.

---

## Layered Design

The backend follows a structured layered approach:

- **Controllers** represent business logic and use cases.
- **Handlers** encapsulate persistence operations.
- **Entities** represent the database model.
- **DTOs** are used for communication through SOAP services.

The web frontend also follows a clear separation:

- **Servlets** handle request logic and call backend services.
- **JSP** handles rendering and UI.

---

## Data Persistence

Persistence is implemented using:

- **Jakarta Persistence API (JPA)**
- **EclipseLink** as the JPA provider
- **HSQLDB** as the database engine

The system uses an HSQLDB server mode configuration, allowing:

- Shared access between multiple clients
- Consistent storage across executions
- Reusability between desktop and web workflows

---

## Portability as a Design Goal

A key engineering decision in TurismoUY was to make the system portable and reproducible across environments.

This was achieved by:

- Bundling a preconfigured Tomcat server directly into the repository
- Providing scripts for different operating systems
- Standardizing ports and runtime configuration
- Ensuring the same project structure works consistently on both Windows and Linux

This portability was not a requirement of the academic assignment, but rather a technical improvement to reduce friction during development and evaluation.

---

## Summary

TurismoUY demonstrates an architecture with:

- A shared backend core reused across multiple interfaces
- A web frontend integrated through SOAP and generated stubs
- A Swing desktop interface embedded in the backend project
- A portable server package ensuring cross-platform execution
- A layered design aligned with enterprise development practices

Overall, the project provides a strong example of a distributed architecture implemented using classic Java enterprise technologies, while still prioritizing modularity and maintainability.

---


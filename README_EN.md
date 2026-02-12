🌐 Language: [Spanish](README.md) | **English**

# TurismoUY - Tourism Management System (Academic Project)

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![Tomcat](https://img.shields.io/badge/Tomcat-11.0.11-yellow.svg)](https://tomcat.apache.org/)
[![HSQLDB](https://img.shields.io/badge/HSQLDB-2.7.2-green.svg)](http://hsqldb.org/)

TurismoUY is a tourism management system developed as an academic project, designed to demonstrate software engineering concepts applied to a real-world domain.

The system is built with a layered Java architecture and includes both a web application and a desktop application, connected through SOAP Web Services. It also features a portable Tomcat server setup to simplify cross-platform execution and reduce environment configuration issues.

---

## Project Overview

The goal of TurismoUY is to support a basic tourism ecosystem where different actors interact:

- Tourists can browse activities and register for outings.
- Suppliers can create activities and publish outings.
- Administrators can manage and approve data using the desktop application.
- The web application provides a user-facing interface.
- The desktop application supports administrative operations.
- A backend layer exposes the core business logic through SOAP Web Services.

This approach allows the system to demonstrate a real distributed architecture where components interact through defined contracts instead of direct dependencies.

---

## Architecture Summary

TurismoUY is structured into three main components:

### 1. Backend (Business Logic + SOAP Web Services)
A standalone Java backend that contains:

- Domain logic and controllers
- JPA persistence layer (EclipseLink)
- SOAP Web Services (JAX-WS)
- DTOs and custom business exceptions

It publishes SOAP endpoints on port **8007**.

### 2. Web Application (Frontend)
A Jakarta EE web application running on Tomcat that includes:

- Servlets (presentation layer)
- JSP views
- SOAP client stubs generated via `wsimport`

It runs on Tomcat port **8080** and consumes the backend services remotely.

### 3. Desktop Application (Swing)
A Java Swing desktop client used for administrative tasks.

This desktop application interacts with the same backend logic and shares the same database, enabling real multi-client workflows (web + desktop).

---

## Cross-Platform Portability (Custom Improvement)

A key improvement added to this project was making the execution environment portable and consistent across Windows and Linux systems.

To achieve this, the repository includes:

- A preconfigured Apache Tomcat server inside the monorepo
- A portable database configuration
- A Maven version included in the repository for consistent builds

This reduces setup issues, ensures consistent execution, and allows all team members to run the project with the same stack and configuration, regardless of OS.

This was not a course requirement, but an engineering decision to improve usability, reproducibility, and portability.

---

## Key Functionalities

The system supports the following main features:

- User registration and authentication (Tourists and Suppliers)
- Tourist activity management
- Outing creation and listing
- Tourist outing inscriptions
- User profile management and image handling
- Administrative workflows through the desktop client
- Data persistence through a shared relational database

---

## Documentation

This repository includes additional documentation in the `docs/` directory:

- Setup and execution guide
- System architecture description
- SOAP Web Services documentation
- Features and capabilities breakdown
- Project evolution and design decisions
- Full tech stack description

You can start here:

- [Setup Guide](docs/SETUP_EN.md)
- [Architecture](docs/ARCHITECTURE_EN.md)
- [Web Services](docs/WEBSERVICES_EN.md)
- [Features](docs/FEATURES_EN.md)
- [Tech Stack](docs/TECH_STACK_EN.md)
- [Project Evolution](docs/PROJECT_EVOLUTION_EN.md)

---

## Tech Stack (Summary)

- Java 17
- Jakarta EE (Servlets, Persistence API)
- JPA (EclipseLink)
- HSQLDB
- JAX-WS (SOAP Web Services)
- JAXB (XML serialization)
- Apache Tomcat
- Maven
- Swing (Desktop GUI)
- JSP + Bootstrap (UI layer)

A more detailed breakdown is available here:  
[Tech Stack Documentation](docs/TECH_STACK_EN.md)

---

## Notes

This project was developed using **Eclipse IDE** as part of the course requirements, and the repository is structured to support that workflow.

Even though the project includes scripts and a portable server setup, Eclipse remains the recommended environment to run the system as originally intended.

---

This repository is part of the **Tecnólogo en Informática** program, a degree jointly managed by **Universidad de la República (UDELAR)**, **Universidad Tecnológica (UTEC)**, and **Dirección General de Educación Técnico Profesional UTU**.  
More information about the program can be found here:  

https://eduterciaria.utu.edu.uy/2024/06/18/tecnologo-en-informatica/

🌐 Language: [Español](TECH_STACK.md) | **English**

# Tech Stack

This document summarizes the technologies used in TurismoUY and their role within the system.

---

## Backend

The backend concentrates business logic, persistence, and service exposure.

- **Java SE 17**  
  Primary programming language.

- **Jakarta EE 10**  
  Enterprise specification foundation used throughout the project.

- **Jakarta Persistence API (JPA) 3.1**  
  ORM abstraction layer for object–relational mapping.

- **EclipseLink 4.x**  
  JPA implementation used for data persistence.

- **JAX-WS (Metro) 4.x**  
  SOAP Web Services implementation.

- **JAXB 4.x**  
  XML serialization and deserialization of DTOs.

- **HSQLDB 2.7.x**  
  Relational database running in server mode.

---

## Web Frontend

Traditional web interface built using Jakarta technologies.

- **Jakarta Servlet 6.0**
- **JSP (Jakarta Pages)**
- **Bootstrap 5**
- **Maven WAR Plugin**

The frontend consumes backend services through SOAP stubs automatically generated with `wsimport`.

---

## Desktop Application

Administrative interface developed using:

- **Java Swing**

The desktop application shares backend business logic directly within the same module.

---

## Infrastructure & Environment

- **Apache Tomcat 11.0.x**
  - Included within the repository (portable structure)
  - Designed to run from Eclipse (academic requirement)

- **HSQLDB Server Mode**
  - Shared database used by both web and desktop interfaces

---

## Build & Tooling

- **Apache Maven 3.8+**
  - Dependency management
  - Multi-module build
  - SOAP stub generation (`wsimport`)
  - Javadoc generation

- **Git**
  - Version control

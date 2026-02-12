🌐 Language: [Spanish](PROJECT_EVOLUTION.md) | **English**

# Project Evolution

## Overview

This project was not developed as a single, static implementation.  
It evolved incrementally throughout the semester, reflecting architectural decisions, refactoring stages, and increasing levels of decoupling between components.

The evolution of the system demonstrates progressive improvements in modularity, separation of concerns, and reusability.

---

## Phase 1 — Central Server + Desktop Client (Swing)

The initial implementation focused on:

- A centralized backend containing:
  - Business logic
  - Persistence layer (JPA + EclipseLink)
  - Entity management
- A desktop client (Swing) directly invoking backend controllers

At this stage:

- There was no web client.
- The system operated as a monolithic desktop-oriented application.
- All interactions occurred within the same logical boundary.

This phase established the core domain model and persistence layer.

---

## Phase 2 — Web Client with Duplicated Backend Logic

The next iteration introduced a web client using:

- Jakarta Servlets
- JSP
- Apache Tomcat

However, the web client compiled and executed its own version of the backend logic (packaged as a JAR).  
This resulted in:

- Code duplication
- Two independent execution contexts
- Tighter coupling between frontend and backend components

Although functional, this architecture limited reuse and increased maintenance complexity.

---

## Phase 3 — Decoupling Through SOAP Web Services

The final architectural iteration introduced SOAP-based Web Services.

The backend was transformed into a standalone service layer that:

- Publishes SOAP endpoints (JAX-WS / Metro)
- Exposes WSDL contracts
- Centralizes business logic and persistence

The web application:

- Generates SOAP stubs via `wsimport`
- Communicates remotely with the backend over HTTP

The desktop client:

- Continues to interact directly with backend controllers
- Shares the same central logic

This change enabled:

- A single authoritative backend
- Clear separation between presentation layers and business logic
- Reusability of the backend by multiple interfaces

---

## Cross-Interface Workflow Integration

One of the most relevant architectural outcomes is the ability to support cross-interface workflows.

For example:

- A supplier creates a tourist activity through the web interface.
- An administrator reviews and approves that activity from the desktop client.
- Both operations interact with the same centralized backend and database.

This demonstrates:

- True separation of concerns
- Multi-client consistency
- Shared state across different presentation layers

---

## Portability and Monorepo Strategy

Although not a formal requirement, an additional architectural improvement was implemented:

- The project was structured as a monorepo.
- A portable Apache Tomcat instance was included.
- A shared database directory was configured.

This allowed:

- Cross-platform execution (Linux and Windows)
- Shared persistence between environments
- Simplified configuration for all team members
- Reduced setup inconsistencies

This portability decision was an engineering solution aimed at ensuring reproducibility and usability across operating systems.

---

## Engineering Perspective

From an engineering standpoint, the evolution of the project reflects:

- Progressive architectural refinement
- Incremental decoupling of components
- Improved modularity
- Backend reuse across multiple interfaces
- Practical problem-solving beyond academic requirements

The final architecture supports:

- A centralized business core
- Multiple presentation layers
- Controlled inter-process communication
- Cross-platform execution


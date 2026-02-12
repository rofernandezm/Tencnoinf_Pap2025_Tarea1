🌐 Language: [Spanish](SETUP.md) | **English**

# Setup Guide (TurismoUY)

This document explains how to install, build, and run **TurismoUY** locally.

TurismoUY is a **multi-module Java project** that includes three main components:

- **Backend** (Java application that publishes SOAP Web Services)
- **Frontend** (Jakarta Servlets + JSP, deployed on Tomcat)
- **Portable Server** (Tomcat + HSQLDB preconfigured inside the repository)

The project is designed to run primarily using **Eclipse IDE**, as required by the academic assignment.  
However, all build and execution steps are also reproducible from the terminal.

---

## Table of Contents

- [Setup Guide (TurismoUY)](#setup-guide-turismouy)
  - [Table of Contents](#table-of-contents)
  - [Prerequisites](#prerequisites)
  - [Required Ports](#required-ports)
  - [Repository Structure](#repository-structure)
  - [Build and Execution Flow (Critical Order)](#build-and-execution-flow-critical-order)
  - [Step-by-Step Setup](#step-by-step-setup)
    - [1. Clone Repository](#1-clone-repository)
    - [2. Build Backend](#2-build-backend)
    - [3. Start SOAP Publisher](#3-start-soap-publisher)
    - [4. Build Frontend (wsimport)](#4-build-frontend-wsimport)
    - [5. Start Tomcat (Portable Server)](#5-start-tomcat-portable-server)
      - [Linux / macOS](#linux--macos)
      - [Windows](#windows)
    - [6. Access the Web Application](#6-access-the-web-application)
  - [Database Setup (HSQLDB)](#database-setup-hsqldb)
  - [Troubleshooting](#troubleshooting)
    - [Error: `Connection refused: 127.0.0.1:8007`](#error-connection-refused-1270018007)
    - [Error: `Failed to download WSDL`](#error-failed-to-download-wsdl)
    - [Error: `Address already in use` (ports 8007, 8080, or 9001)](#error-address-already-in-use-ports-8007-8080-or-9001)
      - [Linux / macOS](#linux--macos-1)
      - [Windows](#windows-1)
    - [Tomcat Starts but Database Does Not Persist](#tomcat-starts-but-database-does-not-persist)
  - [Useful Maven Commands](#useful-maven-commands)
    - [Backend](#backend)
    - [Run Publisher](#run-publisher)
    - [Frontend Build](#frontend-build)
    - [Generate Javadoc](#generate-javadoc)
  - [Final Notes](#final-notes)

---

## Prerequisites

The following software is required:

| Tool | Version | Notes |
|------|---------|------|
| Java JDK | 17+ | Required to build and run the project |
| Apache Maven | 3.8+ | The repository includes Maven 3.9.11 under `/resources` |
| Eclipse IDE | 2024-03+ | Recommended and required by the academic assignment |
| Git | Any | Used to clone the repository |

---

## Required Ports

TurismoUY uses **three critical ports**:

| Port | Service | Component |
|------|---------|----------|
| 8007 | SOAP Web Services Publisher | Backend |
| 8080 | Web Application (HTTP) | Tomcat |
| 9001 | Database Server | HSQLDB (inside Tomcat) |

Before running the project, make sure these ports are free.

---

## Repository Structure

The repository is organized as a **monorepo**, including the server runtime:

```
/backend        -> Java backend application (business logic + SOAP WS)
/frontend       -> Jakarta Servlet web application (WAR)
/server         -> Portable Apache Tomcat + HSQLDB configuration
/resources      -> Maven distribution included for portability
```

This structure improves portability and allows running the full system without external Tomcat/HSQLDB installation.

---

## Build and Execution Flow (Critical Order)

TurismoUY requires a **strict build and execution order**, because the frontend uses `wsimport` to generate SOAP client stubs.

The correct flow is:

1. Build backend (creates JAR)
2. Run SOAP publisher (exposes WSDLs on port 8007)
3. Build frontend (downloads WSDLs and generates stubs)
4. Start Tomcat (serves WAR on port 8080 and starts HSQLDB on port 9001)

If the SOAP publisher is not running, frontend compilation will fail.

---

## Step-by-Step Setup

### 1. Clone Repository

```bash
git clone https://github.com/rofernandezm/Tencnoinf_Pap2025_Tarea1.git
cd Tencnoinf_Pap2025_Tarea1
```

---

### 2. Build Backend

From the root directory:

```bash
cd backend
mvn clean install
```

Expected output artifact:

```
backend/target/turismouy.Backend-1.0.0.jar
```

---

### 3. Start SOAP Publisher

The backend publishes SOAP Web Services on port **8007**.

Run the publisher from a separate terminal:

```bash
cd backend
mvn exec:java -Prun-publisher
```

Expected logs:

```
[UserWebService] http://localhost:8007/ws/user?wsdl
[ActivityWebService] http://localhost:8007/ws/activity?wsdl
[OutingAndInscriptionWebService] http://localhost:8007/ws/outingAndInscription?wsdl
```

Important: this process must remain running while building the frontend.

---

### 4. Build Frontend (wsimport)

The frontend uses the `jaxws-maven-plugin` to download WSDLs and generate SOAP stubs.

In a second terminal:

```bash
cd frontend
mvn clean package
```

Expected output artifact:

```
frontend/target/turismouy.UI.war
```

Generated SOAP stubs location:

```
frontend/target/generated-sources/wsimport/
```

---

### 5. Start Tomcat (Portable Server)

The repository includes a portable Tomcat distribution already configured.

#### Linux / macOS

```bash
cd server/apache-tomcat-11.0.11/bin
chmod +x *.sh
./startup.sh
```

#### Windows

```powershell
cd serverpache-tomcat-11.0.11in
startup.bat
```

Expected result:

- Tomcat starts on port **8080**
- HSQLDB starts on port **9001** (via lifecycle listener)

---

### 6. Access the Web Application

Once Tomcat is running:

```
http://localhost:8080/turismouy.UI/
```

---

## Database Setup (HSQLDB)

TurismoUY uses **HSQLDB**, configured in server mode.

The database runs automatically when Tomcat starts.

Default configuration:

- Port: `9001`
- Database name: `turismoUyDB`
- Username: `SA`
- Password: (empty)

Connection URL:

```
jdbc:hsqldb:hsql://localhost:9001/turismoUyDB
```

Database files are stored under:

```
server/apache-tomcat-11.0.11/data/
```

This allows persistence across executions.

---

## Troubleshooting

### Error: `Connection refused: 127.0.0.1:8007`

This happens when compiling the frontend without the publisher running.

Solution:

```bash
cd backend
mvn exec:java -Prun-publisher
```

Then recompile frontend:

```bash
cd frontend
mvn clean package
```

---

### Error: `Failed to download WSDL`

Cause: SOAP endpoints are not reachable.

Verify WSDL availability:

```bash
curl http://localhost:8007/ws/user?wsdl
```

If the response is XML, the publisher is running correctly.

---

### Error: `Address already in use` (ports 8007, 8080, or 9001)

One of the ports is already taken.

Check port usage:

#### Linux / macOS

```bash
lsof -i :8007
lsof -i :8080
lsof -i :9001
```

#### Windows

```cmd
netstat -ano | findstr :8007
netstat -ano | findstr :8080
netstat -ano | findstr :9001
```

Stop the conflicting process and retry.

---

### Tomcat Starts but Database Does Not Persist

The HSQLDB database files should be stored under:

```
server/apache-tomcat-11.0.11/data/
```

If Tomcat is executed from Eclipse, it may use a different deployment folder (`wtpwebapps`).

For full portability and consistency, running the server directly from `/server/apache-tomcat-11.0.11/bin` is recommended.

---

## Useful Maven Commands

### Backend

```bash
cd backend
mvn clean install
```

### Run Publisher

```bash
cd backend
mvn exec:java -Prun-publisher
```

### Frontend Build

```bash
cd frontend
mvn clean package
```

### Generate Javadoc

```bash
cd backend
mvn javadoc:javadoc
```

---

## Final Notes

- The project was developed under an academic requirement to use Eclipse IDE.
- The repository includes a portable Tomcat + HSQLDB configuration to ensure cross-platform reproducibility.

- The execution order is critical due to SOAP stub generation (`wsimport`).

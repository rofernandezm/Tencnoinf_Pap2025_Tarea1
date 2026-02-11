🌐 Language: [Español](WEBSERVICES.md) | **English**

# Web Services SOAP - TurismoUY

This document describes the SOAP-based integration layer of the **TurismoUY** project.

The system uses **JAX-WS (Metro)** and **WSDL-based contracts** to expose backend functionality to the web interface through standard SOAP services.

---

## Overview

The project includes **three SOAP Web Services**, all published by the backend on port `8007`.

These services provide operations for:

- User management (tourists and suppliers)
- Tourist activities management
- Outings and tourist inscriptions

The web frontend consumes these services through **stubs automatically generated with `wsimport`**.

---

## Execution Flow (Important)

The frontend module depends on the WSDL endpoints to generate stubs during compilation.

That means the following order must be respected:

1. Compile backend
2. Publish SOAP services
3. Compile frontend (generates stubs)
4. Run Tomcat

If the publisher is not running, `wsimport` fails and the frontend build will not succeed.

---

## Available Endpoints

When the backend publisher is running, the following endpoints are available:

| Service | WSDL URL |
|--------|----------|
| User Service | `http://localhost:8007/ws/user?wsdl` |
| Activity Service | `http://localhost:8007/ws/activity?wsdl` |
| Outing & Inscription Service | `http://localhost:8007/ws/outingAndInscription?wsdl` |

---

## Services

### 1. UserService

Responsible for user registration, retrieval and updates.

It supports both **Tourists** and **Suppliers**.

Common operations include:

- Register users
- Modify user data
- Retrieve user information
- List registered users
- Update profile images

---

### 2. ActivityService

Responsible for managing tourist activities, including:

- Creating activities
- Updating activities
- Listing activities by status
- Listing activities by supplier
- Querying full activity details

---

### 3. OutingAndInscriptionService

Responsible for outing creation and tourist inscriptions.

Operations include:

- Register outings
- Query outing details
- Register inscriptions
- List inscriptions for a given outing
- List inscriptions by tourist nickname

---

## Stub Generation (wsimport)

The frontend project generates SOAP client stubs using Maven.

This is done automatically during build execution (`mvn clean package`).

Generated code is stored under:

```
frontend/target/generated-sources/wsimport/
```

This directory should **never be manually edited**, since it is regenerated on every build.

---

## wsimport Configuration

The plugin is configured inside `frontend/pom.xml` and points directly to the backend WSDL URLs.

Example structure:

```xml
<plugin>
  <groupId>com.sun.xml.ws</groupId>
  <artifactId>jaxws-maven-plugin</artifactId>
  <executions>
    <execution>
      <goals>
        <goal>wsimport</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <wsdlUrls>
      <wsdlUrl>http://localhost:8007/ws/user?wsdl</wsdlUrl>
      <wsdlUrl>http://localhost:8007/ws/activity?wsdl</wsdlUrl>
      <wsdlUrl>http://localhost:8007/ws/outingAndInscription?wsdl</wsdlUrl>
    </wsdlUrls>
    <packageName>turismouyapp.webservices</packageName>
    <keep>true</keep>
  </configuration>
</plugin>
```

---

## Architecture Notes

The SOAP layer is designed as a **remote integration contract** between backend and frontend.

The backend defines:

- Interfaces annotated with `@WebService`
- Implementations that delegate logic to controllers
- DTOs annotated with JAXB annotations for XML serialization
- Custom exceptions mapped to SOAP faults

The frontend uses stubs generated from WSDLs, ensuring a clear separation between both modules.

---

## Troubleshooting

### Problem: `Connection refused: 127.0.0.1:8007`

Cause: the publisher is not running when compiling the frontend.

Solution:

1. Run backend publisher:
   ```bash
   cd backend
   mvn exec:java -Prun-publisher
   ```

2. Wait until endpoints appear in console output.

3. Rebuild frontend:
   ```bash
   cd frontend
   mvn clean package
   ```

---

### Problem: Stubs not generated

Cause: WSDL download failed or publisher was not reachable.

Solution:

- Verify WSDL access:
  ```bash
  curl http://localhost:8007/ws/user?wsdl
  ```

- Rebuild frontend after publisher is running.

---

## References

- JAX-WS (Metro): https://eclipse-ee4j.github.io/metro-jax-ws/
- Jakarta XML Web Services: https://jakarta.ee/specifications/xml-web-services/
- JAXB Reference: https://eclipse-ee4j.github.io/jaxb-ri/

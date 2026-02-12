🌐 Idioma: **Español** | [Inglés](TECH_STACK_EN.md)

# Stack Tecnológico

Este documento resume las tecnologías utilizadas en TurismoUY y el rol que cumplen dentro del sistema.

---

## Backend

El backend concentra la lógica de negocio, persistencia y exposición de servicios.

- **Java SE 17**  
  Lenguaje principal del sistema.

- **Jakarta EE 10**  
  Base de especificaciones empresariales utilizadas.

- **Jakarta Persistence API (JPA) 3.1**  
  Abstracción ORM para el mapeo objeto-relacional.

- **EclipseLink 4.x**  
  Implementación JPA utilizada para la persistencia.

- **JAX-WS (Metro) 4.x**  
  Exposición de Web Services SOAP.

- **JAXB 4.x**  
  Serialización y deserialización XML de DTOs.

- **HSQLDB 2.7.x**  
  Base de datos relacional ejecutada en modo servidor.

---

## Frontend Web

Interfaz web tradicional basada en tecnologías Jakarta.

- **Jakarta Servlet 6.0**
- **JSP (Jakarta Pages)**
- **Bootstrap 5**
- **Maven WAR Plugin**

El frontend consume el backend mediante stubs SOAP generados automáticamente con `wsimport`.

---

## Aplicación Desktop

Interfaz administrativa desarrollada en:

- **Java Swing**

La aplicación desktop comparte directamente la lógica del backend dentro del mismo módulo.

---

## Infraestructura y Entorno

- **Apache Tomcat 11.0.x**
  - Incluido dentro del repositorio (estructura portable)
  - Configurado para ejecución desde Eclipse (requisito académico)

- **HSQLDB Server Mode**
  - Base de datos compartida entre interfaz web y aplicación desktop

---

## Build y Herramientas

- **Apache Maven 3.8+**
  - Gestión de dependencias
  - Compilación multi-módulo
  - Generación de stubs SOAP (`wsimport`)
  - Generación de documentación Javadoc

- **Git**
  - Control de versiones


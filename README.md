# TurismoUY – Sistema de Gestión Turística

Proyecto académico desarrollado para la asignatura **Programación de Aplicaciones**, correspondiente a la carrera **Tecnólogo en Informática**.

TurismoUY es un sistema de gestión turística implementado en **Java / Jakarta EE**, con arquitectura en **tres capas** (Web, Servidor y Escritorio), comunicación mediante **Web Services SOAP**, persistencia relacional con **JPA (EclipseLink)** y base de datos **HSQLDB**, ejecutado sobre **Apache Tomcat**.

El proyecto fue diseñado para ejecutarse de forma **portable y consistente** en distintos entornos (Linux / Windows), encapsulando servidor, base de datos y configuración dentro del propio repositorio.

---

## 🧩 Componentes principales

- **Backend**  
  Lógica de negocio, entidades JPA, controladores y Web Services SOAP.

- **Frontend Web**  
  Aplicación web basada en Servlets y JSP, con diseño responsive para acceso desde navegadores desktop y mobile.

- **Aplicación de Escritorio**  
  Cliente Swing que consume la misma lógica de negocio.

- **Servidor**  
  Apache Tomcat preconfigurado, con HSQLDB inicializado automáticamente mediante listeners.

---

## 🧠 Contenidos destacados

- Arquitectura en capas claramente definida  
- Comunicación distribuida mediante contratos SOAP  
- Persistencia relacional con JPA  
- Portabilidad del entorno de ejecución  
- Separación entre lógica de negocio y presentación  
- Documentación técnica extensa (README + docs)

---

## 📂 Documentación adicional

La carpeta `/docs` contiene documentación técnica detallada sobre:

- Arquitectura del sistema  
- Flujo de ejecución  
- Decisiones de diseño  
- Portabilidad del entorno  
- Encadre académico  
- Alcances y limitaciones  

Ver índice completo en `/docs`.

---

## 🛠️ Tecnologías principales

- Java 17  
- Jakarta EE  
- JPA (EclipseLink)  
- Web Services SOAP (JAX-WS)  
- Apache Tomcat  
- HSQLDB  
- Maven  

---

## 📌 Nota

Este proyecto es de carácter **académico**, con foco en demostrar conceptos de **programación de aplicaciones**, arquitectura y comunicación entre componentes, no en un entorno productivo real.
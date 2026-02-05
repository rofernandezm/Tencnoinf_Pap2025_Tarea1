# Arquitectura del Sistema

TurismoUY implementa una **arquitectura en tres capas**, separando responsabilidades y permitiendo la reutilización de la lógica de negocio desde distintos clientes.

---

## 🧱 Capas del sistema

### 1. Capa Web (Frontend)

- Aplicación web basada en **Jakarta Servlets y JSP**
- Comunicación con la capa de servidor mediante **Web Services SOAP**
- Diseño responsive, permitiendo acceso desde navegadores desktop y mobile
- No contiene lógica de negocio: actúa como consumidor de servicios

---

### 2. Capa Servidor (Backend)

- Núcleo del sistema
- Implementa la lógica de negocio
- Expone servicios mediante **JAX-WS**
- Contiene:
  - Controladores
  - Entidades JPA
  - DTOs
  - Excepciones de dominio
- Orquesta la persistencia y reglas del sistema

---

### 3. Capa Escritorio

- Aplicación **Swing**
- Consume la misma lógica de negocio que la capa web
- Demuestra reutilización del backend sin dependencia del frontend web

---

## 🔄 Comunicación entre capas

- Web ↔ Servidor: SOAP sobre HTTP
- Escritorio ↔ Servidor: llamadas directas a lógica de negocio
- Servidor ↔ Base de Datos: JPA / JDBC

---

## 🎯 Beneficios de esta arquitectura

- Separación clara de responsabilidades
- Posibilidad de múltiples clientes
- Facilidad de mantenimiento
- Comprensión clara del flujo de datos
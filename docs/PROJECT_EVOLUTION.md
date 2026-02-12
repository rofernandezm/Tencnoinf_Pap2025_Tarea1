🌐 Idioma: **Español** | [Inglés](PROJECT_EVOLUTION_EN.md)

# Evolución del Proyecto

## Introducción

TurismoUY no fue concebido como una solución monolítica desde el inicio. El proyecto evolucionó progresivamente a lo largo del curso, atravesando distintas etapas arquitectónicas que permitieron incorporar nuevos requerimientos sin descartar el trabajo previo.

La arquitectura final es el resultado de decisiones técnicas iterativas, donde cada fase buscó mejorar la separación de responsabilidades, la reutilización del backend y la independencia entre interfaces.

---

## 1. Fase Inicial: Aplicación Central con Interfaz Desktop (Swing)

El proyecto comenzó como una aplicación Java monolítica con interfaz de
escritorio (Swing). Esta primera etapa consistía en:

-   La lógica de negocio y la persistencia estaban integradas en el
    mismo módulo.
-   La aplicación Swing actuaba como única interfaz de usuario.
-   La base de datos HSQLDB se utilizaba en modo local.
-   No existía separación entre cliente y servidor.

Características
- Arquitectura en capas (Controller / Handler / Entity).
- Persistencia con JPA + EclipseLink.
- HSQLDB embebido.
- Factory singleton para gestión de controladores.

En esta etapa, tanto la lógica como la interfaz residían dentro del mismo módulo, lo que facilitaba el desarrollo inicial pero limitaba la escalabilidad y reutilización.

---

## 2. Segunda Fase: Cliente Web Acoplado al Backend

En la segunda etapa se incorporó una interfaz web basada en Servlets Jakarta y
JSP.

Inicialmente:

-   El frontend web compilaba y reutilizaba directamente el JAR del
    backend (Duplicación de la lógica central).
-   Dependencia directa entre frontend y backend.
-   Riesgo de inconsistencias si no se mantenía sincronización estricta.

Si bien esto permitió exponer el sistema vía navegador, mantenía un alto
acoplamiento.

---

## 3. Tercera Fase: Arquitectura Distribuida con Web Services SOAP

La etapa final introdujo una arquitectura distribuida (cliente-servidor) utilizando Web Services SOAP (JAX-WS).

En esta fase:

-   El backend publica servicios SOAP (JAX-WS).
-   El frontend web genera stubs automáticamente mediante wsimport.
-   La aplicación desktop continúa utilizando directamente la lógica del backend
-   Ambos clientes comparten el mismo servidor central.

Esto permitió:

-   Eliminación de duplicación de lógica.
-   Reutilizar el backend desde múltiples interfaces.
-   Contrato formal de integración vía WSDL.
-   Ejecutar simultáneamente la aplicación Swing y la web.
-   Simular escenarios más cercanos a sistemas empresariales reales.

---

## 4. Flujos Híbridos (Web + Desktop)

Una característica relevante del diseño final es la coexistencia de múltiples interfaces sobre el mismo núcleo de negocio.

Ejemplo:
1. Un proveedor crea una actividad desde la interfaz web.
2. La solicitud es gestionada por el backend.
3. Un administrador la revisa y aprueba desde la aplicación desktop.

Esto demuestra:
- Consistencia de estado.
- Compartición real de base de datos.
- Independencia de interfaz sin duplicación de lógica.

---

## Decisión de Monorepo y Servidor Portable

El uso de un monorepo que incluye:

- Backend
- Frontend
- Servidor Tomcat preconfigurado
- Base de datos HSQLDB

no fue un requisito académico, sino una decisión técnica orientada a:

- Portabilidad entre Linux y Windows.
- Configuración rápida en entornos heterogéneos.
- Garantizar que todos los integrantes trabajaran con el mismo stack.
- Minimizar errores de configuración.

El Tomcat portable permite que todos los desarrolladores apunten al mismo directorio clonado, evitando inconsistencias en rutas y dependencias.

---

## Requisito Académico: Eclipse como Entorno Principal

El proyecto fue requerido para ejecutarse desde Eclipse.

Por este motivo:

- La configuración está orientada a dicho entorno.
- El flujo de ejecución documentado prioriza Eclipse.
- Existen scripts auxiliares, pero no reemplazan el entorno requerido.

Esta decisión garantiza reproducibilidad bajo el contexto académico del curso.

---

## Conclusión

El proyecto permitió experimentar con:

- Evolución arquitectónica progresiva.
- Desacoplamiento real entre capas.
- Diseño de contratos SOAP.
- Gestión de stubs generados automáticamente.
- Coordinación entre múltiples interfaces.
- Portabilidad y configuración reproducible.

Más allá del resultado funcional, el valor principal radica en la transición desde una aplicación centralizada hacia una arquitectura distribuida con separación clara de responsabilidades.


# Flujo de Ejecución del Sistema

El sistema posee un **orden de ejecución estricto**, especialmente debido al uso de Web Services SOAP y generación de stubs.

---

## 🔁 Flujo general

1. Compilación del Backend
2. Publicación de Web Services
3. Compilación del Frontend (descarga WSDLs)
4. Inicio de Apache Tomcat
5. Acceso a la aplicación

---

## ⚠️ Orden crítico

El frontend **no puede compilarse** si los Web Services no están publicados previamente, ya que los stubs SOAP se generan dinámicamente a partir de los WSDL.

---

## 🌐 Puertos utilizados

- 8007: Publicación de Web Services SOAP
- 8080: Apache Tomcat (Frontend)
- 9001: HSQLDB

---

## ❗ Consideraciones importantes

- El Publisher debe permanecer en ejecución mientras se compila el frontend
- Tomcat inicializa automáticamente la base de datos
- El incumplimiento del orden genera errores de conexión o clases inexistentes
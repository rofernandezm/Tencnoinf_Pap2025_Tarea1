# Decisiones de Diseño

Las decisiones técnicas del proyecto responden tanto a los objetivos de la asignatura como a criterios de ingeniería aplicados durante el desarrollo.

---

## 🧩 Uso de Web Services SOAP

- Permite desacoplar frontend y backend
- Define contratos claros mediante WSDL
- Facilita la generación automática de stubs
- Refuerza conceptos de integración distribuida

---

## 🗄️ Persistencia con JPA + HSQLDB

- Uso de ORM para desacoplar modelo y base de datos
- Base de datos embebida para facilitar ejecución local
- Configuración flexible para distintos modos de conexión

---

## 🖥️ Servidor Tomcat integrado

- Se exigió el uso de Tomcat como contenedor
- Se decidió incluirlo dentro del repositorio
- Se configuró para iniciar la base de datos automáticamente

---

## 📚 Documentación extensa

- README detallado
- Documentación específica de Web Services
- Javadoc del backend y frontend
# Portabilidad del Entorno

Durante el desarrollo surgieron problemas al instalar y configurar Apache Tomcat en distintos sistemas operativos, lo que derivó en conflictos de ejecución, datos de prueba y control de versiones.

---

## 💡 Decisión clave

Se decidió **encapsular completamente el entorno de ejecución** dentro del repositorio:

- Tomcat preconfigurado
- Base de datos incluida
- Scripts multiplataforma
- Configuración versionada

---

## 🎯 Resultados

- Ejecución consistente en Linux y Windows
- Eliminación de conflictos por instalaciones locales
- Datos de prueba compartidos
- Reducción de errores de entorno
- Mayor reproducibilidad del proyecto

---

## 🧠 Aporte al proyecto

Esta decisión permitió destrabar problemas reales del equipo y mejorar la experiencia de desarrollo, asegurando que todos los integrantes trabajaran bajo las mismas condiciones técnicas.
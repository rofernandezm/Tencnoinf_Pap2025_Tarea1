🌐 Language: **Español** | [English](FEATURES_EN.md)

# Funcionalidades

## Visión General

TurismoUY es un sistema de gestión turística que permite administrar usuarios, actividades y salidas turísticas, integrando tanto una interfaz web como una aplicación de escritorio que comparten el mismo núcleo de negocio.

El sistema está orientado a cubrir el flujo completo desde la creación de una actividad hasta la inscripción de turistas.

---

## Gestión de Usuarios

El sistema permite registrar y administrar distintos tipos de usuarios:

### Tipos de usuario

- Turista
- Proveedor
- Administrador (gestión desde aplicación desktop)

### Funcionalidades principales

- Registro de nuevos usuarios.
- Autenticación mediante login.
- Consulta de datos personales.
- Gestión de imagen de perfil.
- Validación de unicidad (nickname y email).

---

## Gestión de Actividades Turísticas

Las actividades constituyen el núcleo funcional del sistema.

### Funcionalidades

- Creación de actividades por parte de proveedores.
- Definición de:
  - Nombre
  - Descripción
  - Duración
  - Costo
  - Ciudad
- Asociación a un proveedor.
- Consulta de actividades disponibles.
- Proceso de aprobación administrativa (flujo web → desktop).

El modelo permite que una actividad pase por un flujo de revisión antes de estar disponible para el público.

---

## Gestión de Salidas Turísticas

Una salida representa una instancia concreta de una actividad en una fecha determinada.

### Funcionalidades

- Creación de salidas asociadas a actividades.
- Definición de:
  - Fecha
  - Cupo máximo
  - Lugar de encuentro
- Consulta de salidas disponibles.
- Control de disponibilidad de cupos.

---

## Inscripción de Turistas

Los turistas pueden inscribirse a salidas disponibles.

### Funcionalidades

- Inscripción a salidas con control de cupo.
- Asociación entre:
  - Turista
  - Salida
  - Actividad
- Consulta de historial de inscripciones.
- Persistencia consistente de la relación entre entidades.

---

## Flujos Interfaz Web + Desktop

El sistema soporta flujos combinados entre interfaces.

Ejemplo típico:

1. Un proveedor crea una actividad desde la interfaz web.
2. Un administrador revisa y aprueba la actividad desde la aplicación desktop.
3. Una vez aprobada, el proveedor puede crear salidas asociadas a esa actividad.
4. Los turistas pueden inscribirse a las salidas disponibles.

Esto garantiza coherencia de datos y control administrativo centralizado.

---

## Gestión de Sesiones y Seguridad Básica

- Manejo de sesiones HTTP.
- Control de acceso según tipo de usuario.
- Hash de contraseñas.
- Validación básica de entrada de datos.

---

## Persistencia y Consistencia de Datos

El sistema garantiza:

- Integridad entre actividades, salidas e inscripciones.
- Relaciones consistentes entre usuarios y entidades asociadas.
- Almacenamiento estructurado mediante modelo relacional.

# Comandos Maven Simplificados - TurismoUY

Este proyecto incluye un POM padre que permite ejecutar tareas automatizadas similares a `npm scripts`.

## 📦 Compilación Completa

### Compilar Backend y Frontend en un solo comando

```bash
mvn clean install
```

Este comando:
1. Compila el Backend (`backend/`)
2. Instala el Backend en repositorio Maven local
3. Compila el Frontend (`frontend/`)
4. Genera `backend/target/turismouy.Backend-1.0.0.jar`
5. Genera `frontend/target/turismouy.UI.war`

## 🚀 Iniciar/Detener Servidor

### Iniciar Tomcat (Linux/macOS)

```bash
mvn validate -Pstart-server
```

### Detener Tomcat (Linux/macOS)

```bash
mvn validate -Pstop-server
```

⚠️ **Nota**: Para Windows, usar directamente:
```cmd
server\apache-tomcat-11.0.11\bin\startup.bat
server\apache-tomcat-11.0.11\bin\shutdown.bat
```

## 🔨 Comandos por Módulo

### Solo Backend

```bash
cd backend
mvn clean install
```

### Solo Frontend

```bash
cd frontend
mvn clean package
```

## 📚 Generar Documentación

### Javadoc del Backend

```bash
cd backend
mvn javadoc:javadoc
```

Documentación en: `backend/target/site/apidocs/index.html`

### Javadoc del Frontend

```bash
cd frontend
mvn javadoc:javadoc
```

Documentación en: `frontend/target/site/apidocs/index.html`

## 🧹 Limpieza

### Limpiar todo

```bash
mvn clean
```

### Limpiar y compilar

```bash
mvn clean install
```

## 🔍 Verificación

### Ver versiones de artefactos

```bash
mvn dependency:tree
```

### Verificar estructura del proyecto

```bash
mvn validate
```

## 💡 Equivalencias con npm

Si vienes del mundo Node.js, aquí está la comparación:

| npm | Maven | Descripción |
|-----|-------|-------------|
| `npm install` | `mvn install` | Instala dependencias y compila |
| `npm run build` | `mvn clean package` | Compila el proyecto |
| `npm run clean` | `mvn clean` | Limpia archivos generados |
| `npm start` | `mvn validate -Pstart-server` | Inicia el servidor |
| `npm test` | `mvn test` | Ejecuta tests |
| `npm run docs` | `mvn javadoc:javadoc` | Genera documentación |

## 🎯 Workflow Recomendado

### Primera vez (setup completo)

```bash
# 1. Compilar todo
mvn clean install

# 2. Iniciar servidor
mvn validate -Pstart-server

# 3. Acceder a: http://localhost:8080/turismouy.UI/
```

### Desarrollo diario

```bash
# Backend modificado
cd backend
mvn clean install

# Frontend modificado
cd frontend
mvn clean package

# Reiniciar Tomcat para ver cambios
mvn validate -Pstop-server
mvn validate -Pstart-server
```

## ⚙️ Perfiles Maven (Profiles)

Este proyecto usa perfiles para tareas específicas:

- **Default**: Compila backend y frontend
- **`start-server`**: Inicia Tomcat
- **`stop-server`**: Detiene Tomcat
- **`build-all`**: Compilación completa con limpieza profunda

Para activar un perfil:

```bash
mvn [goal] -P[profile-name]
```

Ejemplo:
```bash
mvn validate -Pstart-server
```

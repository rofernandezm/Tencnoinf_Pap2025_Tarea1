# 🚀 PLAN DE IMPLEMENTACIÓN - FILTROS MIDDLEWARE TURISMOUY

## 📋 GUÍA PASO A PASO PARA IMPLEMENTACIÓN

Este documento proporciona un plan de acción concreto y ordenado para implementar el sistema de filtros middleware descrito en `ARQUITECTURA_FILTRO_MIDDLEWARE.md`.

---

## 🎯 OBJETIVOS

1. ✅ Implementar autenticación y autorización basada en roles
2. ✅ Gestionar sesiones HTTP de forma segura
3. ✅ Controlar acceso a recursos según permisos
4. ✅ Mantener compatibilidad con código existente

---

## 📦 FASE 1: PREPARACIÓN DEL BACKEND

### Paso 1.1: Verificar UserType Enum

**Archivo**: `backend/src/main/java/turismouyapp/core/dto/UserType.java`

**Acción**: Verificar que existe y contiene los tres roles necesarios.

**Verificación**:
```bash
cd backend/src/main/java/turismouyapp/core/dto
cat UserType.java
```

**Si no existe o falta GUEST**, crear/modificar:

```java
package turismouyapp.core.dto;

public enum UserType {
    GUEST("Invitado"),
    TOURIST("Turista"),
    SUPPLIER("Proveedor");
    
    private final String displayName;
    
    UserType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
```

### Paso 1.2: Verificar DtUser

**Archivo**: `backend/src/main/java/turismouyapp/core/dto/DtUser.java`

**Acción**: Verificar que el método `getUserType()` existe.

```bash
cd backend/src/main/java/turismouyapp/core/dto
grep "getUserType" DtUser.java
```

**Si no existe**, agregar:

```java
public abstract UserType getUserType();
```

Y implementar en las subclases `DtTourist` y `DtSupplier`:

```java
// En DtTourist.java
@Override
public UserType getUserType() {
    return UserType.TOURIST;
}

// En DtSupplier.java
@Override
public UserType getUserType() {
    return UserType.SUPPLIER;
}
```

### Paso 1.3: Compilar Backend

```bash
cd backend
mvn clean install
```

**Resultado esperado**: `BUILD SUCCESS`

---

## 📦 FASE 2: CREAR INFRAESTRUCTURA DE SEGURIDAD

### Paso 2.1: Crear Paquete Security

```bash
cd frontend/src/main/java/turismouyapp
mkdir -p security
```

### Paso 2.2: Crear SessionUtils.java

**Archivo**: `frontend/src/main/java/turismouyapp/security/SessionUtils.java`

**Contenido**: Copiar de `ARQUITECTURA_FILTRO_MIDDLEWARE.md` sección "3. Utilidad de Sesión"

**Comandos**:
```bash
cd frontend/src/main/java/turismouyapp/security
# Crear archivo y pegar contenido
nano SessionUtils.java
```

### Paso 2.3: Crear AccessControlConfig.java

**Archivo**: `frontend/src/main/java/turismouyapp/security/AccessControlConfig.java`

**Contenido**: Copiar de `ARQUITECTURA_FILTRO_MIDDLEWARE.md` sección "2. Configuración de Permisos"

**Importante**: Ajustar los conjuntos de URLs según los servlets reales del proyecto:

```java
// Verificar URLs actuales
cd frontend/src/main/java/turismouyapp/servlets
grep -r "@WebServlet" .
```

Ejemplo de salida:
```
./Login.java:@WebServlet("/login")
./home.java:@WebServlet("/home")
./Activities.java:@WebServlet("/activities")
./Outings.java:@WebServlet("/outings")
./Inscriptions.java:@WebServlet("/inscriptions")
./ConsultUser.java:@WebServlet("/consult-user")
./ModifyDataUser.java:@WebServlet("/modify-data-user")
./Logout.java:@WebServlet("/logout")
```

Ajustar `AccessControlConfig.java` con estas URLs.

---

## 📦 FASE 3: CREAR FILTROS

### Paso 3.1: Crear Paquete Filters

```bash
cd frontend/src/main/java/turismouyapp
mkdir -p filters
```

### Paso 3.2: Crear CharacterEncodingFilter

**Archivo**: `frontend/src/main/java/turismouyapp/filters/CharacterEncodingFilter.java`

**Contenido**: Copiar de `ARQUITECTURA_FILTRO_MIDDLEWARE.md` sección "4. CharacterEncodingFilter"

**Orden**: 1 (primero)

### Paso 3.3: Crear SessionManagementFilter

**Archivo**: `frontend/src/main/java/turismouyapp/filters/SessionManagementFilter.java`

**Contenido**: Copiar de `ARQUITECTURA_FILTRO_MIDDLEWARE.md` sección "1. SessionManagementFilter"

**Orden**: 2

### Paso 3.4: Crear AuthenticationFilter

**Archivo**: `frontend/src/main/java/turismouyapp/filters/AuthenticationFilter.java`

**Contenido**: Copiar de `ARQUITECTURA_FILTRO_MIDDLEWARE.md` sección "2. AuthenticationFilter"

**Orden**: 3

### Paso 3.5: Crear AuthorizationFilter

**Archivo**: `frontend/src/main/java/turismouyapp/filters/AuthorizationFilter.java`

**Contenido**: Copiar de `ARQUITECTURA_FILTRO_MIDDLEWARE.md` sección "3. AuthorizationFilter"

**Orden**: 4

---

## 📦 FASE 4: MODIFICAR SERVLETS EXISTENTES

### Paso 4.1: Modificar Login.java

**Archivo**: `frontend/src/main/java/turismouyapp/servlets/Login.java`

**Cambios necesarios**:

1. Importar SessionUtils:
```java
import turismouyapp.security.SessionUtils;
```

2. Modificar método `handleLogin()` para usar SessionUtils:

**Buscar** (líneas ~104-124):
```java
private void createNewSessionAndAssingUser(HttpServletRequest request, HttpServletResponse response, DtUser user)
        throws ServletException, IOException {

    String msg = (String) request.getAttribute("mensaje");

    // Invalidar sesión anterior si existe (seguridad)
    HttpSession session = request.getSession(false);
    if (session != null) {
        session.invalidate();
    }

    // Crear NUEVA sesión → genera nuevo JSESSIONID
    session = request.getSession(true);
    session.setAttribute("logged_user", user);
    session.setAttribute("user_role", user.getUserType());
    if (msg != null) {
        request.setAttribute("mensaje", msg);
    }
    response.sendRedirect(request.getContextPath() + "/home");
}
```

**Reemplazar por**:
```java
private void createNewSessionAndAssingUser(HttpServletRequest request, HttpServletResponse response, DtUser user)
        throws ServletException, IOException {

    String msg = (String) request.getAttribute("mensaje");

    // Usar SessionUtils para inicializar sesión segura
    SessionUtils.initAuthenticatedSession(request, user);

    // Verificar si hay redirección pendiente
    HttpSession session = request.getSession();
    String redirectUrl = (String) session.getAttribute("redirect_after_login");
    
    if (redirectUrl != null) {
        session.removeAttribute("redirect_after_login");
        response.sendRedirect(request.getContextPath() + redirectUrl);
    } else {
        if (msg != null) {
            session.setAttribute("mensaje", msg);
        }
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
```

3. Modificar método `handleGuestLogin()`:

**Buscar** (líneas ~69-77):
```java
protected void handleGuestLogin(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(true);
    session.setAttribute("guest_mode", true);
    session.setAttribute("user_role", UserType.GUEST);

    response.sendRedirect(request.getContextPath() + "/home");
}
```

**Reemplazar por**:
```java
protected void handleGuestLogin(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // Usar SessionUtils para inicializar sesión de invitado
    SessionUtils.initGuestSession(request);
    response.sendRedirect(request.getContextPath() + "/home");
}
```

### Paso 4.2: Modificar/Crear Logout.java

**Archivo**: `frontend/src/main/java/turismouyapp/servlets/Logout.java`

**Verificar si existe**:
```bash
ls frontend/src/main/java/turismouyapp/servlets/Logout.java
```

**Si existe**, modificar método `doGet()`:

```java
import turismouyapp.security.SessionUtils;

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    // Usar SessionUtils para invalidar sesión
    SessionUtils.invalidateSession(request);
    
    // Redireccionar a login con mensaje
    request.getSession(true).setAttribute("mensaje", "Sesión cerrada correctamente");
    response.sendRedirect(request.getContextPath() + "/login");
}
```

**Si NO existe**, crear archivo completo:

```java
package turismouyapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import turismouyapp.security.SessionUtils;
import java.io.IOException;

/**
 * Servlet para cierre de sesión.
 * 
 * @author Equipo TurismoUY
 * @version 2.0.0
 * @since Tarea 2 - 2025
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public Logout() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Invalidar sesión
        SessionUtils.invalidateSession(request);
        
        // Redireccionar a login con mensaje
        request.getSession(true).setAttribute("mensaje", "Sesión cerrada correctamente");
        response.sendRedirect(request.getContextPath() + "/login");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
```

### Paso 4.3: Modificar Activities.java (Opcional - Verificación adicional)

**Archivo**: `frontend/src/main/java/turismouyapp/servlets/Activities.java`

**Cambio**: Agregar verificación de rol SUPPLIER para crear actividades.

**En el método que maneja creación** (buscar `action=create` o similar):

```java
import turismouyapp.security.SessionUtils;
import turismouyapp.core.dto.UserType;

// Al inicio del método de creación
if (!SessionUtils.hasRole(request, UserType.SUPPLIER)) {
    response.sendError(HttpServletResponse.SC_FORBIDDEN, 
        "Solo los proveedores pueden crear actividades");
    return;
}
```

### Paso 4.4: Modificar Inscriptions.java (Opcional - Verificación adicional)

**Archivo**: `frontend/src/main/java/turismouyapp/servlets/Inscriptions.java`

**Cambio**: Agregar verificación de rol TOURIST para inscripciones.

```java
import turismouyapp.security.SessionUtils;
import turismouyapp.core.dto.UserType;

// Al inicio del método de inscripción
if (!SessionUtils.hasRole(request, UserType.TOURIST)) {
    response.sendError(HttpServletResponse.SC_FORBIDDEN, 
        "Solo los turistas pueden inscribirse");
    return;
}
```

### Paso 4.5: Modificar ModifyDataUser.java

**Archivo**: `frontend/src/main/java/turismouyapp/servlets/ModifyDataUser.java`

**Cambio**: Asegurar que solo pueda modificar su propio perfil.

```java
import turismouyapp.security.SessionUtils;
import turismouyapp.core.dto.DtUser;

// Al inicio del método doPost
DtUser loggedUser = SessionUtils.getLoggedUser(request);
if (loggedUser == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}

String nicknameToEdit = request.getParameter("nickname");
if (nicknameToEdit == null || !loggedUser.getNickname().equals(nicknameToEdit)) {
    response.sendError(HttpServletResponse.SC_FORBIDDEN, 
        "Solo puede modificar su propio perfil");
    return;
}
```

---

## 📦 FASE 5: CREAR PÁGINAS DE ERROR

### Paso 5.1: Crear error403.jsp

**Archivo**: `frontend/src/main/webapp/WEB-INF/vistas/error403.jsp`

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%
String ctx = request.getContextPath();
String errorMessage = (String) request.getAttribute("error_message");
if (errorMessage == null) {
    errorMessage = "No tiene permisos para acceder a este recurso.";
}
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acceso Denegado - TurismoUY</title>
    <link rel="icon" type="image/png" href="<%=ctx%>/res/turismouyAppIcon.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" 
          rel="stylesheet">
    <link rel="stylesheet" 
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
    
    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow">
                    <div class="card-body text-center p-5">
                        <i class="bi bi-shield-lock text-danger" style="font-size: 4rem;"></i>
                        <h1 class="display-4 mt-3">403</h1>
                        <h2 class="mb-3">Acceso Denegado</h2>
                        <p class="lead text-muted"><%=errorMessage%></p>
                        
                        <div class="mt-4">
                            <a href="<%=ctx%>/home" class="btn btn-primary me-2">
                                <i class="bi bi-house-door me-1"></i> Ir al Inicio
                            </a>
                            <a href="javascript:history.back()" class="btn btn-outline-secondary">
                                <i class="bi bi-arrow-left me-1"></i> Volver
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

### Paso 5.2: Crear error404.jsp

**Archivo**: `frontend/src/main/webapp/WEB-INF/vistas/error404.jsp`

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página No Encontrada - TurismoUY</title>
    <link rel="icon" type="image/png" href="<%=ctx%>/res/turismouyAppIcon.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" 
          rel="stylesheet">
    <link rel="stylesheet" 
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
    
    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow">
                    <div class="card-body text-center p-5">
                        <i class="bi bi-compass text-warning" style="font-size: 4rem;"></i>
                        <h1 class="display-4 mt-3">404</h1>
                        <h2 class="mb-3">Página No Encontrada</h2>
                        <p class="lead text-muted">
                            La página que buscas no existe o fue movida.
                        </p>
                        
                        <div class="mt-4">
                            <a href="<%=ctx%>/home" class="btn btn-primary">
                                <i class="bi bi-house-door me-1"></i> Ir al Inicio
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

### Paso 5.3: Crear error500.jsp

**Archivo**: `frontend/src/main/webapp/WEB-INF/vistas/error500.jsp`

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error del Servidor - TurismoUY</title>
    <link rel="icon" type="image/png" href="<%=ctx%>/res/turismouyAppIcon.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" 
          rel="stylesheet">
    <link rel="stylesheet" 
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
    
    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow">
                    <div class="card-body text-center p-5">
                        <i class="bi bi-exclamation-triangle text-danger" style="font-size: 4rem;"></i>
                        <h1 class="display-4 mt-3">500</h1>
                        <h2 class="mb-3">Error del Servidor</h2>
                        <p class="lead text-muted">
                            Ocurrió un error inesperado. Nuestro equipo ha sido notificado.
                        </p>
                        
                        <div class="mt-4">
                            <a href="<%=ctx%>/home" class="btn btn-primary me-2">
                                <i class="bi bi-house-door me-1"></i> Ir al Inicio
                            </a>
                            <a href="javascript:location.reload()" class="btn btn-outline-secondary">
                                <i class="bi bi-arrow-clockwise me-1"></i> Reintentar
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

---

## 📦 FASE 6: ACTUALIZAR web.xml

### Paso 6.1: Modificar web.xml

**Archivo**: `frontend/src/main/webapp/WEB-INF/web.xml`

**Agregar después de los context-params existentes**:

```xml
<!-- Configuración de sesión -->
<session-config>
    <!-- Timeout de 30 minutos -->
    <session-timeout>30</session-timeout>
    <!-- Cookies HTTP-only (prevención XSS) -->
    <cookie-config>
        <http-only>true</http-only>
        <max-age>1800</max-age>
    </cookie-config>
    <!-- Tracking mode solo con cookies -->
    <tracking-mode>COOKIE</tracking-mode>
</session-config>

<!-- Páginas de error personalizadas -->
<error-page>
    <error-code>403</error-code>
    <location>/WEB-INF/vistas/error403.jsp</location>
</error-page>

<error-page>
    <error-code>404</error-code>
    <location>/WEB-INF/vistas/error404.jsp</location>
</error-page>

<error-page>
    <error-code>500</error-code>
    <location>/WEB-INF/vistas/error500.jsp</location>
</error-page>
```

---

## 📦 FASE 7: MODIFICAR JSPs

### Paso 7.1: Modificar header.jsp

**Archivo**: `frontend/src/main/webapp/WEB-INF/partials/header.jsp`

**Agregar al inicio** (después de los taglibs):

```jsp
<%@ page import="turismouyapp.security.SessionUtils" %>
<%@ page import="turismouyapp.core.dto.UserType" %>
<%@ page import="turismouyapp.core.dto.DtUser" %>
<%
// Obtener información de sesión
UserType userRole = SessionUtils.getUserRole(request);
DtUser loggedUser = SessionUtils.getLoggedUser(request);
boolean isGuest = (userRole == UserType.GUEST);
boolean isTourist = (userRole == UserType.TOURIST);
boolean isSupplier = (userRole == UserType.SUPPLIER);
%>
```

**Modificar menú de navegación** para mostrar/ocultar opciones:

```jsp
<!-- Ejemplo: Mostrar Inscripciones solo a turistas -->
<% if (isTourist) { %>
<li class="nav-item">
    <a class="nav-link <%=active.equals("inscriptions") ? "active" : ""%>" 
       href="<%=ctx%>/inscriptions">
        <i class="bi bi-card-checklist"></i> Inscripciones
    </a>
</li>
<% } %>

<!-- Ejemplo: Botón crear actividad solo para proveedores -->
<% if (isSupplier) { %>
<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalCrearActividad">
    <i class="bi bi-plus-circle"></i> Nueva Actividad
</button>
<% } %>

<!-- Ejemplo: Mostrar nombre de usuario si está logueado -->
<% if (!isGuest && loggedUser != null) { %>
<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
        <i class="bi bi-person-circle"></i> <%=loggedUser.getNickname()%>
    </a>
    <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="<%=ctx%>/consult-user?nickname=<%=loggedUser.getNickname()%>">
            <i class="bi bi-person"></i> Mi Perfil
        </a></li>
        <li><a class="dropdown-item" href="<%=ctx%>/modify-data-user">
            <i class="bi bi-gear"></i> Configuración
        </a></li>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="<%=ctx%>/logout">
            <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
        </a></li>
    </ul>
</li>
<% } else { %>
<li class="nav-item">
    <a class="btn btn-primary" href="<%=ctx%>/login">
        <i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión
    </a>
</li>
<% } %>
```

---

## 📦 FASE 8: COMPILAR Y DESPLEGAR

### Paso 8.1: Compilar Backend

```bash
cd backend
mvn clean install
```

**Verificar**: `BUILD SUCCESS`

### Paso 8.2: Compilar Frontend

```bash
cd ../frontend
mvn clean package
```

**Verificar**: 
- `BUILD SUCCESS`
- WAR generado en `frontend/target/turismouy.UI.war`

### Paso 8.3: Copiar WAR a Tomcat (si no usas Eclipse)

```bash
cp frontend/target/turismouy.UI.war ../server/apache-tomcat-11.0.11/webapps/
```

### Paso 8.4: Reiniciar Tomcat

**Linux/macOS**:
```bash
cd ../server/apache-tomcat-11.0.11
./bin/shutdown.sh
./bin/startup.sh
```

**Windows**:
```cmd
cd ..\server\apache-tomcat-11.0.11
bin\shutdown.bat
bin\startup.bat
```

---

## 🧪 FASE 9: TESTING

### Test 1: Verificar Sesión GUEST

1. Abrir navegador en: `http://localhost:8080/turismouy.UI/home`
2. Abrir DevTools → Application → Cookies
3. Verificar cookie `JSESSIONID` existe
4. En JSP, agregar debug temporal:
```jsp
<p>Rol: <%=SessionUtils.getUserRole(request)%></p>
```
**Esperado**: `GUEST`

### Test 2: Acceso Denegado a Inscripciones (GUEST)

1. Intentar acceder: `http://localhost:8080/turismouy.UI/inscriptions`
2. **Esperado**: Redirección a `/login`

### Test 3: Login Exitoso

1. En `/login`, ingresar credenciales válidas
2. **Esperado**: 
   - Redirección a `/home`
   - Menú muestra nombre de usuario
   - Opciones según rol visibles

### Test 4: Crear Actividad (SUPPLIER)

1. Loguearse como proveedor
2. Ir a `/activities`
3. Intentar crear actividad
4. **Esperado**: Formulario accesible y creación exitosa

### Test 5: Crear Actividad (TOURIST - Debe fallar)

1. Loguearse como turista
2. Intentar acceder a URL de crear actividad
3. **Esperado**: Error 403 o redirección

### Test 6: Logout

1. Hacer clic en "Cerrar Sesión"
2. **Esperado**: 
   - Sesión invalidada
   - Redirección a login
   - Menú vuelve a modo GUEST

### Test 7: Verificar Logs

```bash
tail -f server/apache-tomcat-11.0.11/logs/catalina.out
```

**Buscar**:
```
[SessionManagementFilter] Inicializado correctamente
[AuthenticationFilter] Inicializado correctamente
[AuthorizationFilter] Inicializado correctamente
```

---

## 🐛 TROUBLESHOOTING

### Problema 1: Filtros no se ejecutan

**Síntoma**: Sesión no se crea automáticamente

**Solución**:
1. Verificar que las clases tienen `@WebFilter`
2. Verificar que están en paquete correcto
3. Limpiar y recompilar:
```bash
cd frontend
mvn clean package
```
4. Verificar logs de Tomcat para errores de inicialización

### Problema 2: ClassNotFoundException

**Síntoma**: Error al iniciar Tomcat
```
ClassNotFoundException: turismouyapp.security.SessionUtils
```

**Solución**:
1. Verificar que SessionUtils.java existe y está en el paquete correcto
2. Recompilar frontend:
```bash
cd frontend
mvn clean package
```
3. Verificar que el WAR contiene las clases:
```bash
jar -tf frontend/target/turismouy.UI.war | grep SessionUtils
```

### Problema 3: Redirección infinita a /login

**Síntoma**: Navegador se queda en loop entre `/login` y `/home`

**Solución**:
1. Verificar que `/login` está en `PUBLIC_RESOURCES` en `AccessControlConfig.java`
2. Verificar que el AuthenticationFilter no intercepta `/login`:
```java
if (AccessControlConfig.isPublicResource(resource)) {
    chain.doFilter(request, response);
    return; // ← Importante: retornar aquí
}
```

### Problema 4: Error "Cannot create session after response has been committed"

**Síntoma**: Exception al intentar crear sesión

**Solución**:
1. Asegurar que no se llama `response.sendRedirect()` o `response.getWriter()` antes de manipular la sesión
2. Verificar orden de operaciones en servlets

### Problema 5: Sesión se pierde entre peticiones

**Síntoma**: Usuario debe loguearse en cada página

**Solución**:
1. Verificar que `web.xml` tiene configuración de sesión correcta
2. Verificar cookies en DevTools
3. Asegurar que `tracking-mode` es `COOKIE`
4. En desarrollo local, verificar que no hay problemas de dominio/puerto

---

## ✅ CHECKLIST FINAL

### Backend
- [ ] UserType enum con GUEST, TOURIST, SUPPLIER
- [ ] DtUser.getUserType() implementado
- [ ] Backend compilado exitosamente

### Frontend - Seguridad
- [ ] Paquete `turismouyapp.security` creado
- [ ] SessionUtils.java creado y compilado
- [ ] AccessControlConfig.java creado y ajustado con URLs reales

### Frontend - Filtros
- [ ] Paquete `turismouyapp.filters` creado
- [ ] CharacterEncodingFilter.java creado
- [ ] SessionManagementFilter.java creado
- [ ] AuthenticationFilter.java creado
- [ ] AuthorizationFilter.java creado

### Frontend - Servlets
- [ ] Login.java modificado para usar SessionUtils
- [ ] Logout.java creado/modificado
- [ ] Activities.java con verificación de rol
- [ ] Inscriptions.java con verificación de rol
- [ ] ModifyDataUser.java con verificación de propietario

### Frontend - Vistas
- [ ] error403.jsp creado
- [ ] error404.jsp creado
- [ ] error500.jsp creado
- [ ] header.jsp modificado con menú dinámico

### Configuración
- [ ] web.xml actualizado con session-config
- [ ] web.xml con páginas de error

### Despliegue
- [ ] Backend compilado (`mvn install`)
- [ ] Frontend compilado (`mvn package`)
- [ ] WAR desplegado en Tomcat
- [ ] Tomcat reiniciado

### Testing
- [ ] Sesión GUEST se crea automáticamente
- [ ] Login funciona correctamente
- [ ] Logout invalida sesión
- [ ] Acceso denegado funciona (GUEST → inscriptions)
- [ ] Verificación de roles funciona (TOURIST vs SUPPLIER)
- [ ] Páginas de error se muestran correctamente
- [ ] Menú se adapta según rol

---

## 📈 PRÓXIMOS PASOS (POST-IMPLEMENTACIÓN)

1. **Seguridad Avanzada**:
   - Implementar hashing de contraseñas (BCrypt)
   - Agregar CSRF tokens
   - Rate limiting en login

2. **UX Mejorado**:
   - Mensajes flash más elaborados
   - Breadcrumbs de navegación
   - Indicadores de carga (spinners)

3. **Testing Automatizado**:
   - JUnit tests para filtros
   - Integration tests con Arquillian
   - Selenium tests E2E

4. **Monitoreo**:
   - Logging estructurado (SLF4J + Logback)
   - Métricas de sesiones activas
   - Auditoría de accesos

---

**Documento creado por:** Equipo TurismoUY  
**Versión:** 1.0.0  
**Fecha:** Octubre 2025  
**Estado:** ✅ Guía Completa - Lista para Seguir

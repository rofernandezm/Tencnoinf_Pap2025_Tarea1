# 🔐 ARQUITECTURA DE FILTRO MIDDLEWARE - TURISMOUY

## 📋 ÍNDICE

1. [Visión General](#visión-general)
2. [Análisis de Requerimientos](#análisis-de-requerimientos)
3. [Arquitectura Propuesta](#arquitectura-propuesta)
4. [Componentes del Sistema](#componentes-del-sistema)
5. [Implementación Técnica](#implementación-técnica)
6. [Flujos de Autenticación](#flujos-de-autenticación)
7. [Configuración y Despliegue](#configuración-y-despliegue)
8. [Casos de Uso y Ejemplos](#casos-de-uso-y-ejemplos)

---

## 🎯 VISIÓN GENERAL

### Objetivo
Implementar un sistema de **filtros middleware Jakarta Servlet** que gestione:
- ✅ Autenticación de usuarios (login/logout)
- ✅ Autorización basada en roles (GUEST, TOURIST, SUPPLIER)
- ✅ Gestión de sesiones HTTP
- ✅ Control de acceso a recursos protegidos
- ✅ Redirección automática según permisos

### Tecnologías Base
- **Jakarta Servlet 6.0** - Filtros con `@WebFilter`
- **HttpSession** - Gestión de sesiones
- **Enumeraciones Java** - Definición de roles
- **Patrón Chain of Responsibility** - Cadena de filtros

---

## 📊 ANÁLISIS DE REQUERIMIENTOS

### Actores del Sistema (Tarea 2)

| Actor | Descripción | Permisos |
|-------|-------------|----------|
| **Visitante (GUEST)** | Usuario no autenticado | Consultas públicas |
| **Turista (TOURIST)** | Usuario autenticado tipo turista | Inscripciones, perfil |
| **Proveedor (SUPPLIER)** | Usuario autenticado tipo proveedor | Crear actividades, salidas |
| **Administrador** | Solo Estación de Trabajo (Swing) | No usa web |

### Requerimientos Funcionales

#### RF1: Inicio de Sesión
- Permitir acceso con `nickname` o `email` + `password`
- Crear sesión HTTP con atributos:
  - `logged_user` (DtUser)
  - `user_role` (UserType)
- Validar credenciales contra backend

#### RF2: Modo Invitado
- Permitir acceso sin autenticación
- Atributos de sesión:
  - `guest_mode = true`
  - `user_role = GUEST`
- Acceso limitado a consultas públicas

#### RF3: Cierre de Sesión
- Invalidar sesión HTTP
- Redireccionar a página de inicio

#### RF4: Control de Acceso por Recurso
- Restringir URLs según rol
- Redireccionar usuarios no autorizados

### Matriz de Permisos

| Caso de Uso | GUEST | TOURIST | SUPPLIER |
|-------------|-------|---------|----------|
| Consulta Usuario | ✅ | ✅ | ✅ |
| Consulta Actividad | ✅ | ✅ | ✅ |
| Consulta Salida | ✅ | ✅ | ✅ |
| Alta Usuario (registro) | ✅ | ❌ | ❌ |
| Modificar Datos Usuario | ❌ | ✅ (propio) | ✅ (propio) |
| Alta Actividad | ❌ | ❌ | ✅ |
| Alta Salida | ❌ | ❌ | ✅ |
| Inscripción a Salida | ❌ | ✅ | ❌ |
| Inicio/Cierre Sesión | ✅ | ✅ | ✅ |

---

## 🏗️ ARQUITECTURA PROPUESTA

### Patrón: Filter Chain + Role-Based Access Control (RBAC)

```
┌─────────────────────────────────────────────────────────────────┐
│                         CLIENTE WEB                              │
│                    (Browser - HTTP Request)                      │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                      APACHE TOMCAT 11                            │
│                                                                   │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              FILTER CHAIN (Orden de ejecución)           │   │
│  │                                                           │   │
│  │  1. CharacterEncodingFilter (UTF-8)                      │   │
│  │         ↓                                                 │   │
│  │  2. SessionManagementFilter                              │   │
│  │         ↓  (Inicializa sesión si no existe)              │   │
│  │  3. AuthenticationFilter                                 │   │
│  │         ↓  (Verifica autenticación)                      │   │
│  │  4. AuthorizationFilter (RBAC)                           │   │
│  │         ↓  (Verifica permisos por URL)                   │   │
│  └─────────┼───────────────────────────────────────────────┘   │
│            │                                                     │
│            ▼                                                     │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                    SERVLETS                              │   │
│  │  - Login                                                 │   │
│  │  - Logout                                                │   │
│  │  - home                                                  │   │
│  │  - Activities                                            │   │
│  │  - Outings                                               │   │
│  │  - Inscriptions                                          │   │
│  │  - ConsultUser                                           │   │
│  │  - ModifyDataUser                                        │   │
│  └─────────┼───────────────────────────────────────────────┘   │
│            │                                                     │
│            ▼                                                     │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │               BACKEND (JAR)                              │   │
│  │  - FactoryUyTourism                                      │   │
│  │  - Controllers (User, Activity, Outing)                  │   │
│  │  - Handlers (JPA)                                        │   │
│  │  - Entities (User, Tourist, Supplier)                    │   │
│  └─────────┼───────────────────────────────────────────────┘   │
│            │                                                     │
│            ▼                                                     │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              HSQLDB (Puerto 9001)                        │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

### Estrategia de Filtros

#### 1. **CharacterEncodingFilter** (Opcional pero recomendado)
- **Propósito**: Establecer UTF-8 en todas las peticiones/respuestas
- **URL Pattern**: `/*`
- **Orden**: Primero

#### 2. **SessionManagementFilter**
- **Propósito**: Inicializar sesión con valores por defecto
- **URL Pattern**: `/*`
- **Orden**: Segundo
- **Responsabilidades**:
  - Crear sesión HTTP si no existe
  - Establecer `guest_mode=true` si no hay usuario logueado
  - Establecer `user_role=GUEST` por defecto

#### 3. **AuthenticationFilter**
- **Propósito**: Verificar si el usuario está autenticado
- **URL Pattern**: `/protected/*` (recursos que requieren login)
- **Orden**: Tercero
- **Responsabilidades**:
  - Verificar presencia de `logged_user` en sesión
  - Si no autenticado → Redireccionar a `/login`
  - Si autenticado → Continuar cadena

#### 4. **AuthorizationFilter** (RBAC)
- **Propósito**: Controlar acceso basado en roles
- **URL Pattern**: URLs específicas según permisos
- **Orden**: Cuarto
- **Responsabilidades**:
  - Verificar `user_role` contra permisos del recurso
  - Si no autorizado → HTTP 403 o redirección
  - Si autorizado → Continuar cadena

---

## 🧩 COMPONENTES DEL SISTEMA

### 1. Enumeración de Roles

**Ubicación**: `backend/src/main/java/turismouyapp/core/dto/UserType.java`

```java
package turismouyapp.core.dto;

/**
 * Enumeración de tipos de usuario en el sistema TurismoUY.
 * 
 * @author Equipo TurismoUY
 * @version 2.0.0
 * @since Tarea 2 - 2025
 */
public enum UserType {
    /**
     * Usuario no autenticado (visitante).
     * Permisos: Solo consultas públicas.
     */
    GUEST("Invitado"),
    
    /**
     * Usuario turista autenticado.
     * Permisos: Inscripciones, modificar perfil propio.
     */
    TOURIST("Turista"),
    
    /**
     * Usuario proveedor autenticado.
     * Permisos: Crear actividades, salidas, modificar perfil propio.
     */
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

### 2. Configuración de Permisos

**Ubicación**: `frontend/src/main/java/turismouyapp/security/AccessControlConfig.java`

```java
package turismouyapp.security;

import turismouyapp.core.dto.UserType;
import java.util.*;

/**
 * Configuración centralizada de control de acceso basado en roles (RBAC).
 * 
 * Define qué roles pueden acceder a cada URL pattern.
 * 
 * @author Equipo TurismoUY
 * @version 2.0.0
 * @since Tarea 2 - 2025
 */
public class AccessControlConfig {
    
    // Recursos públicos (accesibles sin autenticación)
    public static final Set<String> PUBLIC_RESOURCES = Set.of(
        "/login",
        "/logout",
        "/home",
        "/consult-user",
        "/activities",
        "/outings",
        "/res/*",
        "/assets/*",
        "/WEB-INF/vistas/home.jsp",
        "/WEB-INF/vistas/iniciarSesionRegistrarse.jsp",
        "/WEB-INF/vistas/consultUser.jsp",
        "/WEB-INF/vistas/activities.jsp",
        "/WEB-INF/vistas/outings.jsp"
    );
    
    // Recursos que requieren autenticación (cualquier rol autenticado)
    public static final Set<String> AUTHENTICATED_RESOURCES = Set.of(
        "/modify-data-user",
        "/WEB-INF/vistas/modificarDatosUsuario.jsp"
    );
    
    // Recursos exclusivos de TOURIST
    public static final Set<String> TOURIST_ONLY_RESOURCES = Set.of(
        "/inscriptions"
    );
    
    // Recursos exclusivos de SUPPLIER
    public static final Set<String> SUPPLIER_ONLY_RESOURCES = Set.of(
        "/create-activity",
        "/create-outing",
        "/modify-activity"
    );
    
    /**
     * Verifica si un recurso es público.
     */
    public static boolean isPublicResource(String uri) {
        return PUBLIC_RESOURCES.stream().anyMatch(pattern -> matchesPattern(uri, pattern));
    }
    
    /**
     * Verifica si un usuario tiene acceso a un recurso.
     */
    public static boolean hasAccess(String uri, UserType userType) {
        // Recursos públicos: todos tienen acceso
        if (isPublicResource(uri)) {
            return true;
        }
        
        // Recursos autenticados: GUEST no tiene acceso
        if (matchesAnyPattern(uri, AUTHENTICATED_RESOURCES)) {
            return userType != UserType.GUEST;
        }
        
        // Recursos de TOURIST
        if (matchesAnyPattern(uri, TOURIST_ONLY_RESOURCES)) {
            return userType == UserType.TOURIST;
        }
        
        // Recursos de SUPPLIER
        if (matchesAnyPattern(uri, SUPPLIER_ONLY_RESOURCES)) {
            return userType == UserType.SUPPLIER;
        }
        
        // Por defecto, denegar acceso si no está en ninguna categoría
        return false;
    }
    
    /**
     * Verifica si una URI coincide con un pattern (soporta wildcard *).
     */
    private static boolean matchesPattern(String uri, String pattern) {
        if (pattern.endsWith("/*")) {
            String prefix = pattern.substring(0, pattern.length() - 2);
            return uri.startsWith(prefix);
        }
        return uri.equals(pattern);
    }
    
    /**
     * Verifica si una URI coincide con algún pattern de una colección.
     */
    private static boolean matchesAnyPattern(String uri, Set<String> patterns) {
        return patterns.stream().anyMatch(pattern -> matchesPattern(uri, pattern));
    }
    
    /**
     * Obtiene el mensaje de error apropiado para acceso denegado.
     */
    public static String getAccessDeniedMessage(UserType userType, String uri) {
        if (userType == UserType.GUEST) {
            return "Debe iniciar sesión para acceder a este recurso.";
        }
        return "No tiene permisos para acceder a este recurso.";
    }
}
```

### 3. Utilidad de Sesión

**Ubicación**: `frontend/src/main/java/turismouyapp/security/SessionUtils.java`

```java
package turismouyapp.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.dto.UserType;

/**
 * Utilidades para manejo de sesiones HTTP.
 * 
 * @author Equipo TurismoUY
 * @version 2.0.0
 * @since Tarea 2 - 2025
 */
public class SessionUtils {
    
    // Constantes de atributos de sesión
    public static final String ATTR_LOGGED_USER = "logged_user";
    public static final String ATTR_USER_ROLE = "user_role";
    public static final String ATTR_GUEST_MODE = "guest_mode";
    
    /**
     * Verifica si el usuario está autenticado.
     */
    public static boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        
        DtUser user = (DtUser) session.getAttribute(ATTR_LOGGED_USER);
        return user != null;
    }
    
    /**
     * Obtiene el usuario logueado de la sesión.
     * 
     * @return DtUser o null si no está autenticado
     */
    public static DtUser getLoggedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (DtUser) session.getAttribute(ATTR_LOGGED_USER);
    }
    
    /**
     * Obtiene el rol del usuario actual.
     * 
     * @return UserType, por defecto GUEST
     */
    public static UserType getUserRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return UserType.GUEST;
        }
        
        UserType role = (UserType) session.getAttribute(ATTR_USER_ROLE);
        return role != null ? role : UserType.GUEST;
    }
    
    /**
     * Verifica si está en modo invitado.
     */
    public static boolean isGuestMode(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return true;
        }
        
        Boolean guestMode = (Boolean) session.getAttribute(ATTR_GUEST_MODE);
        return guestMode != null && guestMode;
    }
    
    /**
     * Inicializa sesión de invitado.
     */
    public static void initGuestSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute(ATTR_GUEST_MODE, true);
        session.setAttribute(ATTR_USER_ROLE, UserType.GUEST);
    }
    
    /**
     * Inicializa sesión de usuario autenticado.
     */
    public static void initAuthenticatedSession(HttpServletRequest request, DtUser user) {
        // Invalidar sesión anterior (prevención session fixation)
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        
        // Crear nueva sesión
        HttpSession session = request.getSession(true);
        session.setAttribute(ATTR_LOGGED_USER, user);
        session.setAttribute(ATTR_USER_ROLE, user.getUserType());
        session.setAttribute(ATTR_GUEST_MODE, false);
    }
    
    /**
     * Invalida la sesión actual (logout).
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    
    /**
     * Verifica si el usuario tiene un rol específico.
     */
    public static boolean hasRole(HttpServletRequest request, UserType requiredRole) {
        UserType currentRole = getUserRole(request);
        return currentRole == requiredRole;
    }
    
    /**
     * Verifica si el usuario tiene alguno de los roles especificados.
     */
    public static boolean hasAnyRole(HttpServletRequest request, UserType... requiredRoles) {
        UserType currentRole = getUserRole(request);
        for (UserType role : requiredRoles) {
            if (currentRole == role) {
                return true;
            }
        }
        return false;
    }
}
```

---

## 🛡️ IMPLEMENTACIÓN DE FILTROS

### 1. SessionManagementFilter

**Ubicación**: `frontend/src/main/java/turismouyapp/filters/SessionManagementFilter.java`

```java
package turismouyapp.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import turismouyapp.security.SessionUtils;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Filtro de gestión de sesiones.
 * 
 * Responsabilidades:
 * - Asegurar que cada petición tenga una sesión HTTP
 * - Inicializar sesión de invitado si no hay usuario logueado
 * - Configurar atributos por defecto de sesión
 * 
 * @author Equipo TurismoUY
 * @version 2.0.0
 * @since Tarea 2 - 2025
 */
@WebFilter(
    filterName = "SessionManagementFilter",
    urlPatterns = {"/*"},
    dispatcherTypes = {DispatcherType.REQUEST}
)
public class SessionManagementFilter implements Filter {
    
    private static final Logger LOGGER = Logger.getLogger(SessionManagementFilter.class.getName());
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("[SessionManagementFilter] Inicializado correctamente");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        
        // Si no hay sesión, crear una con valores por defecto
        if (session == null) {
            LOGGER.fine("No hay sesión existente. Inicializando sesión de invitado.");
            SessionUtils.initGuestSession(httpRequest);
        } else {
            // Verificar coherencia: si hay sesión pero no hay user_role, establecer GUEST
            if (session.getAttribute(SessionUtils.ATTR_USER_ROLE) == null) {
                LOGGER.fine("Sesión sin user_role. Estableciendo modo invitado.");
                SessionUtils.initGuestSession(httpRequest);
            }
        }
        
        // Continuar cadena de filtros
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        LOGGER.info("[SessionManagementFilter] Destruido");
    }
}
```

### 2. AuthenticationFilter

**Ubicación**: `frontend/src/main/java/turismouyapp/filters/AuthenticationFilter.java`

```java
package turismouyapp.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import turismouyapp.security.AccessControlConfig;
import turismouyapp.security.SessionUtils;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Filtro de autenticación.
 * 
 * Verifica que el usuario esté autenticado para acceder a recursos protegidos.
 * Redirige a login si no está autenticado y el recurso lo requiere.
 * 
 * @author Equipo TurismoUY
 * @version 2.0.0
 * @since Tarea 2 - 2025
 */
@WebFilter(
    filterName = "AuthenticationFilter",
    urlPatterns = {"/*"},
    dispatcherTypes = {DispatcherType.REQUEST}
)
public class AuthenticationFilter implements Filter {
    
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("[AuthenticationFilter] Inicializado correctamente");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String resource = uri.substring(contextPath.length());
        
        LOGGER.fine(() -> String.format("[AuthenticationFilter] Verificando recurso: %s", resource));
        
        // Si es recurso público, continuar sin verificar autenticación
        if (AccessControlConfig.isPublicResource(resource)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Verificar si el usuario está autenticado
        boolean authenticated = SessionUtils.isAuthenticated(httpRequest);
        
        if (!authenticated) {
            LOGGER.info(() -> String.format(
                "[AuthenticationFilter] Usuario no autenticado intentando acceder a: %s. Redirigiendo a login.",
                resource
            ));
            
            // Guardar URL destino para redirección después del login
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("redirect_after_login", resource);
            
            // Redireccionar a login
            httpResponse.sendRedirect(contextPath + "/login?action=required");
            return;
        }
        
        // Usuario autenticado, continuar cadena
        LOGGER.fine(() -> String.format(
            "[AuthenticationFilter] Usuario autenticado. Permitiendo acceso a: %s",
            resource
        ));
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        LOGGER.info("[AuthenticationFilter] Destruido");
    }
}
```

### 3. AuthorizationFilter (RBAC)

**Ubicación**: `frontend/src/main/java/turismouyapp/filters/AuthorizationFilter.java`

```java
package turismouyapp.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import turismouyapp.core.dto.UserType;
import turismouyapp.security.AccessControlConfig;
import turismouyapp.security.SessionUtils;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Filtro de autorización basado en roles (RBAC).
 * 
 * Verifica que el usuario tenga los permisos necesarios para acceder al recurso.
 * 
 * @author Equipo TurismoUY
 * @version 2.0.0
 * @since Tarea 2 - 2025
 */
@WebFilter(
    filterName = "AuthorizationFilter",
    urlPatterns = {"/*"},
    dispatcherTypes = {DispatcherType.REQUEST}
)
public class AuthorizationFilter implements Filter {
    
    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class.getName());
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("[AuthorizationFilter] Inicializado correctamente");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String resource = uri.substring(contextPath.length());
        
        // Obtener rol del usuario
        UserType userRole = SessionUtils.getUserRole(httpRequest);
        
        LOGGER.fine(() -> String.format(
            "[AuthorizationFilter] Verificando permisos para rol %s en recurso: %s",
            userRole, resource
        ));
        
        // Verificar si tiene acceso
        boolean hasAccess = AccessControlConfig.hasAccess(resource, userRole);
        
        if (!hasAccess) {
            LOGGER.warning(() -> String.format(
                "[AuthorizationFilter] Acceso DENEGADO. Usuario con rol %s intentó acceder a: %s",
                userRole, resource
            ));
            
            // Determinar acción según el caso
            if (userRole == UserType.GUEST) {
                // Invitado: Redireccionar a login
                httpRequest.getSession().setAttribute("access_denied_message", 
                    "Debe iniciar sesión para acceder a este recurso.");
                httpResponse.sendRedirect(contextPath + "/login");
            } else {
                // Autenticado pero sin permisos: HTTP 403
                httpRequest.setAttribute("error_message", 
                    AccessControlConfig.getAccessDeniedMessage(userRole, resource));
                httpRequest.getRequestDispatcher("/WEB-INF/vistas/error403.jsp")
                    .forward(request, response);
            }
            return;
        }
        
        // Tiene acceso, continuar cadena
        LOGGER.fine(() -> String.format(
            "[AuthorizationFilter] Acceso PERMITIDO. Usuario con rol %s accedió a: %s",
            userRole, resource
        ));
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        LOGGER.info("[AuthorizationFilter] Destruido");
    }
}
```

### 4. CharacterEncodingFilter (Recomendado)

**Ubicación**: `frontend/src/main/java/turismouyapp/filters/CharacterEncodingFilter.java`

```java
package turismouyapp.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Filtro de codificación de caracteres.
 * 
 * Establece UTF-8 en todas las peticiones y respuestas para evitar
 * problemas con caracteres especiales (acentos, ñ, etc.).
 * 
 * @author Equipo TurismoUY
 * @version 2.0.0
 * @since Tarea 2 - 2025
 */
@WebFilter(
    filterName = "CharacterEncodingFilter",
    urlPatterns = {"/*"},
    dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE}
)
public class CharacterEncodingFilter implements Filter {
    
    private static final Logger LOGGER = Logger.getLogger(CharacterEncodingFilter.class.getName());
    private static final String ENCODING = "UTF-8";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("[CharacterEncodingFilter] Inicializado con encoding: " + ENCODING);
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Establecer encoding en request
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(ENCODING);
        }
        
        // Establecer encoding en response
        response.setCharacterEncoding(ENCODING);
        
        // Continuar cadena
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        LOGGER.info("[CharacterEncodingFilter] Destruido");
    }
}
```

---

## 🔄 FLUJOS DE AUTENTICACIÓN

### Flujo 1: Usuario Visitante (GUEST)

```
┌─────────────┐
│   Browser   │
└──────┬──────┘
       │ GET /home
       ▼
┌──────────────────────────┐
│ SessionManagementFilter  │
│ - Crea sesión            │
│ - guest_mode = true      │
│ - user_role = GUEST      │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────┐
│ AuthenticationFilter     │
│ - /home es público       │
│ - Continuar              │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────┐
│ AuthorizationFilter      │
│ - GUEST puede acceder    │
│ - Continuar              │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────┐
│ Servlet: home            │
│ - Renderizar home.jsp    │
└──────┬───────────────────┘
       │
       ▼
┌─────────────┐
│ Respuesta   │
│ (home.jsp)  │
└─────────────┘
```

### Flujo 2: Inicio de Sesión

```
┌─────────────┐
│   Browser   │
└──────┬──────┘
       │ POST /login
       │ nickname=juan&password=123
       ▼
┌──────────────────────────┐
│ SessionManagementFilter  │
│ - Sesión existe (GUEST)  │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────┐
│ AuthenticationFilter     │
│ - /login es público      │
│ - Continuar              │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────┐
│ Servlet: Login           │
│ - Validar credenciales   │
│ - Obtener DtUser         │
│ - Invalidar sesión vieja │
│ - Crear nueva sesión:    │
│   * logged_user = user   │
│   * user_role = TOURIST  │
│   * guest_mode = false   │
└──────┬───────────────────┘
       │
       ▼ redirect /home
┌──────────────────────────┐
│ Nueva petición GET /home │
│ - user_role = TOURIST    │
└──────┬───────────────────┘
       │
       ▼
┌─────────────┐
│ Respuesta   │
│ (home.jsp)  │
└─────────────┘
```

### Flujo 3: Acceso Denegado (GUEST intenta inscribirse)

```
┌─────────────┐
│   Browser   │
└──────┬──────┘
       │ GET /inscriptions
       │ (sin login)
       ▼
┌──────────────────────────┐
│ SessionManagementFilter  │
│ - user_role = GUEST      │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────┐
│ AuthenticationFilter     │
│ - /inscriptions NO público│
│ - Usuario NO autenticado │
│ - Guardar redirect_after_login│
│ - Redirigir a /login     │
└──────┬───────────────────┘
       │
       ▼
┌─────────────┐
│   Browser   │
│ /login      │
└─────────────┘
```

### Flujo 4: Acceso Denegado (TOURIST intenta crear actividad)

```
┌─────────────┐
│   Browser   │
└──────┬──────┘
       │ POST /create-activity
       │ (logueado como TOURIST)
       ▼
┌──────────────────────────┐
│ SessionManagementFilter  │
│ - user_role = TOURIST    │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────┐
│ AuthenticationFilter     │
│ - Usuario autenticado ✓  │
│ - Continuar              │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────┐
│ AuthorizationFilter      │
│ - TOURIST NO puede crear │
│ - HTTP 403 Forbidden     │
└──────┬───────────────────┘
       │
       ▼
┌─────────────┐
│ error403.jsp│
└─────────────┘
```

### Flujo 5: Cierre de Sesión

```
┌─────────────┐
│   Browser   │
└──────┬──────┘
       │ GET /logout
       │ (autenticado)
       ▼
┌──────────────────────────┐
│ SessionManagementFilter  │
│ - Sesión existe          │
└──────┬───────────────────┘
       │
       ▼
┌──────────────────────────┐
│ Servlet: Logout          │
│ - Invalidar sesión       │
│ - session.invalidate()   │
└──────┬───────────────────┘
       │
       ▼ redirect /login
┌──────────────────────────┐
│ Nueva petición GET /login│
│ - SessionManagement crea │
│   nueva sesión GUEST     │
└──────┬───────────────────┘
       │
       ▼
┌─────────────┐
│ login.jsp   │
└─────────────┘
```

---

## ⚙️ CONFIGURACIÓN Y DESPLIEGUE

### 1. web.xml (Opcional - ya que usamos anotaciones)

**Ubicación**: `frontend/src/main/webapp/WEB-INF/web.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
         xmlns="https://jakarta.ee/xml/ns/jakartaee" 
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee 
                             https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd" 
         id="WebApp_ID" version="5.0">
         
  <display-name>turismouy.UI</display-name>
  
  <!-- Página de bienvenida -->
  <welcome-file-list>
    <welcome-file>login</welcome-file>
  </welcome-file-list>
  
  <!-- Context params existentes -->
  <context-param>
    <param-name>uploadProfileFolder</param-name>
    <param-value>${catalina.base}/data/turismouy.UI/profile_img</param-value>
  </context-param>
  
  <context-param>
    <param-name>uploadActivityFolder</param-name>
    <param-value>${catalina.base}/data/turismouy.UI/activity_img</param-value>
  </context-param>
  
  <context-param>
    <param-name>defaultPath_img</param-name>
    <param-value>${catalina.base}/data/turismouy.UI/res</param-value>
  </context-param>
  
  <!-- Configuración de sesión -->
  <session-config>
    <!-- Timeout de 30 minutos -->
    <session-timeout>30</session-timeout>
    <!-- Cookies HTTP-only (prevención XSS) -->
    <cookie-config>
      <http-only>true</http-only>
      <!-- En producción, habilitar secure para HTTPS -->
      <!-- <secure>true</secure> -->
      <max-age>1800</max-age> <!-- 30 minutos -->
    </cookie-config>
    <!-- Tracking mode solo con cookies (no URL rewriting) -->
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
  
  <!-- Security constraints (opcional - ya manejado por filtros) -->
  <!-- 
  <security-constraint>
    <web-resource-collection>
      <web-resource-name>Recursos Protegidos</web-resource-name>
      <url-pattern>/inscriptions</url-pattern>
      <url-pattern>/modify-data-user</url-pattern>
    </web-resource-collection>
    <auth-constraint>
      <role-name>TOURIST</role-name>
      <role-name>SUPPLIER</role-name>
    </auth-constraint>
  </security-constraint>
  -->
  
</web-app>
```

### 2. Orden de Ejecución de Filtros

Por defecto, los filtros se ejecutan en orden alfabético de clase si no se especifica `@WebFilter(order=...)`. Para garantizar el orden correcto:

**Opción 1: Renombrar clases con prefijos numéricos**
```
01_CharacterEncodingFilter.java
02_SessionManagementFilter.java
03_AuthenticationFilter.java
04_AuthorizationFilter.java
```

**Opción 2: Configurar en web.xml explícitamente**
```xml
<filter>
  <filter-name>CharacterEncodingFilter</filter-name>
  <filter-class>turismouyapp.filters.CharacterEncodingFilter</filter-class>
</filter>
<filter-mapping>
  <filter-name>CharacterEncodingFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>

<filter>
  <filter-name>SessionManagementFilter</filter-name>
  <filter-class>turismouyapp.filters.SessionManagementFilter</filter-class>
</filter>
<filter-mapping>
  <filter-name>SessionManagementFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>

<!-- ... continuar con el resto -->
```

### 3. Estructura de Directorios Final

```
frontend/src/main/java/turismouyapp/
├── servlets/
│   ├── Login.java (modificar para usar SessionUtils)
│   ├── Logout.java (modificar)
│   ├── home.java
│   ├── Activities.java
│   ├── Outings.java
│   ├── Inscriptions.java (requiere TOURIST)
│   ├── ConsultUser.java
│   └── ModifyDataUser.java (requiere autenticación)
├── filters/ (NUEVO)
│   ├── CharacterEncodingFilter.java
│   ├── SessionManagementFilter.java
│   ├── AuthenticationFilter.java
│   └── AuthorizationFilter.java
└── security/ (NUEVO)
    ├── AccessControlConfig.java
    └── SessionUtils.java
```

---

## 📝 CASOS DE USO Y EJEMPLOS

### Caso 1: Visitante Navega el Sitio

**Escenario**: Usuario entra al sitio sin autenticarse

**URL**: `http://localhost:8080/turismouy.UI/home`

**Flujo**:
1. SessionManagementFilter crea sesión con `guest_mode=true`, `user_role=GUEST`
2. AuthenticationFilter verifica que `/home` es público → continúa
3. AuthorizationFilter verifica que GUEST puede acceder → continúa
4. Servlet `home` renderiza página con menú limitado para invitados
5. JSP muestra botón "Iniciar Sesión" prominente

**Atributos de Sesión**:
```java
guest_mode = true
user_role = GUEST
logged_user = null
```

### Caso 2: Turista Intenta Inscribirse sin Login

**Escenario**: Usuario hace clic en "Inscribirme" sin estar autenticado

**URL**: `http://localhost:8080/turismouy.UI/inscriptions?outing=12`

**Flujo**:
1. SessionManagementFilter mantiene sesión GUEST
2. AuthenticationFilter detecta que `/inscriptions` NO es público
3. Usuario NO autenticado → guarda `redirect_after_login=/inscriptions?outing=12`
4. Redirecciona a `/login?action=required`
5. JSP muestra mensaje: "Debe iniciar sesión para inscribirse"

### Caso 3: Login Exitoso y Redirección

**Escenario**: Usuario se loguea después del caso anterior

**POST** `/login`
```
nickname=juan
password=123456
```

**Flujo**:
1. Servlet Login valida credenciales
2. `SessionUtils.initAuthenticatedSession()`:
   - Invalida sesión anterior
   - Crea nueva sesión
   - `logged_user = DtTourist(juan, ...)`
   - `user_role = TOURIST`
   - `guest_mode = false`
3. Verifica si hay `redirect_after_login` en sesión
4. Redirecciona a `/inscriptions?outing=12`
5. Ahora pasa todos los filtros y puede inscribirse

### Caso 4: Proveedor Crea Actividad

**Escenario**: Usuario autenticado como SUPPLIER crea actividad

**POST** `/activities?action=create`

**Flujo**:
1. SessionManagementFilter mantiene sesión SUPPLIER
2. AuthenticationFilter verifica autenticación ✓
3. AuthorizationFilter verifica que SUPPLIER puede crear actividades ✓
4. Servlet `Activities` procesa formulario
5. Llama a `FactoryUyTourism.getITouristActivityController().createActivity(...)`
6. Actividad se crea en estado "Agregada" (pendiente aprobación)
7. Redirecciona a perfil del proveedor mostrando actividad nueva

### Caso 5: Turista Intenta Crear Actividad (Acceso Denegado)

**Escenario**: Usuario autenticado como TOURIST intenta acceder a formulario de creación

**GET** `/activities?action=showCreateForm`

**Flujo**:
1. SessionManagementFilter mantiene sesión TOURIST
2. AuthenticationFilter verifica autenticación ✓
3. AuthorizationFilter verifica permisos:
   - TOURIST NO está en `SUPPLIER_ONLY_RESOURCES`
   - Acceso DENEGADO
4. Forward a `/WEB-INF/vistas/error403.jsp`
5. Muestra mensaje: "No tiene permisos para acceder a este recurso"

### Caso 6: Modificar Datos Propios (Autenticado)

**Escenario**: Usuario autenticado modifica su perfil

**GET** `/modify-data-user`

**Flujo**:
1. Filters verifican autenticación ✓
2. Servlet `ModifyDataUser` verifica que edita su propio perfil:
```java
DtUser loggedUser = SessionUtils.getLoggedUser(request);
String nicknameToEdit = request.getParameter("nickname");

if (!loggedUser.getNickname().equals(nicknameToEdit)) {
    // Intentando editar perfil ajeno
    response.sendError(HttpServletResponse.SC_FORBIDDEN);
    return;
}
```
3. Permite edición de datos (excepto nickname/email)
4. Actualiza en backend y sesión

### Caso 7: Logout

**Escenario**: Usuario cierra sesión

**GET** `/logout`

**Flujo**:
1. Servlet `Logout` llama `SessionUtils.invalidateSession(request)`
2. `session.invalidate()` destruye sesión completa
3. Redirecciona a `/login`
4. SessionManagementFilter crea nueva sesión GUEST
5. Muestra mensaje: "Sesión cerrada correctamente"

---

## 🧪 TESTING Y VALIDACIÓN

### Tests de Integración Sugeridos

```java
package turismouyapp.filters;

import org.junit.jupiter.api.*;
import org.mockito.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import turismouyapp.core.dto.UserType;
import turismouyapp.security.SessionUtils;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthorizationFilterTest {
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Mock
    private FilterChain chain;
    
    @Mock
    private HttpSession session;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getContextPath()).thenReturn("/turismouy.UI");
    }
    
    @Test
    void testGuestCanAccessPublicResource() throws Exception {
        // Arrange
        when(request.getRequestURI()).thenReturn("/turismouy.UI/home");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(SessionUtils.ATTR_USER_ROLE)).thenReturn(UserType.GUEST);
        
        AuthorizationFilter filter = new AuthorizationFilter();
        
        // Act
        filter.doFilter(request, response, chain);
        
        // Assert
        verify(chain).doFilter(request, response);
    }
    
    @Test
    void testGuestCannotAccessInscriptions() throws Exception {
        // Arrange
        when(request.getRequestURI()).thenReturn("/turismouy.UI/inscriptions");
        when(request.getSession(false)).thenReturn(session);
        when(request.getSession(true)).thenReturn(session);
        when(session.getAttribute(SessionUtils.ATTR_USER_ROLE)).thenReturn(UserType.GUEST);
        
        AuthorizationFilter filter = new AuthorizationFilter();
        
        // Act
        filter.doFilter(request, response, chain);
        
        // Assert
        verify(response).sendRedirect(anyString());
        verify(chain, never()).doFilter(request, response);
    }
    
    @Test
    void testTouristCanAccessInscriptions() throws Exception {
        // Arrange
        when(request.getRequestURI()).thenReturn("/turismouy.UI/inscriptions");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(SessionUtils.ATTR_USER_ROLE)).thenReturn(UserType.TOURIST);
        
        AuthorizationFilter filter = new AuthorizationFilter();
        
        // Act
        filter.doFilter(request, response, chain);
        
        // Assert
        verify(chain).doFilter(request, response);
    }
}
```

---

## 🔒 CONSIDERACIONES DE SEGURIDAD

### 1. Session Fixation Prevention
✅ **Implementado** en `SessionUtils.initAuthenticatedSession()`:
```java
HttpSession oldSession = request.getSession(false);
if (oldSession != null) {
    oldSession.invalidate();
}
HttpSession session = request.getSession(true); // Nueva sesión
```

### 2. CSRF Protection (Recomendado - NO implementado aún)
⚠️ **Pendiente**: Agregar tokens CSRF en formularios

```jsp
<input type="hidden" name="csrf_token" value="${sessionScope.csrf_token}">
```

### 3. XSS Prevention
✅ **Parcialmente implementado**:
- `http-only` cookies en `web.xml`
- JSTL escapa HTML por defecto en `<c:out>`

⚠️ **Recomendación**: Validar entrada de usuario en servlets

### 4. Password Hashing
⚠️ **CRÍTICO - NO implementado**: Contraseñas en texto plano

**Acción requerida**: Implementar BCrypt
```java
import org.mindrot.jbcrypt.BCrypt;

// Al registrar
String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));

// Al validar
if (BCrypt.checkpw(inputPassword, storedHash)) {
    // Login exitoso
}
```

### 5. HTTPS en Producción
⚠️ **Recomendación**: Forzar HTTPS en producción
```xml
<security-constraint>
  <web-resource-collection>
    <web-resource-name>All</web-resource-name>
    <url-pattern>/*</url-pattern>
  </web-resource-collection>
  <user-data-constraint>
    <transport-guarantee>CONFIDENTIAL</transport-guarantee>
  </user-data-constraint>
</security-constraint>
```

---

## 📈 ROADMAP Y MEJORAS FUTURAS

### Fase 1: Implementación Básica (Tarea 2) ✅
- [x] Filtros de sesión, autenticación y autorización
- [x] Roles GUEST, TOURIST, SUPPLIER
- [x] Redirección según permisos
- [x] SessionUtils y AccessControlConfig

### Fase 2: Seguridad Avanzada (Tarea 3 / Post-Tarea)
- [ ] Hashing de contraseñas con BCrypt
- [ ] CSRF tokens en formularios
- [ ] Rate limiting para login
- [ ] Auditoría de accesos (logging)

### Fase 3: Optimización (Futuro)
- [ ] Caché de permisos en sesión
- [ ] Roles dinámicos desde BD
- [ ] JWT para API REST (si se implementa)
- [ ] Remember me functionality

---

## 📚 REFERENCIAS

- [Jakarta Servlet 6.0 Specification](https://jakarta.ee/specifications/servlet/6.0/)
- [Java EE Security Best Practices](https://owasp.org/www-project-web-security-testing-guide/)
- [OWASP Session Management Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Session_Management_Cheat_Sheet.html)
- [Tomcat Security How-To](https://tomcat.apache.org/tomcat-11.0-doc/security-howto.html)

---

## ✅ CHECKLIST DE IMPLEMENTACIÓN

### Backend
- [ ] Verificar que `UserType` enum existe con GUEST, TOURIST, SUPPLIER
- [ ] Asegurar que `DtUser.getUserType()` retorna correctamente

### Frontend - Nuevos Componentes
- [ ] Crear paquete `turismouyapp.security`
- [ ] Crear `SessionUtils.java`
- [ ] Crear `AccessControlConfig.java`
- [ ] Crear paquete `turismouyapp.filters`
- [ ] Crear `CharacterEncodingFilter.java`
- [ ] Crear `SessionManagementFilter.java`
- [ ] Crear `AuthenticationFilter.java`
- [ ] Crear `AuthorizationFilter.java`

### Frontend - Modificar Servlets Existentes
- [ ] Modificar `Login.java` para usar `SessionUtils`
- [ ] Crear/modificar `Logout.java`
- [ ] Agregar verificación de roles en `Activities.java`
- [ ] Agregar verificación de roles en `Inscriptions.java`
- [ ] Agregar verificación en `ModifyDataUser.java`

### Frontend - JSP
- [ ] Crear `error403.jsp`
- [ ] Crear `error404.jsp`
- [ ] Crear `error500.jsp`
- [ ] Modificar `header.jsp` para mostrar/ocultar según rol
- [ ] Agregar mensaje de bienvenida con nombre de usuario

### Configuración
- [ ] Actualizar `web.xml` con configuración de sesión
- [ ] Verificar orden de filtros
- [ ] Configurar páginas de error

### Testing
- [ ] Test: Visitante puede acceder a home
- [ ] Test: Visitante no puede inscribirse
- [ ] Test: Login crea sesión correctamente
- [ ] Test: Logout invalida sesión
- [ ] Test: TOURIST puede inscribirse
- [ ] Test: TOURIST no puede crear actividades
- [ ] Test: SUPPLIER puede crear actividades

---

**Documento creado por:** Equipo TurismoUY  
**Versión:** 2.0.0  
**Fecha:** Octubre 2025  
**Estado:** ✅ Diseño Completo - Listo para Implementación

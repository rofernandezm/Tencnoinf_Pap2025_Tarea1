# Sistema de Autenticación y Control de Acceso

## Índice

1. [Introducción](#introducción)
2. [Arquitectura General](#arquitectura-general)
3. [Roles y Permisos](#roles-y-permisos)
4. [Flujos de Usuario](#flujos-de-usuario)
5. [Implementación del Filter](#implementación-del-filter)
6. [Gestión de Sesiones](#gestión-de-sesiones)
7. [Interfaz de Usuario Adaptativa](#interfaz-de-usuario-adaptativa)
8. [Patrones de Navegación](#patrones-de-navegación)
9. [Matriz de Control de Acceso](#matriz-de-control-de-acceso)

---

## Introducción

Este documento describe la implementación de un sistema de autenticación y autorización basado en roles para aplicaciones **Java Web (JSP/Servlet)**. El sistema permite tres niveles de acceso diferenciados con permisos específicos para cada tipo de usuario.

### Características principales

✅ **Autenticación basada en sesiones HTTP**
- Gestión segura mediante `HttpSession`
- Soporte para múltiples tipos de usuario
- Modo invitado sin registro

✅ **Control de acceso centralizado**
- Filtro de autenticación que intercepta todas las peticiones
- Validación de permisos por URL y rol
- Redirección automática según estado de sesión

✅ **Interfaz adaptativa**
- UI condicional mediante JSTL
- Navegación dinámica según permisos
- Feedback visual del rol activo

✅ **Patrones de seguridad**
- Post-Redirect-Get (PRG) para evitar reenvío de formularios
- Separación entre URLs públicas, protegidas y por rol
- Timeout de sesión configurable

---

## Arquitectura General

El sistema se compone de tres capas principales:

```text
┌─────────────────────────────────────────────────────────────┐
│                    CLIENTE (Navegador)                      │
└────────────────────────┬────────────────────────────────────┘
                         │ HTTP Request
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                   SERVIDOR (Tomcat)                         │
├─────────────────────────────────────────────────────────────┤
│  [1] FILTER: AuthenticationFilter                           │
│      • Valida sesión y permisos                             │
│      • Clasifica URLs (públicas/protegidas/por rol)         │
│      • Redirige si no autorizado                            │
│                         │                                   │
│  [2] SERVLET: Login / ModifyUser / etc.                     │
│      • Procesa la lógica de negocio                         │
│      • Manipula atributos de sesión                         │
│                         │                                   │
│  [3] JSP: Vista con JSTL                                    │
│      • Renderiza HTML dinámico                              │
│      • Muestra/oculta según rol                             │
└────────────────────────┬────────────────────────────────────┘
                         │ HTTP Response
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                    CLIENTE (Navegador)                      │
└─────────────────────────────────────────────────────────────┘
```

### Flujo de una petición

1. **Request llega al servidor** → Filter intercepta
2. **Validación de sesión** → Verifica `logged_user` o `guest_mode`
3. **Autorización por URL** → Comprueba si el usuario puede acceder
4. **Procesamiento** → Servlet ejecuta lógica si está autorizado
5. **Respuesta** → JSP renderiza vista adaptada al rol

---

## Roles y Permisos

El sistema soporta tres tipos de acceso:

### 👤 Invitado (Guest)

**Características:**
- Acceso temporal sin registro
- Sesión ligera: `guest_mode=true`, `user_role="GUEST"`
- **NO** tiene objeto `logged_user` en sesión
- Timeout de sesión: **10 minutos**

**Permisos:**
- ✅ Ver página de inicio
- ✅ Consultar actividades turísticas
- ✅ Consultar salidas turísticas
- ✅ Ver ranking de actividades
- ❌ Inscribirse a salidas
- ❌ Modificar perfil
- ❌ Crear contenido

### 🏖️ Turista (Tourist)

**Características:**
- Usuario registrado y autenticado
- Sesión completa: `logged_user` (tipo `DtTourist`) + `userType=TOURIST`
- Timeout de sesión: **5 minutos** (extendido por actividad)

**Permisos:**
- ✅ **Todos los permisos de Invitado**
- ✅ Inscribirse a salidas turísticas
- ✅ Ver mis inscripciones
- ✅ Modificar mi perfil
- ✅ Subir foto de perfil
- ❌ Crear actividades o salidas

### 🏢 Proveedor (Supplier)

**Características:**
- Usuario registrado con permisos de gestión
- Sesión completa: `logged_user` (tipo `DtSupplier`) + `userType=SUPPLIER`
- Timeout de sesión: **5 minutos** (extendido por actividad)

**Permisos:**
- ✅ **Permisos de consulta de Invitado**
- ✅ Crear actividades turísticas
- ✅ Crear salidas turísticas
- ✅ Ver mis actividades
- ✅ Modificar datos de actividades/salidas
- ✅ Modificar mi perfil
- ❌ Inscribirse a salidas (no aplica)

### Atributos de sesión por rol

| Atributo | Invitado | Turista | Proveedor |
|----------|----------|---------|-----------|
| `guest_mode` | `true` | — | — |
| `user_role` | `"GUEST"` | — | — |
| `logged_user` | `null` | `DtTourist` | `DtSupplier` |
| `userType` | — | `TOURIST` | `SUPPLIER` |
| Timeout | 600s (10 min) | 300s (5 min) | 300s (5 min) |

---

## Flujos de Usuario

### Flujo: Invitado

```text
┌─────────────────────────────────────────────────────────────┐
│                    FLUJO GUEST                              │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1. Usuario hace clic en "Ingresar como invitado"          │
│                                                             │
│  2. handleGuestLogin() ejecuta:                             │
│     • session.setAttribute("guest_mode", true)              │
│     • session.setAttribute("user_role", "GUEST")            │
│     • session.setMaxInactiveInterval(600)                   │
│                                                             │
│  3. Redirect a /home (PRG pattern)                          │
│                                                             │
│  4. AuthenticationFilter permite acceso a:                  │
│     ✅ /home                                                │
│     ✅ /consult-activities                                  │
│     ✅ /consult-outings                                     │
│     ✅ /activity-ranking                                    │
│                                                             │
│  5. Navbar muestra:                                         │
│     • Badge "👤 Invitado"                                   │
│     • Botón "Iniciar Sesión"                                │
│     • Enlaces públicos                                      │
│                                                             │
│  6. Si intenta acceder a URL protegida:                     │
│     ❌ Filter redirige a /login                             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Flujo: Turista

```text
┌─────────────────────────────────────────────────────────────┐
│                    FLUJO TOURIST                            │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1. Usuario ingresa credenciales en formulario              │
│                                                             │
│  2. handleLogin() valida y ejecuta:                         │
│     • session.setAttribute("logged_user", dtTourist)        │
│     • session.setMaxInactiveInterval(300)                   │
│                                                             │
│  3. Redirect a /home (PRG pattern)                          │
│                                                             │
│  4. AuthenticationFilter permite acceso a:                  │
│     ✅ URLs públicas                                        │
│     ✅ URLs autenticadas                                    │
│     ✅ URLs específicas de TOURIST                          │
│     ❌ URLs específicas de SUPPLIER                         │
│                                                             │
│  5. Navbar muestra:                                         │
│     • "🏖️ Turista: [nombre]"                               │
│     • "Mis Inscripciones"                                   │
│     • "Inscribirse"                                         │
│     • "Mi Perfil"                                           │
│     • "Cerrar Sesión"                                       │
│                                                             │
│  6. Puede realizar:                                         │
│     ✅ Inscribirse a salidas                                │
│     ✅ Consultar sus inscripciones                          │
│     ✅ Modificar su perfil                                  │
│     ❌ Crear actividades (denegado con 403)                 │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Flujo: Proveedor

```text
┌─────────────────────────────────────────────────────────────┐
│                    FLUJO SUPPLIER                           │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1. Usuario ingresa credenciales en formulario              │
│                                                             │
│  2. handleLogin() valida y ejecuta:                         │
│     • session.setAttribute("logged_user", dtSupplier)       │
│     • session.setMaxInactiveInterval(1800)                  │
│                                                             │
│  3. Redirect a /home (PRG pattern)                          │
│                                                             │
│  4. AuthenticationFilter permite acceso a:                  │
│     ✅ URLs públicas                                        │
│     ✅ URLs autenticadas                                    │
│     ✅ URLs específicas de SUPPLIER                         │
│     ❌ URLs específicas de TOURIST                          │
│                                                             │
│  5. Navbar muestra:                                         │
│     • "🏢 Proveedor: [nombre]"                              │
│     • "Mis Actividades"                                     │
│     • "Crear Actividad"                                     │
│     • "Crear Salida"                                        │
│     • "Mi Perfil"                                           │
│     • "Cerrar Sesión"                                       │
│                                                             │
│  6. Puede realizar:                                         │
│     ✅ Crear actividades turísticas                         │
│     ✅ Crear salidas turísticas                             │
│     ✅ Gestionar su contenido                               │
│     ❌ Inscribirse a salidas (denegado con 403)             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## Implementación del Filter

### Estructura del AuthenticationFilter

El filtro intercepta **todas las peticiones** (`@WebFilter("/*")`) y clasifica las URLs en categorías para aplicar la política de acceso correspondiente.

```java
@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    
    // URLs accesibles sin autenticación
    private static final List<String> PUBLIC_URLS = Arrays.asList(
        "/login", "/register", "/guest-login",
        "/IniciarSesionRegistrarse.jsp",
        "/AccedeAlHome.jsp",
        "/CerrarSesion.jsp",
        "/css/", "/js/", "/images/", "/uploads/", "/favicon.ico"
    );
    
    // URLs accesibles para invitados y autenticados
    private static final List<String> GUEST_URLS = Arrays.asList(
        "/home", "/consult-activities", "/consult-outings", 
        "/activity-ranking", "/consult-user"
    );
    
    // URLs que requieren autenticación (no guest)
    private static final List<String> AUTHENTICATED_URLS = Arrays.asList(
        "/profile", "/modify-data-user", "/logout"
    );
    
    // URLs exclusivas para TOURIST
    private static final List<String> TOURIST_ONLY_URLS = Arrays.asList(
        "/my-inscriptions", "/inscription-tourist-outing"
    );
    
    // URLs exclusivas para SUPPLIER
    private static final List<String> SUPPLIER_ONLY_URLS = Arrays.asList(
        "/my-activities", "/create-activity", "/create-tourist-outing"
    );
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getRequestURI().substring(request.getContextPath().length());
        
        // Obtener estado de sesión
        HttpSession session = request.getSession(false);
        DtUser loggedUser = (session != null) 
            ? (DtUser) session.getAttribute("logged_user") 
            : null;
        boolean isGuest = (session != null) 
            && (session.getAttribute("guest_mode") != null);
        
        // 1. URLs públicas: acceso sin restricción
        if (matchesAny(path, PUBLIC_URLS)) {
            chain.doFilter(request, response);
            return;
        }
        
        // 2. URLs guest: invitados o autenticados
        if (matchesAny(path, GUEST_URLS)) {
            if (loggedUser != null || isGuest) {
                chain.doFilter(request, response);
                return;
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }
        
        // 3. URLs autenticadas: solo usuarios registrados
        if (matchesAny(path, AUTHENTICATED_URLS)) {
            if (loggedUser == null) {
                request.setAttribute("loginError", 
                    "Debes iniciar sesión para acceder a esta página");
                request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp")
                    .forward(request, response);
                return;
            }
            chain.doFilter(request, response);
            return;
        }
        
        // 4. URLs exclusivas para TOURIST
        if (matchesAny(path, TOURIST_ONLY_URLS)) {
            if (loggedUser == null) {
                request.setAttribute("loginError", 
                    "Debes ser un turista registrado para realizar esta acción");
                request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp")
                    .forward(request, response);
                return;
            }
            if (loggedUser.getUserType() != UserType.TOURIST) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, 
                    "Esta funcionalidad es solo para turistas");
                return;
            }
            chain.doFilter(request, response);
            return;
        }
        
        // 5. URLs exclusivas para SUPPLIER
        if (matchesAny(path, SUPPLIER_ONLY_URLS)) {
            if (loggedUser == null) {
                request.setAttribute("loginError", 
                    "Debes ser un proveedor registrado para realizar esta acción");
                request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp")
                    .forward(request, response);
                return;
            }
            if (loggedUser.getUserType() != UserType.SUPPLIER) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, 
                    "Esta funcionalidad es solo para proveedores");
                return;
            }
            chain.doFilter(request, response);
            return;
        }
        
        // 6. URLs sin categoría: requiere autenticación o guest
        if (loggedUser == null && !isGuest) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    // Método utilitario para verificar coincidencia de paths
    private boolean matchesAny(String path, List<String> patterns) {
        return patterns.stream().anyMatch(pattern -> {
            if (pattern.endsWith("/")) {
                return path.startsWith(pattern);
            } else {
                return path.equals(pattern);
            }
        });
    }
}
```

### Lógica de decisión

```text
┌─────────────────────────────────────────────────────────────┐
│              DIAGRAMA DE DECISIÓN DEL FILTER                │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Request → ¿URL pública?                                    │
│            ├─ SÍ → Permitir (chain.doFilter)                │
│            └─ NO ↓                                          │
│                                                             │
│            ¿URL guest?                                      │
│            ├─ SÍ → ¿logged_user o guest_mode?               │
│            │       ├─ SÍ → Permitir                         │
│            │       └─ NO → Redirect /login                  │
│            └─ NO ↓                                          │
│                                                             │
│            ¿URL autenticada?                                │
│            ├─ SÍ → ¿logged_user existe?                     │
│            │       ├─ SÍ → Permitir                         │
│            │       └─ NO → Forward /login con error         │
│            └─ NO ↓                                          │
│                                                             │
│            ¿URL tourist-only?                               │
│            ├─ SÍ → ¿logged_user y userType=TOURIST?         │
│            │       ├─ SÍ → Permitir                         │
│            │       └─ NO → 403 Forbidden                    │
│            └─ NO ↓                                          │
│                                                             │
│            ¿URL supplier-only?                              │
│            ├─ SÍ → ¿logged_user y userType=SUPPLIER?        │
│            │       ├─ SÍ → Permitir                         │
│            │       └─ NO → 403 Forbidden                    │
│            └─ NO ↓                                          │
│                                                             │
│            Por defecto:                                     │
│            ¿logged_user o guest_mode?                       │
│            ├─ SÍ → Permitir                                 │
│            └─ NO → Redirect /login                          │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## Gestión de Sesiones

### Inicio de sesión como Invitado

```java
protected void handleGuestLogin(HttpServletRequest request, 
                                HttpServletResponse response)
        throws ServletException, IOException {
    
    // Crear sesión nueva
    HttpSession session = request.getSession(true);
    
    // Marcar como invitado
    session.setAttribute("guest_mode", true);
    session.setAttribute("user_role", "GUEST");
    
    // NO establecer logged_user (distingue de usuarios autenticados)
    
    // Timeout corto: 10 minutos
    session.setMaxInactiveInterval(600);
    
    System.out.println(">>> Usuario ingresó como invitado");
    
    // Redirect para evitar reenvío de formulario (PRG pattern)
    response.sendRedirect(request.getContextPath() + "/home");
}
```

### Inicio de sesión autenticado

```java
protected void handleLogin(HttpServletRequest request, 
                           HttpServletResponse response)
        throws ServletException, IOException {
    
    String nickname = request.getParameter("nickname");
    String password = request.getParameter("password");
    
    // Validación de credenciales
    IUserController userController = factory.getUserController();
    DtUser dtUser = userController.findUser(nickname);
    
    if (dtUser != null && dtUser.getPassword().equals(password)) {
        // Autenticación exitosa
        
        // Invalidar sesión anterior si existe (seguridad)
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        
        // Crear NUEVA sesión → genera nuevo JSESSIONID
        HttpSession session = request.getSession(true);
        session.setAttribute("logged_user", dtUser);
        
        // Timeout: 5 minutos para todos los usuarios autenticados
        session.setMaxInactiveInterval(300); // 5 min
        
        System.out.println(">>> Login exitoso: " + nickname + 
                         " | Nueva sesión: " + session.getId());
        
        // Redirect (PRG pattern)
        // La respuesta incluye Set-Cookie con el nuevo JSESSIONID
        response.sendRedirect(request.getContextPath() + "/home");
    } else {
        // Autenticación fallida
        request.setAttribute("loginError", "Credenciales inválidas");
        request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp")
            .forward(request, response);
    }
}
```

**¿Qué sucede con la cookie?**

1. **Si existía sesión previa** (por ejemplo, de invitado):
   - `oldSession.invalidate()` destruye la sesión anterior
   - El servidor marca el JSESSIONID antiguo como inválido
   
2. **Creación de nueva sesión:**
   - `getSession(true)` crea una nueva sesión con **nuevo ID**
   - Tomcat genera un nuevo JSESSIONID (ej: `3F2504E0-4F89-11D3-9A0C-0305E82C3301`)

3. **Respuesta HTTP incluye:**
   ```http
   HTTP/1.1 302 Found
   Location: /turismouy/home
   Set-Cookie: JSESSIONID=3F2504E0-4F89-11D3-9A0C-0305E82C3301; 
               Path=/turismouy; 
               HttpOnly
   ```

4. **El navegador:**
   - Reemplaza el JSESSIONID antiguo con el nuevo
   - Lo envía en todas las peticiones subsiguientes

**Beneficios de seguridad:**
- ✅ Previene **Session Fixation attacks** (atacante no puede predecir el ID)
- ✅ Cada login genera ID único y aleatorio
- ✅ Sesiones de invitado se invalidan al hacer login real

### Cierre de sesión

```java
protected void handleLogout(HttpServletRequest request, 
                            HttpServletResponse response)
        throws ServletException, IOException {
    
    HttpSession session = request.getSession(false);
    if (session != null) {
        System.out.println(">>> Cerrando sesión de: " 
            + session.getAttribute("logged_user"));
        session.invalidate();
    }
    
    response.sendRedirect(request.getContextPath() + "/login");
}
```

### Atributos de sesión

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `logged_user` | `DtUser` (DtTourist o DtSupplier) | Usuario autenticado. Si es `null`, no hay login |
| `guest_mode` | `Boolean` | Indica si la sesión es de invitado |
| `user_role` | `String` | Valor "GUEST" para invitados |

**Importante:**
- `logged_user` es `null` para invitados → permite distinguirlos
- `guest_mode` es `true` solo para invitados
- Usuarios autenticados **nunca** tienen `guest_mode`

---

### Ciclo de vida de la Cookie JSESSIONID

#### Escenario 1: Usuario entra como invitado → Login

```text
┌─────────────────────────────────────────────────────────────┐
│         RENOVACIÓN DE COOKIE: GUEST → AUTHENTICATED         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  [1] Usuario hace clic "Ingresar como invitado"             │
│      ↓                                                      │
│      GET /guest-login                                       │
│      ↓                                                      │
│      handleGuestLogin() ejecuta:                            │
│      • session = request.getSession(true)  // crea sesión   │
│      • session.setAttribute("guest_mode", true)             │
│      ↓                                                      │
│      Respuesta HTTP:                                        │
│      Set-Cookie: JSESSIONID=ABC123; Path=/; HttpOnly        │
│                                                             │
│  [2] Usuario navega como invitado (10 minutos)              │
│      Cada request incluye:                                  │
│      Cookie: JSESSIONID=ABC123                              │
│                                                             │
│  [3] Usuario decide hacer login                             │
│      ↓                                                      │
│      POST /login (credenciales)                             │
│      Cookie: JSESSIONID=ABC123  (sesión invitado)           │
│      ↓                                                      │
│      handleLogin() ejecuta:                                 │
│      • oldSession = request.getSession(false)               │
│      • oldSession.invalidate()  // ⚠️ destruye ABC123       │
│      • session = request.getSession(true)  // crea nueva    │
│      • session.setAttribute("logged_user", dtUser)          │
│      ↓                                                      │
│      Respuesta HTTP:                                        │
│      Set-Cookie: JSESSIONID=XYZ789; Path=/; HttpOnly        │
│      ⚠️ NUEVO ID: XYZ789 reemplaza ABC123                   │
│                                                             │
│  [4] Usuario navega autenticado                             │
│      Cada request incluye:                                  │
│      Cookie: JSESSIONID=XYZ789  (nueva sesión)              │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

#### Escenario 2: Usuario entra directo con login

```text
┌─────────────────────────────────────────────────────────────┐
│              COOKIE CREADA EN PRIMER LOGIN                  │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  [1] Usuario va a /login (sin sesión)                       │
│      ↓                                                      │
│      GET /login                                             │
│      (sin cookie JSESSIONID)                                │
│      ↓                                                      │
│      Servidor muestra formulario                            │
│      No crea sesión todavía                                 │
│                                                             │
│  [2] Usuario envía credenciales                             │
│      ↓                                                      │
│      POST /login                                            │
│      (aún sin cookie)                                       │
│      ↓                                                      │
│      handleLogin() ejecuta:                                 │
│      • oldSession = request.getSession(false)  // null      │
│      • session = request.getSession(true)  // crea primera  │
│      • session.setAttribute("logged_user", dtUser)          │
│      ↓                                                      │
│      Respuesta HTTP:                                        │
│      Set-Cookie: JSESSIONID=DEF456; Path=/; HttpOnly        │
│      ⚠️ PRIMERA COOKIE creada en este momento               │
│                                                             │
│  [3] Usuario navega autenticado                             │
│      Cada request incluye:                                  │
│      Cookie: JSESSIONID=DEF456                              │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

#### Escenario 3: Renovación por actividad

```text
┌─────────────────────────────────────────────────────────────┐
│           RENOVACIÓN DE TIMEOUT (mismo JSESSIONID)          │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Usuario autenticado (timeout: 5 minutos)                   │
│  JSESSIONID: XYZ789                                         │
│                                                             │
│  [Minuto 0] POST /login → Sesión creada                     │
│  [Minuto 2] GET /home → Timeout reinicia a 5 min           │
│  [Minuto 4] GET /consult-activities → Timeout reinicia     │
│  [Minuto 7] GET /profile → Timeout reinicia                │
│                                                             │
│  ⚠️ El JSESSIONID NO cambia (sigue siendo XYZ789)           │
│  ⚠️ Solo se reinicia el contador de inactividad            │
│                                                             │
│  Si pasan 5 minutos SIN actividad:                          │
│  → Sesión expira automáticamente                            │
│  → Próximo request: getSession(false) retorna null          │
│  → Filter redirige a /login                                 │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Puntos clave:**

| Acción | ¿Crea nueva sesión? | ¿Nuevo JSESSIONID? | Timeout |
|--------|---------------------|---------------------|---------|
| Primer acceso al sitio | No | No | — |
| Guest login | ✅ Sí | ✅ Sí (primera cookie) | 10 min |
| Login después de guest | ✅ Sí | ✅ Sí (reemplaza anterior) | 5 min |
| Login sin sesión previa | ✅ Sí | ✅ Sí (primera cookie) | 5 min |
| Navegación normal | No | No | — |
| Cada request activo | No (renueva timeout) | No (mismo ID) | Reset |
| Logout | ❌ Invalida sesión | ❌ Cookie queda inválida | — |

---

## Interfaz de Usuario Adaptativa

### Navbar con JSTL

La barra de navegación se adapta dinámicamente según el rol del usuario:

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container-fluid">
    
    <!-- Logo/Brand -->
    <a class="navbar-brand" href="${pageContext.request.contextPath}/home">
      <strong>TurismoUY</strong>
    </a>
    
    <div class="collapse navbar-collapse">
      <ul class="navbar-nav me-auto">
        
        <!-- Enlaces públicos (visible para todos) -->
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/home">
            🏠 Inicio
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" 
             href="${pageContext.request.contextPath}/consult-activities">
            🎭 Actividades
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" 
             href="${pageContext.request.contextPath}/consult-outings">
            🚌 Salidas Turísticas
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" 
             href="${pageContext.request.contextPath}/activity-ranking">
            🏆 Ranking
          </a>
        </li>
        
        <!-- Enlaces solo para TOURIST -->
        <c:if test="${not empty sessionScope.logged_user and 
                      sessionScope.logged_user.userType eq 'TOURIST'}">
          <li class="nav-item">
            <a class="nav-link" 
               href="${pageContext.request.contextPath}/my-inscriptions">
              📝 Mis Inscripciones
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" 
               href="${pageContext.request.contextPath}/inscription-tourist-outing">
              ➕ Inscribirse
            </a>
          </li>
        </c:if>
        
        <!-- Enlaces solo para SUPPLIER -->
        <c:if test="${not empty sessionScope.logged_user and 
                      sessionScope.logged_user.userType eq 'SUPPLIER'}">
          <li class="nav-item">
            <a class="nav-link" 
               href="${pageContext.request.contextPath}/my-activities">
              📋 Mis Actividades
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" 
               href="${pageContext.request.contextPath}/create-activity">
              ➕ Crear Actividad
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" 
               href="${pageContext.request.contextPath}/create-tourist-outing">
              ➕ Crear Salida
            </a>
          </li>
        </c:if>
        
      </ul>
      
      <!-- Panel derecho: info de usuario/guest -->
      <div class="d-flex align-items-center">
        
        <!-- Usuario autenticado -->
        <c:if test="${not empty sessionScope.logged_user}">
          <span class="navbar-text me-3">
            <c:choose>
              <c:when test="${sessionScope.logged_user.userType eq 'TOURIST'}">
                🏖️ Turista:
              </c:when>
              <c:otherwise>
                🏢 Proveedor:
              </c:otherwise>
            </c:choose>
            <strong>${sessionScope.logged_user.nickname}</strong>
          </span>
          <a class="btn btn-outline-light btn-sm me-2" 
             href="${pageContext.request.contextPath}/profile">
            Mi Perfil
          </a>
          <a class="btn btn-danger btn-sm" 
             href="${pageContext.request.contextPath}/logout">
            Cerrar Sesión
          </a>
        </c:if>
        
        <!-- Invitado -->
        <c:if test="${empty sessionScope.logged_user and 
                      not empty sessionScope.guest_mode}">
          <span class="badge bg-secondary me-3">
            👤 Invitado
          </span>
          <a class="btn btn-light btn-sm" 
             href="${pageContext.request.contextPath}/login">
            Iniciar Sesión
          </a>
        </c:if>
        
      </div>
    </div>
  </div>
</nav>
```

### Contenido condicional en páginas

Ejemplo de mostrar/ocultar botones de acción según permisos:

```jsp
<!-- Página de detalle de una salida turística -->

<div class="card">
  <div class="card-body">
    <h3>${salida.nombre}</h3>
    <p>${salida.descripcion}</p>
    
    <!-- Botón visible solo para turistas autenticados -->
    <c:if test="${not empty sessionScope.logged_user and 
                  sessionScope.logged_user.userType eq 'TOURIST'}">
      <form method="POST" 
            action="${pageContext.request.contextPath}/inscription-tourist-outing">
        <input type="hidden" name="outingId" value="${salida.id}"/>
        <button type="submit" class="btn btn-success">
          ✅ Inscribirse a esta salida
        </button>
      </form>
    </c:if>
    
    <!-- Botón visible solo para proveedores (dueños de la salida) -->
    <c:if test="${not empty sessionScope.logged_user and 
                  sessionScope.logged_user.userType eq 'SUPPLIER' and
                  sessionScope.logged_user.nickname eq salida.supplier}">
      <a class="btn btn-warning" 
         href="${pageContext.request.contextPath}/edit-outing?id=${salida.id}">
        ✏️ Editar salida
      </a>
    </c:if>
    
    <!-- Mensaje para invitados -->
    <c:if test="${empty sessionScope.logged_user}">
      <div class="alert alert-info">
        💡 <a href="${pageContext.request.contextPath}/login">Inicia sesión</a> 
        como turista para inscribirte a esta salida.
      </div>
    </c:if>
  </div>
</div>
```

---

## Patrones de Navegación

### Forward vs Redirect

Dos mecanismos distintos para pasar el control de un servlet a otro recurso:

#### FORWARD (server-side)

```text
┌─────────────────────────────────────────────────────────────┐
│                    FORWARD (Server-side)                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  [Cliente] ────── POST /login ─────> [LoginServlet]         │
│                                           │                 │
│                                           │ forward()       │
│                                           ▼                 │
│                                      [home.jsp]             │
│                                           │                 │
│  [Cliente] <───── HTML Response ──────────┘                 │
│                                                             │
│  ✅ Mismo Request/Response                                  │
│  ✅ Atributos se preservan                                  │
│  ✅ Rápido (sin round-trip)                                 │
│  ❌ URL no cambia en navegador: sigue mostrando "/login"    │
│  ❌ F5 reenvía el POST (problema de doble envío)            │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Cuándo usar:**
- Para mostrar errores de validación en el mismo formulario
- Para pasar atributos del servlet a la JSP
- Cuando no importa que la URL no cambie

**Ejemplo:**
```java
// Validación fallida: forward para mostrar error
request.setAttribute("loginError", "Usuario o contraseña incorrectos");
request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp")
    .forward(request, response);
```

#### REDIRECT (client-side)

```text
┌─────────────────────────────────────────────────────────────┐
│                    REDIRECT (Client-side)                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  [Cliente] ────── POST /login ─────> [LoginServlet]         │
│                                           │                 │
│                                response.sendRedirect()      │
│                                           │                 │
│  [Cliente] <───── 302 Found + Location ───┘                 │
│                                                             │
│  [Cliente] ────── GET /home ──────> [HomeServlet/JSP]       │
│                                           │                 │
│  [Cliente] <───── HTML Response ──────────┘                 │
│                                                             │
│  ✅ URL cambia en navegador: muestra "/home"                │
│  ✅ F5 solo recarga la página (GET), no reenvía POST        │
│  ✅ Evita doble envío de formulario                         │
│  ❌ Atributos de request se pierden                         │
│  ❌ Más lento (dos peticiones HTTP)                         │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Cuándo usar:**
- Después de operaciones POST exitosas (login, registro, creación)
- Para implementar patrón Post-Redirect-Get (PRG)
- Cuando queremos que la URL del navegador refleje la nueva página

**Ejemplo:**
```java
// Login exitoso: redirect para cambiar URL y evitar reenvío
response.sendRedirect(request.getContextPath() + "/home");
```

### Patrón Post-Redirect-Get (PRG)

Estrategia para evitar el problema de doble envío de formularios:

```text
┌─────────────────────────────────────────────────────────────┐
│                  PATRÓN POST-REDIRECT-GET                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  [1] Usuario envía formulario                               │
│      ↓                                                      │
│  [2] POST /login (credenciales)                             │
│      ↓                                                      │
│  [3] Servlet procesa y valida                               │
│      ↓                                                      │
│  [4] Si ÉXITO → 302 Redirect /home                          │
│      Si ERROR → Forward /login.jsp (con mensaje)            │
│      ↓                                                      │
│  [5] Cliente recibe 302 y hace:                             │
│      GET /home                                              │
│      ↓                                                      │
│  [6] Servidor responde con página de inicio                 │
│                                                             │
│  ✅ Si el usuario presiona F5, solo recarga GET /home       │
│  ✅ No se reenvía el POST de login                          │
│  ✅ Mejor experiencia de usuario                            │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Implementación:**

```java
protected void doPost(HttpServletRequest request, 
                      HttpServletResponse response)
        throws ServletException, IOException {
    
    String action = request.getParameter("action");
    
    if ("login".equals(action)) {
        // Validar credenciales
        if (credencialesValidas()) {
            // ÉXITO: Redirect (PRG)
            HttpSession session = request.getSession(true);
            session.setAttribute("logged_user", user);
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            // ERROR: Forward para mostrar mensaje
            request.setAttribute("loginError", "Credenciales inválidas");
            request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp")
                .forward(request, response);
        }
    }
}
```

---

## Matriz de Control de Acceso

Tabla completa de permisos por URL y rol:

### URLs Públicas

| URL | Invitado | Turista | Proveedor | Descripción |
|-----|----------|---------|-----------|-------------|
| `/login` | ✅ | ✅ | ✅ | Página de inicio de sesión |
| `/register` | ✅ | ✅ | ✅ | Formulario de registro |
| `/guest-login` | ✅ | ✅ | ✅ | Iniciar como invitado |
| `/IniciarSesionRegistrarse.jsp` | ✅ | ✅ | ✅ | JSP del formulario |
| `/css/*` | ✅ | ✅ | ✅ | Archivos CSS |
| `/js/*` | ✅ | ✅ | ✅ | Archivos JavaScript |
| `/images/*` | ✅ | ✅ | ✅ | Imágenes estáticas |
| `/uploads/*` | ✅ | ✅ | ✅ | Fotos de perfil |
| `/favicon.ico` | ✅ | ✅ | ✅ | Favicon |

### URLs de Consulta (Guest)

| URL | Invitado | Turista | Proveedor | Descripción |
|-----|----------|---------|-----------|-------------|
| `/home` | ✅ | ✅ | ✅ | Página de inicio |
| `/consult-activities` | ✅ | ✅ | ✅ | Listar actividades |
| `/consult-outings` | ✅ | ✅ | ✅ | Listar salidas turísticas |
| `/activity-ranking` | ✅ | ✅ | ✅ | Ranking de popularidad |
| `/consult-user` | ✅ | ✅ | ✅ | Ver perfiles públicos |

### URLs Autenticadas

| URL | Invitado | Turista | Proveedor | Descripción |
|-----|----------|---------|-----------|-------------|
| `/profile` | ❌ | ✅ | ✅ | Ver mi perfil |
| `/modify-data-user` | ❌ | ✅ | ✅ | Editar mi perfil |
| `/logout` | ❌ | ✅ | ✅ | Cerrar sesión |

### URLs exclusivas de Turista

| URL | Invitado | Turista | Proveedor | Descripción |
|-----|----------|---------|-----------|-------------|
| `/my-inscriptions` | ❌ | ✅ | ❌ | Mis inscripciones |
| `/inscription-tourist-outing` | ❌ | ✅ | ❌ | Inscribirse a salida |

**Respuesta si no autorizado:** 403 Forbidden con mensaje "Esta funcionalidad es solo para turistas"

### URLs exclusivas de Proveedor

| URL | Invitado | Turista | Proveedor | Descripción |
|-----|----------|---------|-----------|-------------|
| `/my-activities` | ❌ | ❌ | ✅ | Mis actividades |
| `/create-activity` | ❌ | ❌ | ✅ | Crear actividad |
| `/create-tourist-outing` | ❌ | ❌ | ✅ | Crear salida turística |

**Respuesta si no autorizado:** 403 Forbidden con mensaje "Esta funcionalidad es solo para proveedores"

---

## Configuración en web.xml

Configuración del timeout de sesión por defecto:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
         https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
         version="5.0">
    
    <!-- Configuración de sesión -->
    <session-config>
        <!-- Timeout por defecto: 5 minutos -->
        <session-timeout>5</session-timeout>
        
        <!-- Configuración de cookie JSESSIONID -->
        <cookie-config>
            <http-only>true</http-only>
            <secure>false</secure> <!-- true en producción con HTTPS -->
            <max-age>-1</max-age> <!-- Session cookie (se borra al cerrar navegador) -->
        </cookie-config>
    </session-config>
    
    <!-- Página de inicio por defecto -->
    <welcome-file-list>
        <welcome-file>AccedeAlHome.jsp</welcome-file>
    </welcome-file-list>
    
</web-app>
```

**Nota:** Los timeouts específicos por rol se configuran dinámicamente en el código del servlet con `session.setMaxInactiveInterval()`.

---

## Resumen de Mejores Prácticas

### ✅ Seguridad

- **Filtro centralizado:** Toda validación de acceso pasa por `AuthenticationFilter`
- **Validación doble:** Filter + validación en servlet para operaciones críticas
- **Timeout diferenciado:** Invitados (10min), Turistas (5min), Proveedores (30min)
- **Cookies seguras:** `HttpOnly=true` para prevenir XSS

### ✅ Experiencia de Usuario

- **Patrón PRG:** Redirect después de POST exitoso
- **Forward para errores:** Mantener datos del formulario en validaciones fallidas
- **UI adaptativa:** JSTL para mostrar/ocultar según permisos
- **Feedback claro:** Mensajes de error específicos por tipo de restricción

### ✅ Mantenibilidad

- **Separación de concerns:** Filter (autenticación) → Servlet (lógica) → JSP (vista)
- **Listas centralizadas:** URLs categorizadas en constantes del Filter
- **Código declarativo:** JSTL en lugar de scriptlets
- **Nomenclatura clara:** `logged_user`, `guest_mode`, `user_role`

---

**Fin del documento**

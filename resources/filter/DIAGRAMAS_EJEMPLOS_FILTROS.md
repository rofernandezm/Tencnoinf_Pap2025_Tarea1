# 📊 DIAGRAMAS Y EJEMPLOS - FILTROS MIDDLEWARE

## 🎯 CASOS DE USO ILUSTRADOS

Este documento complementa `ARQUITECTURA_FILTRO_MIDDLEWARE.md` y `PLAN_IMPLEMENTACION_FILTROS.md` con ejemplos visuales y casos de uso concretos.

---

## 📋 TABLA DE CONTENIDOS

1. [Diagrama de Estados de Sesión](#diagrama-de-estados-de-sesión)
2. [Flujos Detallados](#flujos-detallados)
3. [Matriz de Permisos Extendida](#matriz-de-permisos-extendida)
4. [Ejemplos de Código Real](#ejemplos-de-código-real)
5. [Debugging y Logs](#debugging-y-logs)

---

## 🔄 DIAGRAMA DE ESTADOS DE SESIÓN

```
                    ┌─────────────────────────┐
                    │   NAVEGADOR ABIERTO     │
                    │   (Sin cookies)         │
                    └────────────┬────────────┘
                                 │
                                 │ Primera petición
                                 ▼
                    ┌─────────────────────────┐
                    │   ESTADO: GUEST         │
                    │   ─────────────────     │
                    │   • guest_mode = true   │
                    │   • user_role = GUEST   │
                    │   • logged_user = null  │
                    └────────────┬────────────┘
                                 │
                  ┌──────────────┼──────────────┐
                  │              │              │
            Click │        Click │        Navega│
          "Inicio │       "Entrar│         por  │
          Invitado"│       Sesión"│      páginas│
                  │              │        públicas│
                  ▼              ▼              │
       ┌──────────────┐  ┌──────────────┐     │
       │ Continúa     │  │ Formulario   │     │
       │ como GUEST   │  │ Login        │     │
       └──────────────┘  └───────┬──────┘     │
                                 │             │
                            POST │credentials  │
                                 ▼             │
                    ┌─────────────────────────┐│
                    │   VALIDACIÓN            ││
                    │   ──────────            ││
                    │   ¿Credenciales OK?     ││
                    └────────┬────────────────┘│
                             │                  │
                    ┌────────┴────────┐        │
                    │                 │        │
              Válidas│           Inválidas     │
                    │                 │        │
                    ▼                 ▼        │
       ┌────────────────────┐   ┌──────────┐  │
       │   AUTENTICADO      │   │  ERROR   │  │
       │   ────────────     │   │ Reintentar│ │
       │   [TOURIST/        │   └─────┬────┘  │
       │    SUPPLIER]       │         └────────┘
       │                    │
       │ • guest_mode=false │
       │ • user_role=TOURIST│
       │   o SUPPLIER       │
       │ • logged_user=DtUser│
       └─────────┬──────────┘
                 │
    ┌────────────┼───────────────┐
    │            │               │
Intenta│    Navega por│      Click│
acceder│     recursos │    "Cerrar│
recurso│     permitidos│    Sesión"│
no     │               │          │
permitido              │          │
    │                  │          │
    ▼                  │          ▼
┌───────────┐         │   ┌──────────────┐
│  ERROR    │         │   │   LOGOUT     │
│  403      │         │   │   ──────     │
│ Forbidden │         │   │ Invalidar    │
└───────────┘         │   │ sesión       │
                      │   └───────┬──────┘
                      │           │
                      │           ▼
                      │   ┌──────────────┐
                      │   │ Volver a     │
                      │   │ GUEST        │
                      └───┤ (nueva sesión)│
                          └──────────────┘
```

---

## 🔄 FLUJOS DETALLADOS

### Flujo A: Primer Acceso al Sitio (Usuario Nuevo)

```
1. NAVEGADOR
   └─► http://localhost:8080/turismouy.UI/
       (Método: GET, Sin cookies)

2. TOMCAT recibe petición

3. FILTER CHAIN
   ├─► CharacterEncodingFilter
   │   └─► Establece UTF-8
   │
   ├─► SessionManagementFilter
   │   ├─► request.getSession(false) → null
   │   ├─► Crea nueva sesión
   │   ├─► session.setAttribute("guest_mode", true)
   │   ├─► session.setAttribute("user_role", UserType.GUEST)
   │   └─► LOG: "Inicializando sesión de invitado"
   │
   ├─► AuthenticationFilter
   │   ├─► URI = "/"
   │   ├─► isPublicResource("/") → true
   │   └─► Continuar (sin verificar autenticación)
   │
   └─► AuthorizationFilter
       ├─► getUserRole() → GUEST
       ├─► hasAccess("/", GUEST) → true
       └─► Continuar

4. SERVLET (welcome-file redirige a /login)
   └─► Forward a iniciarSesionRegistrarse.jsp

5. JSP renderiza
   ├─► Muestra formulario de login
   ├─► Muestra formulario de registro
   └─► Botón "Continuar como Invitado"

6. RESPUESTA HTTP
   ├─► Status: 200 OK
   ├─► Set-Cookie: JSESSIONID=ABC123; HttpOnly
   └─► HTML de la página

7. NAVEGADOR
   └─► Guarda cookie JSESSIONID=ABC123
```

### Flujo B: Login de Usuario Turista

```
1. NAVEGADOR (ya tiene JSESSIONID de sesión GUEST)
   └─► POST http://localhost:8080/turismouy.UI/login?action=login
       Content-Type: application/x-www-form-urlencoded
       Cookie: JSESSIONID=ABC123
       
       Body:
       nickname-or-email=maria
       password=pass123

2. TOMCAT recibe petición

3. FILTER CHAIN
   ├─► CharacterEncodingFilter → UTF-8
   │
   ├─► SessionManagementFilter
   │   ├─► request.getSession(false) → sesión existente (GUEST)
   │   └─► Ya tiene user_role, continuar
   │
   ├─► AuthenticationFilter
   │   ├─► URI = "/login"
   │   ├─► isPublicResource("/login") → true
   │   └─► Continuar
   │
   └─► AuthorizationFilter
       └─► URI público, continuar

4. SERVLET Login.java
   ├─► handleLogin(request, response)
   ├─► nickname = "maria"
   ├─► password = "pass123"
   │
   ├─► Llamar Backend:
   │   └─► DtUser user = iUserController.consultUserData("maria")
   │
   ├─► Validar password:
   │   ├─► user.getPassword().equals("pass123") → true
   │   └─► ✅ Credenciales válidas
   │
   ├─► createNewSessionAndAssingUser():
   │   ├─► Obtener sesión actual: ABC123
   │   ├─► session.invalidate() → Destruir sesión GUEST
   │   │
   │   ├─► request.getSession(true) → Nueva sesión XYZ789
   │   ├─► session.setAttribute("logged_user", user)
   │   ├─► session.setAttribute("user_role", UserType.TOURIST)
   │   ├─► session.setAttribute("guest_mode", false)
   │   │
   │   ├─► Verificar redirect_after_login → null
   │   └─► response.sendRedirect("/turismouy.UI/home")

5. RESPUESTA HTTP
   ├─► Status: 302 Found
   ├─► Location: http://localhost:8080/turismouy.UI/home
   ├─► Set-Cookie: JSESSIONID=XYZ789; HttpOnly (NUEVA)
   └─► (Sin body, solo headers)

6. NAVEGADOR
   ├─► Elimina cookie JSESSIONID=ABC123
   ├─► Guarda cookie JSESSIONID=XYZ789
   └─► Sigue redirección a /home

7. NUEVA PETICIÓN GET /home
   └─► (Continúa en Flujo C)
```

### Flujo C: Usuario Autenticado Navega a Home

```
1. NAVEGADOR
   └─► GET http://localhost:8080/turismouy.UI/home
       Cookie: JSESSIONID=XYZ789 (sesión TOURIST)

2. FILTER CHAIN
   ├─► CharacterEncodingFilter → UTF-8
   │
   ├─► SessionManagementFilter
   │   ├─► request.getSession(false) → sesión XYZ789
   │   ├─► session.getAttribute("user_role") → TOURIST
   │   └─► LOG: "Sesión válida con rol TOURIST"
   │
   ├─► AuthenticationFilter
   │   ├─► URI = "/home"
   │   ├─► isPublicResource("/home") → true
   │   └─► Continuar (sin verificar, es público)
   │
   └─► AuthorizationFilter
       ├─► getUserRole() → TOURIST
       ├─► hasAccess("/home", TOURIST) → true
       └─► Continuar

3. SERVLET home.java
   └─► doGet(request, response)
       ├─► Obtener datos del backend (actividades, etc.)
       └─► Forward a home.jsp

4. JSP home.jsp
   ├─► <jsp:include page="header.jsp" />
   │   └─► header.jsp:
   │       ├─► UserType userRole = SessionUtils.getUserRole(request)
   │       ├─► DtUser loggedUser = SessionUtils.getLoggedUser(request)
   │       │
   │       ├─► if (userRole == TOURIST)
   │       │   └─► Mostrar:
   │       │       • Menú "Inscripciones"
   │       │       • Dropdown con nombre "maria"
   │       │       • Opción "Mi Perfil"
   │       │       • Opción "Cerrar Sesión"
   │       │
   │       └─► Ocultar:
   │           • Botón "Iniciar Sesión"
   │           • Botón "Registrarse"
   │
   └─► Renderizar contenido principal

5. RESPUESTA HTTP
   ├─► Status: 200 OK
   ├─► Content-Type: text/html;charset=UTF-8
   └─► HTML personalizado para TOURIST
```

### Flujo D: Turista Intenta Crear Actividad (DENEGADO)

```
1. NAVEGADOR (sesión TOURIST - maria)
   └─► GET http://localhost:8080/turismouy.UI/activities?action=showCreateForm
       Cookie: JSESSIONID=XYZ789

2. FILTER CHAIN
   ├─► CharacterEncodingFilter → UTF-8
   │
   ├─► SessionManagementFilter
   │   └─► Sesión válida → TOURIST
   │
   ├─► AuthenticationFilter
   │   ├─► URI = "/activities"
   │   ├─► isPublicResource("/activities") → true (consulta pública)
   │   └─► Continuar
   │
   └─► AuthorizationFilter
       ├─► getUserRole() → TOURIST
       ├─► hasAccess("/activities", TOURIST) → true
       └─► Continuar (consulta permitida)

3. SERVLET Activities.java
   └─► doGet(request, response)
       ├─► String action = request.getParameter("action")
       ├─► action = "showCreateForm"
       │
       ├─► Verificación adicional en servlet:
       │   if (!SessionUtils.hasRole(request, UserType.SUPPLIER)) {
       │       request.setAttribute("error_message", 
       │           "Solo los proveedores pueden crear actividades");
       │       request.getRequestDispatcher("/WEB-INF/vistas/error403.jsp")
       │           .forward(request, response);
       │       return;
       │   }
       │
       └─► ❌ TOURIST no es SUPPLIER → Forward a error403.jsp

4. JSP error403.jsp
   └─► Renderiza página de error
       ├─► Icono de candado
       ├─► Mensaje: "Solo los proveedores pueden crear actividades"
       ├─► Botón "Volver al Inicio"
       └─► Botón "Volver Atrás"

5. RESPUESTA HTTP
   ├─► Status: 200 OK (forward, no es error HTTP real)
   └─► HTML de error403.jsp

ALTERNATIVA: Si se accede directamente a URL de creación protegida:

3. SERVLET Activities.java
   └─► POST /activities?action=create
       └─► Mismo código de verificación
           └─► response.sendError(HttpServletResponse.SC_FORBIDDEN)

5. RESPUESTA HTTP
   ├─► Status: 403 Forbidden
   └─► Tomcat muestra error403.jsp configurado en web.xml
```

### Flujo E: Turista Se Inscribe a Salida (PERMITIDO)

```
1. NAVEGADOR (sesión TOURIST - maria)
   └─► POST http://localhost:8080/turismouy.UI/inscriptions
       Cookie: JSESSIONID=XYZ789
       
       Body:
       outing=Salida001
       quantity=2

2. FILTER CHAIN
   ├─► CharacterEncodingFilter → UTF-8
   │
   ├─► SessionManagementFilter
   │   └─► Sesión válida → TOURIST
   │
   ├─► AuthenticationFilter
   │   ├─► URI = "/inscriptions"
   │   ├─► isPublicResource("/inscriptions") → false
   │   ├─► isAuthenticated(request) → true ✅
   │   └─► Continuar
   │
   └─► AuthorizationFilter
       ├─► getUserRole() → TOURIST
       ├─► hasAccess("/inscriptions", TOURIST) → true ✅
       │   (TOURIST_ONLY_RESOURCES contiene "/inscriptions")
       └─► Continuar

3. SERVLET Inscriptions.java
   └─► doPost(request, response)
       ├─► DtUser user = SessionUtils.getLoggedUser(request)
       ├─► String outingName = request.getParameter("outing")
       ├─► int quantity = Integer.parseInt(request.getParameter("quantity"))
       │
       ├─► Llamar Backend:
       │   └─► controller.registerInscription(
       │           user.getNickname(), 
       │           outingName, 
       │           quantity, 
       │           LocalDate.now()
       │       )
       │
       ├─► ✅ Inscripción exitosa
       │
       └─► response.sendRedirect("/turismouy.UI/inscriptions?success=true")

4. RESPUESTA HTTP
   ├─► Status: 302 Found
   ├─► Location: /turismouy.UI/inscriptions?success=true
   └─► Set-Cookie: JSESSIONID=XYZ789 (mantiene sesión)

5. NAVEGADOR
   └─► Sigue redirección a /inscriptions?success=true
       └─► Muestra mensaje: "Inscripción realizada correctamente"
```

### Flujo F: Logout

```
1. NAVEGADOR (sesión TOURIST - maria)
   └─► GET http://localhost:8080/turismouy.UI/logout
       Cookie: JSESSIONID=XYZ789

2. FILTER CHAIN
   ├─► SessionManagementFilter → Sesión válida
   ├─► AuthenticationFilter → /logout es público
   └─► AuthorizationFilter → Todos pueden hacer logout

3. SERVLET Logout.java
   └─► doGet(request, response)
       ├─► SessionUtils.invalidateSession(request)
       │   └─► HttpSession session = request.getSession(false)
       │       if (session != null) {
       │           session.invalidate() → Destruye XYZ789
       │       }
       │
       ├─► HttpSession newSession = request.getSession(true)
       ├─► newSession.setAttribute("mensaje", "Sesión cerrada correctamente")
       │
       └─► response.sendRedirect("/turismouy.UI/login")

4. RESPUESTA HTTP
   ├─► Status: 302 Found
   ├─► Location: /turismouy.UI/login
   └─► Set-Cookie: JSESSIONID=PQR456 (NUEVA sesión vacía)

5. NAVEGADOR
   ├─► Elimina JSESSIONID=XYZ789
   ├─► Guarda JSESSIONID=PQR456
   └─► Sigue redirección a /login

6. NUEVA PETICIÓN GET /login
   └─► SessionManagementFilter detecta sesión sin user_role
       └─► Inicializa como GUEST
           ├─► session.setAttribute("guest_mode", true)
           ├─► session.setAttribute("user_role", UserType.GUEST)
           └─► Usuario vuelve a estado inicial
```

---

## 📊 MATRIZ DE PERMISOS EXTENDIDA

### Recursos por Categoría

#### 1. Recursos Públicos (GUEST, TOURIST, SUPPLIER)

| URL Pattern | Descripción | Método | Notas |
|-------------|-------------|--------|-------|
| `/login` | Página de inicio de sesión | GET, POST | Punto de entrada |
| `/logout` | Cierre de sesión | GET, POST | Todos pueden cerrar |
| `/home` | Página principal | GET | Landing page |
| `/consult-user` | Consulta de perfil público | GET | Datos básicos |
| `/activities` | Listar actividades | GET | Solo lectura |
| `/outings` | Listar salidas | GET | Solo lectura |
| `/res/*` | Recursos estáticos | GET | Imágenes, CSS, JS |
| `/assets/*` | Assets frontend | GET | Bootstrap, iconos |

#### 2. Recursos Autenticados (TOURIST, SUPPLIER)

| URL Pattern | Descripción | Método | Roles | Notas |
|-------------|-------------|--------|-------|-------|
| `/modify-data-user` | Modificar perfil propio | GET, POST | TOURIST, SUPPLIER | Solo propio perfil |
| `/consult-user` (propio) | Ver datos completos | GET | TOURIST, SUPPLIER | Incluye inscripciones/actividades propias |

#### 3. Recursos Exclusivos TOURIST

| URL Pattern | Descripción | Método | Notas |
|-------------|-------------|--------|-------|
| `/inscriptions` | Ver mis inscripciones | GET | Lista personal |
| `/inscriptions?action=register` | Inscribirse a salida | POST | Crear inscripción |
| `/inscriptions?action=cancel` | Cancelar inscripción | POST | Eliminar inscripción |

#### 4. Recursos Exclusivos SUPPLIER

| URL Pattern | Descripción | Método | Notas |
|-------------|-------------|--------|-------|
| `/activities?action=create` | Crear actividad | POST | Estado: Agregada |
| `/activities?action=showCreateForm` | Formulario nueva actividad | GET | Renderizar form |
| `/activities?action=modify` | Modificar actividad | POST | Solo propias |
| `/outings?action=create` | Crear salida | POST | Para actividad propia |
| `/outings?action=showCreateForm` | Formulario nueva salida | GET | Renderizar form |

#### 5. Recursos Solo Desktop (Administrador)

| URL Pattern | Descripción | Acceso Web |
|-------------|-------------|------------|
| Aceptar/Rechazar Actividad | Cambiar estado | ❌ No accesible |
| Ranking de Actividades | Estadísticas admin | ❌ No accesible |

---

## 💻 EJEMPLOS DE CÓDIGO REAL

### Ejemplo 1: Verificación en Servlet Activities

```java
@WebServlet("/activities")
public class Activities extends HttpServlet {
    
    private final ITouristActivityController activityController;
    
    public Activities() {
        super();
        FactoryUyTourism factory = FactoryUyTourism.getInstance();
        this.activityController = factory.getITouristActivityController();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("showCreateForm".equals(action)) {
            // Solo proveedores pueden ver el formulario de creación
            if (!SessionUtils.hasRole(request, UserType.SUPPLIER)) {
                request.setAttribute("error_message", 
                    "Solo los proveedores pueden crear actividades");
                request.getRequestDispatcher("/WEB-INF/vistas/error403.jsp")
                    .forward(request, response);
                return;
            }
            
            // Mostrar formulario
            request.getRequestDispatcher("/WEB-INF/vistas/createActivity.jsp")
                .forward(request, response);
            return;
        }
        
        // Listar actividades (público)
        List<DtActivityWithOutings> activities = activityController.getAllActivities();
        request.setAttribute("activities", activities);
        request.getRequestDispatcher("/WEB-INF/vistas/activities.jsp")
            .forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            // Verificar rol SUPPLIER
            DtUser loggedUser = SessionUtils.getLoggedUser(request);
            if (loggedUser == null || loggedUser.getUserType() != UserType.SUPPLIER) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, 
                    "Solo proveedores pueden crear actividades");
                return;
            }
            
            // Obtener datos del formulario
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            // ... resto de campos
            
            try {
                // Crear actividad en backend
                activityController.createActivity(
                    name, 
                    description, 
                    /* ... */,
                    loggedUser.getNickname() // proveedor
                );
                
                // Redireccionar con mensaje de éxito
                response.sendRedirect(request.getContextPath() + 
                    "/activities?success=Actividad creada exitosamente");
                
            } catch (RepeatedActivityNameException e) {
                request.setAttribute("error", "Ya existe una actividad con ese nombre");
                request.getRequestDispatcher("/WEB-INF/vistas/createActivity.jsp")
                    .forward(request, response);
            }
        }
    }
}
```

### Ejemplo 2: Header Dinámico en JSP

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
String nickname = (loggedUser != null) ? loggedUser.getNickname() : "Invitado";
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <!-- Logo -->
        <a class="navbar-brand" href="<%=ctx%>/home">
            <img src="<%=ctx%>/res/turismouyAppIcon.png" height="30" alt="TurismoUY">
            TurismoUY
        </a>
        
        <!-- Menú principal -->
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto">
                <!-- Home (todos) -->
                <li class="nav-item">
                    <a class="nav-link" href="<%=ctx%>/home">
                        <i class="bi bi-house-door"></i> Inicio
                    </a>
                </li>
                
                <!-- Actividades (todos) -->
                <li class="nav-item">
                    <a class="nav-link" href="<%=ctx%>/activities">
                        <i class="bi bi-calendar-event"></i> Actividades
                    </a>
                </li>
                
                <!-- Salidas (todos) -->
                <li class="nav-item">
                    <a class="nav-link" href="<%=ctx%>/outings">
                        <i class="bi bi-geo-alt"></i> Salidas
                    </a>
                </li>
                
                <!-- Inscripciones (solo TOURIST) -->
                <% if (isTourist) { %>
                <li class="nav-item">
                    <a class="nav-link" href="<%=ctx%>/inscriptions">
                        <i class="bi bi-card-checklist"></i> Mis Inscripciones
                    </a>
                </li>
                <% } %>
            </ul>
            
            <!-- Menú usuario -->
            <ul class="navbar-nav">
                <% if (isGuest) { %>
                    <!-- Botones para invitados -->
                    <li class="nav-item">
                        <a class="btn btn-outline-primary me-2" href="<%=ctx%>/login">
                            <i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-primary" href="<%=ctx%>/login?action=showRegister">
                            <i class="bi bi-person-plus"></i> Registrarse
                        </a>
                    </li>
                <% } else { %>
                    <!-- Dropdown usuario autenticado -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" 
                           role="button" data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle"></i> 
                            <%=nickname%>
                            <% if (isSupplier) { %>
                                <span class="badge bg-info">Proveedor</span>
                            <% } else if (isTourist) { %>
                                <span class="badge bg-success">Turista</span>
                            <% } %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li>
                                <a class="dropdown-item" href="<%=ctx%>/consult-user?nickname=<%=nickname%>">
                                    <i class="bi bi-person"></i> Mi Perfil
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="<%=ctx%>/modify-data-user">
                                    <i class="bi bi-gear"></i> Configuración
                                </a>
                            </li>
                            
                            <% if (isSupplier) { %>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item" href="<%=ctx%>/activities?action=showCreateForm">
                                    <i class="bi bi-plus-circle"></i> Nueva Actividad
                                </a>
                            </li>
                            <% } %>
                            
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item text-danger" href="<%=ctx%>/logout">
                                    <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                                </a>
                            </li>
                        </ul>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>
```

### Ejemplo 3: Protección en ModifyDataUser

```java
@WebServlet("/modify-data-user")
@MultipartConfig
public class ModifyDataUser extends HttpServlet {
    
    private final IUserController userController;
    
    public ModifyDataUser() {
        super();
        this.userController = FactoryUyTourism.getInstance().getIUserController();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar autenticación (aunque el filtro ya lo hace)
        DtUser loggedUser = SessionUtils.getLoggedUser(request);
        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Obtener datos actuales para el formulario
        String nickname = loggedUser.getNickname();
        DtUser userData = userController.consultUserData(nickname);
        
        request.setAttribute("user", userData);
        request.getRequestDispatcher("/WEB-INF/vistas/modificarDatosUsuario.jsp")
            .forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener usuario logueado
        DtUser loggedUser = SessionUtils.getLoggedUser(request);
        if (loggedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Obtener nickname del formulario
        String nicknameToEdit = request.getParameter("nickname");
        
        // CRÍTICO: Verificar que solo edite su propio perfil
        if (nicknameToEdit == null || !loggedUser.getNickname().equals(nicknameToEdit)) {
            request.setAttribute("error_message", 
                "Solo puede modificar su propio perfil");
            request.getRequestDispatcher("/WEB-INF/vistas/error403.jsp")
                .forward(request, response);
            return;
        }
        
        // Obtener datos del formulario
        String newName = request.getParameter("name");
        String newLastName = request.getParameter("lastName");
        String newBirthDate = request.getParameter("birthDate");
        // ... resto de campos EXCEPTO nickname y email
        
        try {
            // Actualizar en backend
            userController.modifyUserData(
                nicknameToEdit,
                newName,
                newLastName,
                LocalDate.parse(newBirthDate)
                // ...
            );
            
            // Actualizar sesión con datos nuevos
            DtUser updatedUser = userController.consultUserData(nicknameToEdit);
            SessionUtils.initAuthenticatedSession(request, updatedUser);
            
            // Redireccionar con éxito
            response.sendRedirect(request.getContextPath() + 
                "/consult-user?nickname=" + nicknameToEdit + "&success=true");
            
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("user", loggedUser);
            request.getRequestDispatcher("/WEB-INF/vistas/modificarDatosUsuario.jsp")
                .forward(request, response);
        }
    }
}
```

---

## 🔍 DEBUGGING Y LOGS

### Configuración de Logging

**Crear archivo**: `frontend/src/main/resources/logging.properties`

```properties
# Nivel global
.level=INFO

# Niveles específicos por paquete
turismouyapp.filters.level=FINE
turismouyapp.security.level=FINE
turismouyapp.servlets.level=INFO

# Configuración de handlers
handlers=java.util.logging.ConsoleHandler

# Formato de console handler
java.util.logging.ConsoleHandler.level=FINE
java.util.logging.ConsoleHandler.formatter=java.util.logging.SimpleFormatter

# Formato de salida
java.util.logging.SimpleFormatter.format=[%1$tF %1$tT] [%4$-7s] %5$s %n
```

### Logs Esperados en Startup

```
[2025-10-15 14:30:15] [INFO   ] [CharacterEncodingFilter] Inicializado con encoding: UTF-8
[2025-10-15 14:30:15] [INFO   ] [SessionManagementFilter] Inicializado correctamente
[2025-10-15 14:30:15] [INFO   ] [AuthenticationFilter] Inicializado correctamente
[2025-10-15 14:30:15] [INFO   ] [AuthorizationFilter] Inicializado correctamente
[2025-10-15 14:30:15] [INFO   ] [DB] HSQLDB iniciado por Tomcat en puerto 9001
[2025-10-15 14:30:16] [INFO   ] Server startup in [2345] milliseconds
```

### Logs Durante Petición

```
# GET /home (primera vez, como GUEST)
[2025-10-15 14:31:00] [FINE   ] [SessionManagementFilter] No hay sesión existente. Inicializando sesión de invitado.
[2025-10-15 14:31:00] [FINE   ] [AuthenticationFilter] Verificando recurso: /home
[2025-10-15 14:31:00] [FINE   ] [AuthorizationFilter] Verificando permisos para rol GUEST en recurso: /home
[2025-10-15 14:31:00] [FINE   ] [AuthorizationFilter] Acceso PERMITIDO. Usuario con rol GUEST accedió a: /home

# POST /login (autenticación exitosa)
[2025-10-15 14:32:10] [FINE   ] [SessionManagementFilter] Sesión sin user_role. Estableciendo modo invitado.
[2025-10-15 14:32:10] [INFO   ] [Login] Usuario 'maria' intentando iniciar sesión
[2025-10-15 14:32:10] [INFO   ] [Login] Credenciales válidas para 'maria' (TOURIST)
[2025-10-15 14:32:10] [INFO   ] [SessionUtils] Invalidando sesión anterior (prevención session fixation)
[2025-10-15 14:32:10] [INFO   ] [SessionUtils] Sesión autenticada creada para usuario: maria (TOURIST)

# GET /inscriptions (usuario autenticado como TOURIST)
[2025-10-15 14:33:00] [FINE   ] [AuthenticationFilter] Usuario autenticado. Permitiendo acceso a: /inscriptions
[2025-10-15 14:33:00] [FINE   ] [AuthorizationFilter] Acceso PERMITIDO. Usuario con rol TOURIST accedió a: /inscriptions

# GET /activities?action=showCreateForm (TOURIST intenta crear - DENEGADO)
[2025-10-15 14:34:00] [WARNING] [Activities] Usuario 'maria' (TOURIST) intentó acceder a formulario de creación
[2025-10-15 14:34:00] [WARNING] [Activities] Acceso denegado. Solo proveedores pueden crear actividades.

# GET /logout
[2025-10-15 14:35:00] [INFO   ] [Logout] Usuario 'maria' cerrando sesión
[2025-10-15 14:35:00] [INFO   ] [SessionUtils] Sesión invalidada correctamente
```

### Comandos para Ver Logs en Tiempo Real

**Tomcat Logs**:
```bash
# Linux/macOS
tail -f server/apache-tomcat-11.0.11/logs/catalina.out

# Windows (PowerShell)
Get-Content server\apache-tomcat-11.0.11\logs\catalina.out -Wait -Tail 50
```

**Filtrar logs específicos**:
```bash
# Solo filtros
tail -f catalina.out | grep -E "Filter|SessionUtils|AuthorizationFilter"

# Solo errores
tail -f catalina.out | grep -E "ERROR|WARNING|Exception"
```

---

## 🧪 SCRIPTS DE TESTING

### Script 1: Test de Sesiones con cURL

```bash
#!/bin/bash

# Guardar cookies en archivo
COOKIES="cookies.txt"

echo "=== TEST 1: Acceso inicial (GUEST) ==="
curl -v -c $COOKIES http://localhost:8080/turismouy.UI/home 2>&1 | grep -E "Set-Cookie|< HTTP"

echo -e "\n=== TEST 2: Login ==="
curl -v -b $COOKIES -c $COOKIES \
  -d "action=login&nickname-or-email=maria&password=pass123" \
  http://localhost:8080/turismouy.UI/login 2>&1 | grep -E "Set-Cookie|Location|< HTTP"

echo -e "\n=== TEST 3: Acceso a inscripciones (debería funcionar) ==="
curl -v -b $COOKIES http://localhost:8080/turismouy.UI/inscriptions 2>&1 | grep "< HTTP"

echo -e "\n=== TEST 4: Logout ==="
curl -v -b $COOKIES -c $COOKIES http://localhost:8080/turismouy.UI/logout 2>&1 | grep -E "Set-Cookie|Location"

echo -e "\n=== TEST 5: Acceso a inscripciones después de logout (debería redirigir) ==="
curl -v -b $COOKIES http://localhost:8080/turismouy.UI/inscriptions 2>&1 | grep -E "Location|< HTTP"

# Limpiar
rm $COOKIES
```

### Script 2: Test de Roles con Selenium (Java)

```java
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.jupiter.api.Assertions.*;

public class RoleBasedAccessTest {
    
    private WebDriver driver;
    private String baseUrl = "http://localhost:8080/turismouy.UI";
    
    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
    }
    
    @AfterEach
    void tearDown() {
        driver.quit();
    }
    
    @Test
    void testGuestCannotAccessInscriptions() {
        // Acceder como invitado
        driver.get(baseUrl + "/home");
        
        // Intentar acceder a inscripciones
        driver.get(baseUrl + "/inscriptions");
        
        // Verificar redirección a login
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }
    
    @Test
    void testTouristCanAccessInscriptions() {
        // Login como turista
        driver.get(baseUrl + "/login");
        driver.findElement(By.name("nickname-or-email")).sendKeys("maria");
        driver.findElement(By.name("password")).sendKeys("pass123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Acceder a inscripciones
        driver.get(baseUrl + "/inscriptions");
        
        // Verificar que NO redirige
        assertTrue(driver.getCurrentUrl().contains("/inscriptions"));
        
        // Verificar que muestra contenido
        assertTrue(driver.getPageSource().contains("Inscripciones"));
    }
    
    @Test
    void testSupplierCanCreateActivity() {
        // Login como proveedor
        driver.get(baseUrl + "/login");
        driver.findElement(By.name("nickname-or-email")).sendKeys("juan_proveedor");
        driver.findElement(By.name("password")).sendKeys("pass123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Ir a actividades
        driver.get(baseUrl + "/activities");
        
        // Verificar que botón "Nueva Actividad" está visible
        assertTrue(driver.findElement(By.id("btnNuevaActividad")).isDisplayed());
    }
    
    @Test
    void testTouristCannotCreateActivity() {
        // Login como turista
        driver.get(baseUrl + "/login");
        driver.findElement(By.name("nickname-or-email")).sendKeys("maria");
        driver.findElement(By.name("password")).sendKeys("pass123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Intentar acceder a formulario de creación
        driver.get(baseUrl + "/activities?action=showCreateForm");
        
        // Verificar página de error 403
        assertTrue(driver.getPageSource().contains("403") || 
                   driver.getPageSource().contains("Acceso Denegado"));
    }
}
```

---

## 📈 MÉTRICAS Y MONITOREO

### Contadores de Sesiones

**Agregar en SessionManagementFilter**:

```java
public class SessionManagementFilter implements Filter {
    
    private static final AtomicInteger guestSessions = new AtomicInteger(0);
    private static final AtomicInteger authenticatedSessions = new AtomicInteger(0);
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        
        if (session == null) {
            SessionUtils.initGuestSession(httpRequest);
            guestSessions.incrementAndGet();
            LOGGER.info("Sesiones GUEST activas: " + guestSessions.get());
        }
        
        chain.doFilter(request, response);
    }
    
    // Método estático para obtener métricas
    public static String getMetrics() {
        return String.format("GUEST: %d, AUTH: %d", 
            guestSessions.get(), 
            authenticatedSessions.get()
        );
    }
}
```

### Servlet de Métricas (Opcional)

```java
@WebServlet("/admin/metrics")
public class MetricsServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Solo permitir en desarrollo o con autenticación admin
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String metrics = String.format(
            "{\"guest_sessions\": %d, \"authenticated_sessions\": %d}",
            // obtener de contadores
        );
        
        response.getWriter().write(metrics);
    }
}
```

---

**Documento creado por:** Equipo TurismoUY  
**Versión:** 1.0.0  
**Fecha:** Octubre 2025  
**Estado:** ✅ Ejemplos Completos y Listos para Uso

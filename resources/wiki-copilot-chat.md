
# Wiki: Autenticación, Roles e Invitados (JSP/Servlet) — a partir de `chat.json`

> Documento unificado para la wiki, listo para Notion/GitHub/Confluence. Todo el contenido proviene del `chat.json` adjunto.

---

## Índice
1. [Contexto](#contexto)
2. [Resumen Ejecutivo](#resumen-ejecutivo)
3. [Roles y Estados de Sesión](#roles-y-estados-de-sesión)
4. [Flujos por Rol (Diagramas)](#flujos-por-rol-diagramas)
5. [Navegación/UI (JSP con JSTL)](#navegaciónui-jsp-con-jstl)
6. [Filtro de Autenticación con Roles](#filtro-de-autenticación-con-roles)
7. [Inicio como Invitado (`handleGuestLogin`)](#inicio-como-invitado-handleguestlogin)
8. [Forward vs Redirect (Diagrama)](#forward-vs-redirect-diagrama)
9. [Secuencia: Filter → Servlet → JSP](#secuencia-filter--servlet--jsp)
10. [Matriz de Acceso (implícita en snippets)](#matriz-de-acceso-implícita-en-snippets)

---

## Contexto
Basado en la sesión de Copilot exportada como `chat.json`. Se trabaja sobre autenticación en aplicaciones **JSP/Servlet** con **manejo de sesión**, **roles** y el modo **invitado**. 【turn3file10†chat.json†L1-L32】

---

## Resumen Ejecutivo
- Se define un **AuthenticationFilter** que clasifica **URLs públicas**, **accesibles a invitados**, **restringidas a autenticados**, y **exclusivas por rol** (TOURIST/SUPPLIER). Bloquea el acceso no autorizado y redirige/forwarda a login cuando corresponde. 【turn3file17†chat.json†L11-L39】【turn3file8†chat.json†L12-L40】
- El modo **invitado** se gestiona con sesión liviana: `guest_mode=true`, `user_role="GUEST"`, sin `logged_user`, con **timeout corto** (ej. 600s). 【turn3file0†chat.json†L1-L18】【turn3file4†chat.json†L23-L42】
- La **UI (JSP + JSTL)** oculta/expone enlaces según rol, mostrando badge de “👤 Invitado” y botón **Iniciar Sesión** cuando corresponde. 【turn3file3†chat.json†L19-L58】

---

## Roles y Estados de Sesión
- **Invitado (Guest)**: sesión con `guest_mode=true`, `user_role="GUEST"`, **sin** `logged_user`. Permite navegación pública (Home, Actividades, Salidas, Ranking); prohíbe acciones protegidas (inscripciones, edición, creación). 【turn3file0†chat.json†L1-L18】【turn3file16†chat.json†L1-L22】
- **Usuario autenticado**: sesión con `logged_user` (tipo `DtUser`) y `userType` (TOURIST o SUPPLIER). El Filter valida además rutas exclusivas por rol. 【turn3file10†chat.json†L17-L32】【turn3file1†chat.json†L1-L34】

---

## Flujos por Rol (Diagramas)

### Flujo Invitado / Turista / Proveedor
```text
┌─────────────────────────────────────────────────────────────┐
│                    FLUJO GUEST                              │
├─────────────────────────────────────────────────────────────┤
│  1. Usuario hace clic "Ingresar como invitado"             │
│  2. handleGuestLogin() crea sesión con guest_mode=true     │
│  3. Redirect a /home                                        │
│  4. Puede ver: actividades, salidas, ranking               │
│  5. NO puede: inscribirse, modificar perfil, crear         │
│  6. Navbar muestra: "Invitado" + botón "Iniciar Sesión"    │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                    FLUJO TOURIST                            │
├─────────────────────────────────────────────────────────────┤
│  1. Usuario hace login como Tourist                         │
│  2. handleLogin() crea sesión con logged_user (DtTourist)  │
│  3. Redirect a /home                                        │
│  4. Puede ver: todo lo de guest + inscripciones            │
│  5. Puede hacer: inscribirse, modificar perfil             │
│  6. NO puede: crear actividades                            │
│  7. Navbar muestra: "🏖️ Turista" + nombre                  │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                    FLUJO SUPPLIER                           │
├─────────────────────────────────────────────────────────────┤
│  1. Usuario hace login como Supplier                        │
│  2. handleLogin() crea sesión con logged_user (DtSupplier) │
│  3. Redirect a /home                                        │
│  4. Puede ver: todo lo de guest                            │
│  5. Puede hacer: crear actividades, salidas, modificar     │
│  6. NO puede: inscribirse (no es turista)                  │
│  7. Navbar muestra: "🏢 Proveedor" + nombre                │
└─────────────────────────────────────────────────────────────┘
```
【turn3file7†chat.json†L1-L22】【turn3file16†chat.json†L1-L22】

---

## Navegación/UI (JSP con JSTL)
Fragmento de **navbar** con visibilidad condicional por rol y badge de invitado:
```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar">
  <div class="navbar-brand">TurismoUY</div>
  <div class="navbar-menu">
    <!-- Público (incluye guest) -->
    <a href="/home">Inicio</a>
    <a href="/consult-activities">Actividades</a>
    <a href="/consult-outings">Salidas</a>
    <a href="/activity-ranking">Ranking</a>

    <!-- Solo TOURIST -->
    <c:if test="${not empty sessionScope.logged_user and 
                  sessionScope.logged_user.userType eq 'TOURIST'}">
      <a href="/my-inscriptions">Mis Inscripciones</a>
      <a href="/inscription-tourist-outing">Inscribirse</a>
    </c:if>

    <!-- Solo SUPPLIER -->
    <c:if test="${not empty sessionScope.logged_user and 
                  sessionScope.logged_user.userType eq 'SUPPLIER'}">
      <a href="/my-activities">Mis Actividades</a>
      <a href="/create-activity">Crear Actividad</a>
      <a href="/create-tourist-outing">Crear Salida</a>
    </c:if>

    <!-- Solo autenticados (no guest) -->
    <c:if test="${not empty sessionScope.logged_user}">
      <a href="/profile">Mi Perfil</a>
      <a href="/modify-data-user">Editar Perfil</a>
      <a href="/logout">Cerrar Sesión</a>
    </c:if>

    <!-- Solo invitado -->
    <c:if test="${empty sessionScope.logged_user and 
                  not empty sessionScope.guest_mode}">
      <span class="guest-badge">👤 Invitado</span>
      <a href="/login" class="btn-login">Iniciar Sesión</a>
    </c:if>
  </div>
</nav>
```
【turn3file3†chat.json†L19-L58】

---

## Filtro de Autenticación con Roles
**Estructura clave del `AuthenticationFilter`** (públicas, guest, autenticadas, por rol):
```java
// Detección de sesión y modo invitado
HttpSession session = request.getSession(false);
DtUser loggedUser = (session != null) ? (DtUser) session.getAttribute("logged_user") : null;
boolean isGuest = (session != null) && (session.getAttribute("guest_mode") != null);

// URLs guest
if (matchesAny(path, GUEST_URLS)) {
    if (loggedUser != null || isGuest) {
        chain.doFilter(request, response);
        return;
    } else {
        response.sendRedirect(contextPath + "/login");
        return;
    }
}

// URLs que requieren login (no guest)
if (matchesAny(path, AUTHENTICATED_URLS)) {
    if (loggedUser == null) {
        request.setAttribute("loginError", "Debes iniciar sesión para acceder a esta página");
        request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp").forward(request, response);
        return;
    }
    chain.doFilter(request, response);
    return;
}

// Exclusivas TOURIST
if (matchesAny(path, TOURIST_ONLY_URLS)) {
    if (loggedUser == null) {
        request.setAttribute("loginError", "Debes ser un turista registrado para realizar esta acción");
        request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp").forward(request, response);
        return;
    }
    if (loggedUser.getUserType() != UserType.TOURIST) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Esta funcionalidad es solo para turistas");
        return;
    }
    chain.doFilter(request, response);
    return;
}

// Exclusivas SUPPLIER
if (matchesAny(path, SUPPLIER_ONLY_URLS)) {
    if (loggedUser == null) {
        request.setAttribute("loginError", "Debes ser un proveedor registrado para realizar esta acción");
        request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp").forward(request, response);
        return;
    }
    if (loggedUser.getUserType() != UserType.SUPPLIER) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Esta funcionalidad es solo para proveedores");
        return;
    }
    chain.doFilter(request, response);
    return;
}

// No categorizada → requiere autenticación salvo invitado
if (loggedUser == null && !isGuest) {
    response.sendRedirect(contextPath + "/login");
    return;
}

chain.doFilter(request, response);
```
【turn3file17†chat.json†L1-L39】【turn3file8†chat.json†L1-L40】【turn3file1†chat.json†L1-L34】

> **Notas**: Incluye método utilitario `matchesAny(path, urls)` y ciclo de vida `init/destroy` del Filter. 【turn3file1†chat.json†L35-L65】

---

## Inicio como Invitado (`handleGuestLogin`)
```java
// En Login.java
protected void handleGuestLogin(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    session.setAttribute("guest_mode", true);
    session.setAttribute("user_role", "GUEST");
    // No establecer logged_user (solo para autenticados)
    session.setMaxInactiveInterval(600); // 10 minutos
    System.out.println(">>> Usuario ingresó como invitado");
    response.sendRedirect(request.getContextPath() + "/home");
}
```
【turn3file0†chat.json†L1-L18】【turn3file4†chat.json†L23-L42】

---

## Forward vs Redirect (Diagrama)
```text
┌─────────────────────────────────────────────────────────────┐
│                    FORWARD (Server-side)                    │
├─────────────────────────────────────────────────────────────┤
│  [Navegador] ──── POST /login ────> [LoginServlet]          │
│                                         │                   │
│                           forward("/home.jsp")              │
│                                         │                   │
│                                    [home.jsp]               │
│  [Navegador] <──── HTML Response ──────┘                   │
│  ✅ URL en navegador: /login (NO cambia)                    │
│  ✅ Request/Response: MISMO objeto                          │
│  ✅ Atributos: Se mantienen                                 │
│  ✅ Velocidad: Rápido (server-side)                         │
│  ❌ Refrescar F5: RE-ENVÍA el POST                          │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                    REDIRECT (Client-side)                   │
├─────────────────────────────────────────────────────────────┤
│  [Navegador] ──── POST /login ────> [LoginServlet]          │
│                           response.sendRedirect("/home")    │
│  [Navegador] <──── 302 Found ─────┘                         │
│  [Navegador] ──── GET /home ────> [home.jsp]                │
│  ✅ URL en navegador: /home                                 │
│  ✅ No re-envía el POST al refrescar                        │
└─────────────────────────────────────────────────────────────┘
```
【turn3file9†chat.json†L1-L22】【turn3file15†chat.json†L1-L18】

---

## Secuencia: Filter → Servlet → JSP
```text
┌─────────────────────────────────────────────────────────────┐
│                   TOMCAT SERVER                             │
├─────────────────────────────────────────────────────────────┤
│  [1] ⚡ FILTER (AuthenticationFilter)                       │
│      ├─→ Obtener sesión: getSession(false)                  │
│      ├─→ Verificar logged_user / URL pública vs protegida   │
│      └─→ ✅ chain.doFilter() | ❌ redirect("/login")        │
│  [2] 📄 SERVLET (ModifyDataUser)                             │
│      └─→ Procesar request                                   │
│  [3] 📄 JSP (modify-data-user.jsp)                           │
│      └─→ Renderizar HTML                                    │
└─────────────────────────────────────────────────────────────┘
```
【turn3file11†chat.json†L1-L22】【turn3file18†chat.json†L1-L22】

---

## Matriz de Acceso (implícita en snippets)
- **Públicas**: `/login`, `/IniciarSesionRegistrarse.jsp`, `/AccedeAlHome.jsp`, `/CerrarSesion.jsp`, estáticos (`/css/`, `/js/`, `/images/`, `/uploads/`, `/favicon.ico`). 【turn3file10†chat.json†L17-L32】
- **Guest permitidas**: `GUEST_URLS` (p. ej., Home/Consultas/Ranking según navbar). Requiere `isGuest` **o** `logged_user` presente. 【turn3file17†chat.json†L23-L39】【turn3file3†chat.json†L39-L58】
- **Autenticadas (no guest)**: `AUTHENTICATED_URLS`. Si no hay `logged_user` → forward a `IniciarSesionRegistrarse.jsp`. 【turn3file1†chat.json†L1-L18】
- **Solo TOURIST**: `TOURIST_ONLY_URLS` + verificación `UserType.TOURIST`. **403** si no corresponde. 【turn3file1†chat.json†L18-L34】
- **Solo SUPPLIER**: `SUPPLIER_ONLY_URLS` + verificación `UserType.SUPPLIER`. **403** si no corresponde. 【turn3file1†chat.json†L34-L52】

---

> Fin del documento. Todas las secciones y ejemplos se extrajeron del `chat.json` adjunto.

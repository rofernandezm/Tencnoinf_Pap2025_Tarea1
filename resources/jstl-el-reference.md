# JSTL y EL - Guía de Referencia para JSP

## 📋 Índice
1. [Configuración Inicial](#configuración-inicial)
2. [Expression Language (EL)](#expression-language-el)
3. [JSTL Core Tags](#jstl-core-tags)
4. [JSTL Formatting Tags](#jstl-formatting-tags)
5. [JSTL Functions](#jstl-functions)
6. [Ejemplos Prácticos](#ejemplos-prácticos)

---

## Configuración Inicial

### Dependencia Maven (para Tomcat 10/11 - Jakarta EE)
```xml
<dependency>
    <groupId>jakarta.servlet.jsp.jstl</groupId>
    <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>org.glassfish.web</groupId>
    <artifactId>jakarta.servlet.jsp.jstl</artifactId>
    <version>3.0.1</version>
</dependency>
```

### Dependencia Maven (para Tomcat 9 o anterior - Java EE)
```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>
```

### Declaración de Taglibs en JSP

**Para Tomcat 10/11 (Jakarta EE):**
```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
```

**Para Tomcat 9 o anterior:**
```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
```

---

## Expression Language (EL)

### Sintaxis Básica
```jsp
${expresion}          <!-- Imprime el resultado -->
#{expresion}          <!-- Deferred evaluation (JSF) -->
```

### Acceso a Objetos

#### Variables de Ámbito (Scopes)
```jsp
${pageScope.variable}        <!-- Solo en la página actual -->
${requestScope.variable}     <!-- Disponible durante el request -->
${sessionScope.variable}     <!-- Disponible durante la sesión -->
${applicationScope.variable} <!-- Disponible en toda la aplicación -->

<!-- Si no se especifica scope, busca en orden: page → request → session → application -->
${variable}
```

#### Propiedades de Objetos
```jsp
${usuario.nombre}           <!-- Llama a usuario.getNombre() -->
${usuario.edad}             <!-- Llama a usuario.getEdad() -->
${usuario["nombre"]}        <!-- Sintaxis alternativa con corchetes -->
```

#### Mapas y Colecciones
```jsp
${mapa["clave"]}           <!-- Acceso a Map -->
${mapa.clave}              <!-- Sintaxis alternativa -->
${lista[0]}                <!-- Acceso a List por índice -->
${array[2]}                <!-- Acceso a array -->
```

### Operadores

#### Aritméticos
```jsp
${a + b}    ${a - b}    ${a * b}    ${a / b}    ${a % b}
${a div b}  ${a mod b}  <!-- Sintaxis alternativa -->
```

#### Relacionales
```jsp
${a == b}   ${a eq b}   <!-- Igual -->
${a != b}   ${a ne b}   <!-- Diferente -->
${a < b}    ${a lt b}   <!-- Menor que -->
${a > b}    ${a gt b}   <!-- Mayor que -->
${a <= b}   ${a le b}   <!-- Menor o igual -->
${a >= b}   ${a ge b}   <!-- Mayor o igual -->
```

#### Lógicos
```jsp
${a && b}   ${a and b}  <!-- AND -->
${a || b}   ${a or b}   <!-- OR -->
${!a}       ${not a}    <!-- NOT -->
```

#### Especiales
```jsp
${empty variable}       <!-- true si es null, vacío o tamaño 0 -->
${not empty variable}   <!-- true si tiene contenido -->
${variable ? 'si' : 'no'}  <!-- Operador ternario -->
```

### Objetos Implícitos
```jsp
${pageContext}              <!-- javax.servlet.jsp.PageContext -->
${pageContext.request}      <!-- HttpServletRequest -->
${pageContext.response}     <!-- HttpServletResponse -->
${pageContext.session}      <!-- HttpSession -->

${param.nombreParametro}    <!-- request.getParameter("nombreParametro") -->
${paramValues.nombre[0]}    <!-- request.getParameterValues("nombre") -->

${header.Accept}            <!-- request.getHeader("Accept") -->
${headerValues.Accept[0]}   <!-- request.getHeaders("Accept") -->

${cookie.sessionId.value}   <!-- Cookie por nombre -->

${initParam.configParam}    <!-- ServletContext.getInitParameter() -->
```

---

## JSTL Core Tags

### c:out - Imprimir Valores
```jsp
<c:out value="${usuario.nombre}" />
<c:out value="${usuario.nombre}" default="Sin nombre" />
<c:out value="${usuario.nombre}" escapeXml="false" />
```

### c:set - Establecer Variables
```jsp
<!-- Variable simple -->
<c:set var="mensaje" value="Hola mundo" />
<c:set var="contador" value="0" scope="session" />

<!-- Propiedad de objeto -->
<c:set target="${usuario}" property="nombre" value="Juan" />

<!-- Con contenido del cuerpo -->
<c:set var="html">
  <p>Contenido HTML complejo</p>
</c:set>
```

### c:remove - Eliminar Variables
```jsp
<c:remove var="usuario" scope="session" />
```

### c:if - Condicional Simple
```jsp
<c:if test="${edad >= 18}">
  <p>Eres mayor de edad</p>
</c:if>

<!-- Con variable de resultado -->
<c:if test="${edad >= 18}" var="esMayor" />
<c:out value="${esMayor}" />
```

### c:choose/c:when/c:otherwise - Condicional Múltiple
```jsp
<c:choose>
  <c:when test="${edad < 13}">
    <p>Niño</p>
  </c:when>
  <c:when test="${edad < 18}">
    <p>Adolescente</p>
  </c:when>
  <c:when test="${edad >= 65}">
    <p>Jubilado</p>
  </c:when>
  <c:otherwise>
    <p>Adulto</p>
  </c:otherwise>
</c:choose>
```

### c:forEach - Iteración
```jsp
<!-- Iterar sobre colección -->
<c:forEach var="usuario" items="${listaUsuarios}">
  <p>${usuario.nombre}</p>
</c:forEach>

<!-- Con información de estado -->
<c:forEach var="usuario" items="${listaUsuarios}" varStatus="status">
  <p>${status.index}: ${usuario.nombre} 
     (${status.first ? 'primero' : ''} ${status.last ? 'último' : ''})</p>
</c:forEach>

<!-- Rango numérico -->
<c:forEach var="i" begin="1" end="10" step="2">
  <p>Número: ${i}</p>
</c:forEach>
```

**Propiedades de varStatus:**
- `index` - Índice actual (empieza en 0)
- `count` - Contador de iteración (empieza en 1)
- `first` - true si es la primera iteración
- `last` - true si es la última iteración
- `begin` - Valor del atributo begin
- `end` - Valor del atributo end
- `step` - Valor del atributo step

### c:forTokens - Iteración sobre Tokens
```jsp
<c:forTokens var="token" items="rojo,verde,azul" delims=",">
  <p>Color: ${token}</p>
</c:forTokens>
```

### c:url - Construcción de URLs
```jsp
<c:url var="editUrl" value="/usuario/editar">
  <c:param name="id" value="${usuario.id}" />
  <c:param name="return" value="lista" />
</c:url>
<a href="${editUrl}">Editar</a>
<!-- Resultado: /usuario/editar?id=123&return=lista -->
```

### c:redirect - Redirección
```jsp
<c:redirect url="/login.jsp">
  <c:param name="error" value="session_expired" />
</c:redirect>
```

### c:import - Importar Contenido
```jsp
<c:import url="/includes/header.jsp" />
<c:import url="http://example.com/api/data" var="apiResponse" />
```

### c:catch - Manejo de Excepciones
```jsp
<c:catch var="error">
  ${1/0}
</c:catch>
<c:if test="${not empty error}">
  <p>Error: ${error.message}</p>
</c:if>
```

---

## JSTL Formatting Tags

### fmt:formatDate - Formateo de Fechas
```jsp
<%@ page import="java.util.Date" %>
<c:set var="ahora" value="<%= new Date() %>" />

<fmt:formatDate value="${ahora}" pattern="dd/MM/yyyy" />
<fmt:formatDate value="${ahora}" pattern="HH:mm:ss" />
<fmt:formatDate value="${ahora}" pattern="dd/MM/yyyy HH:mm:ss" />

<!-- Estilos predefinidos -->
<fmt:formatDate value="${ahora}" type="date" dateStyle="short" />
<fmt:formatDate value="${ahora}" type="date" dateStyle="medium" />
<fmt:formatDate value="${ahora}" type="date" dateStyle="long" />
<fmt:formatDate value="${ahora}" type="date" dateStyle="full" />
```

### fmt:formatNumber - Formateo de Números
```jsp
<fmt:formatNumber value="${precio}" type="currency" />
<fmt:formatNumber value="${porcentaje}" type="percent" />
<fmt:formatNumber value="${numero}" pattern="#,##0.00" />
<fmt:formatNumber value="${numero}" minFractionDigits="2" maxFractionDigits="4" />
```

### fmt:parseDate - Parsear Fechas
```jsp
<fmt:parseDate value="25/12/2025" pattern="dd/MM/yyyy" var="navidad" />
<fmt:formatDate value="${navidad}" pattern="EEEE, dd 'de' MMMM 'de' yyyy" />
```

### fmt:parseNumber - Parsear Números
```jsp
<fmt:parseNumber value="1.234,56" pattern="#,##0.00" var="numero" />
<fmt:parseNumber value="$125.50" type="currency" var="precio" />
```

### fmt:setLocale - Establecer Localización
```jsp
<fmt:setLocale value="es_UY" />
<fmt:setLocale value="en_US" />
```

### fmt:bundle / fmt:message - Internacionalización
```jsp
<!-- Usando un ResourceBundle -->
<fmt:bundle basename="messages">
  <fmt:message key="welcome.message" />
  <fmt:message key="user.greeting">
    <fmt:param value="${usuario.nombre}" />
  </fmt:message>
</fmt:bundle>
```

---

## JSTL Functions

### Funciones de String
```jsp
${fn:length(string)}                  <!-- Longitud -->
${fn:toUpperCase(string)}             <!-- A mayúsculas -->
${fn:toLowerCase(string)}             <!-- A minúsculas -->
${fn:substring(string, 0, 5)}         <!-- Subcadena -->
${fn:substringBefore(string, ',')}    <!-- Antes del delimitador -->
${fn:substringAfter(string, ',')}     <!-- Después del delimitador -->
${fn:trim(string)}                    <!-- Quitar espacios -->
${fn:replace(string, 'a', 'b')}       <!-- Reemplazar -->
${fn:indexOf(string, 'texto')}        <!-- Posición de subcadena -->
${fn:startsWith(string, 'pre')}       <!-- Empieza con -->
${fn:endsWith(string, 'post')}        <!-- Termina con -->
${fn:contains(string, 'texto')}       <!-- Contiene -->
${fn:containsIgnoreCase(string, 'TEXTO')}  <!-- Contiene (sin case) -->
```

### Funciones de Colecciones
```jsp
${fn:length(coleccion)}               <!-- Tamaño -->
${fn:join(array, ', ')}               <!-- Unir con separador -->
${fn:split(string, ',')}              <!-- Dividir por separador -->
```

### Funciones de Escape
```jsp
${fn:escapeXml(string)}               <!-- Escapar XML/HTML -->
```

---

## Ejemplos Prácticos

### Tabla de Usuarios con Acciones
```jsp
<table class="table">
  <thead>
    <tr>
      <th>#</th>
      <th>Nombre</th>
      <th>Email</th>
      <th>Estado</th>
      <th>Acciones</th>
    </tr>
  </thead>
  <tbody>
    <c:choose>
      <c:when test="${empty listaUsuarios}">
        <tr>
          <td colspan="5" class="text-center">No hay usuarios</td>
        </tr>
      </c:when>
      <c:otherwise>
        <c:forEach var="usuario" items="${listaUsuarios}" varStatus="status">
          <tr>
            <td>${status.count}</td>
            <td>${fn:escapeXml(usuario.nombre)}</td>
            <td>${fn:toLowerCase(usuario.email)}</td>
            <td>
              <c:choose>
                <c:when test="${usuario.activo}">
                  <span class="badge bg-success">Activo</span>
                </c:when>
                <c:otherwise>
                  <span class="badge bg-secondary">Inactivo</span>
                </c:otherwise>
              </c:choose>
            </td>
            <td>
              <c:url var="editUrl" value="/usuario/editar">
                <c:param name="id" value="${usuario.id}" />
              </c:url>
              <c:url var="deleteUrl" value="/usuario/eliminar">
                <c:param name="id" value="${usuario.id}" />
              </c:url>
              <a href="${editUrl}" class="btn btn-sm btn-primary">Editar</a>
              <a href="${deleteUrl}" class="btn btn-sm btn-danger" 
                 onclick="return confirm('¿Confirmar eliminación?')">Eliminar</a>
            </td>
          </tr>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </tbody>
</table>
```

### Formulario con Validación de Errores
```jsp
<c:if test="${not empty errores}">
  <div class="alert alert-danger">
    <ul>
      <c:forEach var="error" items="${errores}">
        <li>${fn:escapeXml(error)}</li>
      </c:forEach>
    </ul>
  </div>
</c:if>

<form action="<c:url value='/usuario/guardar' />" method="post">
  <div class="mb-3">
    <label>Nombre:</label>
    <input type="text" name="nombre" value="${param.nombre}" 
           class="form-control ${not empty errores ? 'is-invalid' : ''}" />
  </div>
  
  <div class="mb-3">
    <label>Tipo de Usuario:</label>
    <select name="tipo" class="form-control">
      <option value="">Seleccione...</option>
      <c:forEach var="tipo" items="${tiposUsuario}">
        <option value="${tipo.id}" 
                ${param.tipo eq tipo.id ? 'selected' : ''}>
          ${tipo.nombre}
        </option>
      </c:forEach>
    </select>
  </div>
  
  <button type="submit" class="btn btn-primary">Guardar</button>
</form>
```

### Paginación
```jsp
<c:if test="${totalPaginas > 1}">
  <nav>
    <ul class="pagination">
      <!-- Anterior -->
      <li class="page-item ${paginaActual == 1 ? 'disabled' : ''}">
        <c:url var="prevUrl" value="/usuarios">
          <c:param name="pagina" value="${paginaActual - 1}" />
        </c:url>
        <a class="page-link" href="${prevUrl}">Anterior</a>
      </li>
      
      <!-- Números de página -->
      <c:forEach var="i" begin="1" end="${totalPaginas}">
        <li class="page-item ${i == paginaActual ? 'active' : ''}">
          <c:url var="pageUrl" value="/usuarios">
            <c:param name="pagina" value="${i}" />
          </c:url>
          <a class="page-link" href="${pageUrl}">${i}</a>
        </li>
      </c:forEach>
      
      <!-- Siguiente -->
      <li class="page-item ${paginaActual == totalPaginas ? 'disabled' : ''}">
        <c:url var="nextUrl" value="/usuarios">
          <c:param name="pagina" value="${paginaActual + 1}" />
        </c:url>
        <a class="page-link" href="${nextUrl}">Siguiente</a>
      </li>
    </ul>
  </nav>
</c:if>
```

### Tarjetas de Productos con Precios
```jsp
<div class="row">
  <c:forEach var="producto" items="${productos}">
    <div class="col-md-4 mb-4">
      <div class="card">
        <img src="<c:url value='/images/${producto.imagen}' />" 
             class="card-img-top" alt="${fn:escapeXml(producto.nombre)}">
        <div class="card-body">
          <h5 class="card-title">${fn:escapeXml(producto.nombre)}</h5>
          <p class="card-text">${fn:substring(producto.descripcion, 0, 100)}...</p>
          <p class="card-text">
            <c:choose>
              <c:when test="${producto.descuento > 0}">
                <span class="text-muted text-decoration-line-through">
                  <fmt:formatNumber value="${producto.precio}" type="currency" />
                </span>
                <br>
                <span class="text-danger fw-bold">
                  <fmt:formatNumber value="${producto.precioConDescuento}" type="currency" />
                </span>
                <span class="badge bg-danger">
                  -<fmt:formatNumber value="${producto.descuento}" type="percent" />
                </span>
              </c:when>
              <c:otherwise>
                <span class="fw-bold">
                  <fmt:formatNumber value="${producto.precio}" type="currency" />
                </span>
              </c:otherwise>
            </c:choose>
          </p>
          <c:url var="detalleUrl" value="/producto/detalle">
            <c:param name="id" value="${producto.id}" />
          </c:url>
          <a href="${detalleUrl}" class="btn btn-primary">Ver Detalle</a>
        </div>
      </div>
    </div>
  </c:forEach>
</div>
```

### Menú de Navegación Dinámico
```jsp
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="<c:url value='/' />">TurismoUY</a>
    <ul class="navbar-nav">
      <c:forEach var="item" items="${menuItems}">
        <li class="nav-item">
          <c:url var="itemUrl" value="${item.url}" />
          <a class="nav-link ${pageContext.request.requestURI eq itemUrl ? 'active' : ''}" 
             href="${itemUrl}">
            ${item.nombre}
          </a>
        </li>
      </c:forEach>
      
      <c:choose>
        <c:when test="${not empty sessionScope.usuario}">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
              ${sessionScope.usuario.nombre}
            </a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="<c:url value='/perfil' />">Mi Perfil</a></li>
              <li><hr class="dropdown-divider"></li>
              <li><a class="dropdown-item" href="<c:url value='/logout' />">Cerrar Sesión</a></li>
            </ul>
          </li>
        </c:when>
        <c:otherwise>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/login' />">Iniciar Sesión</a>
          </li>
        </c:otherwise>
      </c:choose>
    </ul>
  </div>
</nav>
```

### Manejo de Mensajes Flash
```jsp
<c:if test="${not empty sessionScope.mensajeExito}">
  <div class="alert alert-success alert-dismissible fade show">
    ${fn:escapeXml(sessionScope.mensajeExito)}
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
  </div>
  <c:remove var="mensajeExito" scope="session" />
</c:if>

<c:if test="${not empty sessionScope.mensajeError}">
  <div class="alert alert-danger alert-dismissible fade show">
    ${fn:escapeXml(sessionScope.mensajeError)}
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
  </div>
  <c:remove var="mensajeError" scope="session" />
</c:if>
```

---

## Tips y Buenas Prácticas

1. **Siempre escapa el HTML de usuario**: Usa `${fn:escapeXml()}` o `<c:out>` para prevenir XSS
2. **Prefiere EL sobre scriptlets**: `${variable}` en lugar de `<%= variable %>`
3. **Usa c:url para URLs**: Maneja automáticamente context path y encoding
4. **Valida colecciones vacías**: Usa `${empty coleccion}` antes de iterar
5. **Scopes explícitos**: Especifica el scope cuando sea necesario para claridad
6. **Operadores textuales**: Usa `eq`, `lt`, `gt`, etc. para mejor legibilidad
7. **No abuses de c:set**: Si necesitas lógica compleja, hazlo en el servlet/controlador
8. **Internacionalización**: Usa `fmt:message` para textos multiidioma desde el inicio
9. **Formatos consistentes**: Define patrones de fecha/número estándar en tu aplicación
10. **Mensajes flash**: Usa session scope para mensajes y `c:remove` después de mostrarlos

---

## Comparación rápida: JSTL vs Scriptlets

| Tarea | ❌ Scriptlet (Evitar) | ✅ JSTL/EL |
|-------|---------------------|-----------|
| Imprimir variable | `<%= usuario.getNombre() %>` | `${usuario.nombre}` |
| Condicional | `<% if (edad >= 18) { %>...` | `<c:if test="${edad >= 18}">` |
| Bucle | `<% for (Usuario u : lista) { %>` | `<c:forEach var="u" items="${lista}">` |
| Formatear fecha | `<%= new SimpleDateFormat(...).format(fecha) %>` | `<fmt:formatDate value="${fecha}" pattern="..." />` |

---

**Versión**: 1.0  
**Fecha**: Octubre 2025  
**Proyecto**: TurismoUY App

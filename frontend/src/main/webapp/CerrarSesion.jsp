<%@ page session="false" %>
<%
    // Obtener la sesión, si existe
    HttpSession session = request.getSession(false);

    if (session != null) {
        // Invalida la sesión
        session.invalidate();
    }

    // Redirige al login o a la página principal
    response.sendRedirect("/IniciarSesionRegistrarse.jsp");
%>
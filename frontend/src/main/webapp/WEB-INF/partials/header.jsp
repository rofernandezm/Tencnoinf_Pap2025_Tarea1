<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  // Recibo qué item va activo (activities | outings | inscriptions)
  String active = (String) request.getAttribute("navActive");
  if (active == null) active = "";
  String ctx = request.getContextPath();
%>

<header>
  <nav class="navbar navbar-expand-lg fixed-top py-0">
    <div class="container-fluid">
      <a class="navbar-brand me-auto" href="<%=ctx%>/">TurismoUy</a>

      <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar"
           aria-labelledby="offcanvasNavbarLabel">
        <div class="offcanvas-header">
          <h5 class="offcanvas-title" id="offcanvasNavbarLabel">TurismoUy</h5>
          <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>

        <div class="offcanvas-body">
          <ul class="navbar-nav justify-content-center flex-grow-1 pe-3 fs-5">
            <li class="nav-item">
              <a class="nav-link px-lg-2 <%= "activities".equals(active) ? "active" : "" %>"
                 href="<%=ctx%>/activities">Actividades</a>
            </li>
            <li class="nav-item">
              <a class="nav-link px-lg-2 <%= "outings".equals(active) ? "active" : "" %>"
                 href="<%=ctx%>/outings">Salidas</a>
            </li>
            <li class="nav-item">
              <a class="nav-link px-lg-2 <%= "inscriptions".equals(active) ? "active" : "" %>"
                 href="<%=ctx%>/inscriptions">Inscripción</a>
            </li>
          </ul>
        </div>
      </div>

      <a href="#" class="login-button"><i class="bi bi-person-circle"></i></a>
      <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas"
              data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
    </div>
  </nav>
</header>

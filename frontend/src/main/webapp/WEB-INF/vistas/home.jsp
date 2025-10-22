<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="turismouyapp.core.dto.DtTouristActivity"%>
<%
String ctx = request.getContextPath();
String activityImgPath = ctx + "/activity_img";

List<DtTouristActivity> activities = (List<DtTouristActivity>) request.getAttribute("confirmedActivities");
boolean hasActivities = activities != null && !activities.isEmpty();
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="icon" type="image/png" href="<%=ctx%>/res/turismouyAppIcon.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="<%=ctx%>/assets/css/app.css">
    <style>
        .carousel-item {
            height: 500px;
        }

        .carousel-item>img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            display: block;
        }
    </style>

</head>

<body class="d-flex flex-column min-vh-100">

    <!-- Navbar -->
    <%
    request.setAttribute("navActive", "home"); // activities | outings | inscriptions | home
    %>
    <jsp:include page="/WEB-INF/partials/header.jsp" />
    <!-- End Navbar-->

    <!-- INICIO CARROUSEL -->
    <% if (hasActivities) { %>
    <div id="carouselActividades" class="carousel slide my-5 mt-5" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <% for (int i = 0; i < activities.size(); i++) { %>
            <button type="button" data-bs-target="#carouselActividades" data-bs-slide-to="<%= i %>" 
                class="<%= i == 0 ? "active" : "" %>" 
                aria-current="<%= i == 0 ? "true" : "false" %>" 
                aria-label="Slide <%= i + 1 %>"></button>
            <% } %>
        </div>

        <div class="carousel-inner">
            <% 
            for (int i = 0; i < activities.size(); i++) { 
                DtTouristActivity activity = activities.get(i);
            %>
            <div class="carousel-item <%= i == 0 ? "active" : "" %>">
                <a href="<%= ctx %>/activities?q=<%= java.net.URLEncoder.encode(activity.getActivityName(), "UTF-8") %>" 
                   class="text-decoration-none">
                    <div class="d-flex h-100 align-items-center justify-content-center">
                        <img src="<%= activityImgPath + "/" + activity.getImageActPath() %>" 
                             class="d-block w-100" 
                             alt="<%= activity.getActivityName() %>"
                             style="object-fit:cover; height:500px;">
                    </div>
                    <div class="carousel-caption d-none d-md-flex flex-column align-items-center">
                        <h5 class="bg-dark bg-opacity-75 px-4 py-2 rounded"><%= activity.getActivityName() %></h5>
                        <p class="bg-dark bg-opacity-75 px-3 py-1 rounded"><%= activity.getCity() %></p>
                    </div>
                </a>
            </div>
            <% } %>
        </div>
        <!-- BOTONES CARROUSEL -->
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselActividades"
            data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselActividades"
            data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    <% } else { %>
    <div class="container my-5 py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 text-center">
                <div class="card shadow-sm">
                    <div class="card-body p-5">
                        <i class="bi bi-compass display-1 text-primary mb-4"></i>
                        <h2 class="mb-3">Bienvenido a TurismoUy</h2>
                        <p class="lead text-muted mb-4">
                            Actualmente no hay actividades turísticas confirmadas disponibles.
                        </p>
                        <p class="text-muted">
                            Vuelve pronto para descubrir las mejores experiencias turísticas de Uruguay.
                        </p>
                        <div class="mt-4">
                            <c:if test="${not empty sessionScope.logged_user && sessionScope.user_role == 'SUPPLIER'}">
                                <a href="<%=ctx%>/activities" class="btn btn-primary btn-lg">
                                    <i class="bi bi-plus-circle me-2"></i>Registrar una actividad
                                </a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <% } %>
    <!-- FIN CARROUSEL -->
    <!-- Footer -->
    <jsp:include page="/WEB-INF/partials/footer.jsp" />
    <!-- End Footer -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
</body>

</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%
String ctx = request.getContextPath();
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
    <div id="carouselActividades" class="carousel slide my-5 mt-5" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselActividades" data-bs-slide-to="0" class="active"
                aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselActividades" data-bs-slide-to="1"
                aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#carouselActividades" data-bs-slide-to="2"
                aria-label="Slide 3"></button>
        </div>
        
        <div class="carousel-inner">
            <!-- INICIO PRIMER ITEM -->
            <div class="carousel-item active">
                <div class="d-flex h-100 align-items-center justify-content-center">
                    <img src="https://picsum.photos/1200/500?random=1" class="d-block w-100" alt="Turismo 1"
                        style="object-fit:cover; height:500px;">
                </div>
                <div class="carousel-caption d-none d-md-flex flex-column align-items-center">
                    <h5>First slide label</h5>
                </div>
            </div>
            <!-- FIN PRIMER ITEM -->
            <div class="carousel-item">
                <div class="d-flex h-100 align-items-center justify-content-center">
                    <img src="https://picsum.photos/1200/500?random=1" class="d-block w-100" alt="Turismo 2"
                        style="object-fit:cover; height:500px;">
                </div>
                <div class="carousel-caption d-none d-md-flex flex-column align-items-center">
                    <h5>Second slide label</h5>
                </div>
            </div>

            <div class="carousel-item">
                <div class="d-flex h-100 align-items-center justify-content-center">
                    <img src="https://picsum.photos/1200/500?random=1" class="d-block w-100" alt="Turismo 3"
                        style="object-fit:cover; height:500px;">
                </div>
                <div class="carousel-caption d-none d-md-flex flex-column align-items-center">
                    <h5>Third slide label</h5>
                </div>
            </div>
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
	<!-- Footer -->
	<jsp:include page="/WEB-INF/partials/footer.jsp" />
	<!-- End Footer -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
</body>

</html>
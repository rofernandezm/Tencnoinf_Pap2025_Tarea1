<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Actividades</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/activitiesStyles.css">
<style>
:root {
	--bs-primary: rgb(68, 111, 175);
	--bs-primary-rgb: 68, 111, 175;
	--bs-body-bg: #ffffff;
}
/*

.dropdown-menu.bg-primary {
	--bs-dropdown-bg: var(--bs-primary);
	--bs-dropdown-link-color: #fff;
	--bs-dropdown-link-hover-color: #fff;
	--bs-dropdown-link-hover-bg: rgba(255, 255, 255, .2);
	--bs-dropdown-link-active-color: #fff;
	--bs-dropdown-link-active-bg: rgba(255, 255, 255, .3);
	border: none;
}
*/

.card-grid .card {
	height: 100%;
}

.card-grid .card-img-top {
	height: 150px;
	object-fit: cover;
}

.card-grid .card-body {
	display: flex;
	flex-direction: column;
}

.card-grid .btn {
	margin-top: auto;
}
</style>
</head>

<body class="d-flex flex-column min-vh-100">

	<%@ include file="header.jsp"%>

	<!-- CTA agregar actividad -->
	<section class="py-3">
		<div class="container">
			<div class="card shadow-sm border-0">
				<div
					class="card-body d-flex flex-column flex-md-row align-items-start align-items-md-center justify-content-between gap-3">
					<div>
						<h5 class="mb-1">Agregar actividad</h5>
						<p class="text-body-secondary mb-0">Publicá una nueva
							actividad para que aparezca en el listado.</p>
					</div>
					<!-- ahora navega a una página -->
					<a href="actividadForm.html" class="btn btn-outline-primary"> <i
						class="bi bi-plus-circle me-2"></i> Agregar actividad
					</a>
				</div>
			</div>
		</div>
	</section>

	<!-- Buscador -->
	<section class="bg-body-tertiary py-3">
		<div class="container">
			<form id="buscadorTop" class="d-flex gap-2" role="search">
				<input id="buscadorTopInput" class="form-control form-control-lg"
					type="search" placeholder="Buscar actividades..."
					aria-label="Buscar">
				<button class="btn btn-primary btn-lg" type="submit">Buscar</button>
			</form>
		</div>
	</section>

	<!-- Grid de cards -->
	<div class="container my-4 card-grid">
		<div
			class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 g-4">

			<!-- Card ejemplo -->
			<div class="col">
				<div class="card h-100">
					<img src="Imagenes/turis.jpg" class="card-img-top"
						alt="Caminata por la Rambla">
					<div class="card-body">
						<h5 class="card-title">Caminata por la Rambla</h5>
						<!-- antes abría un modal; ahora navega -->
						<a href="actividad.html?id=1" class="btn btn-primary">Detalles</a>
					</div>
				</div>
			</div>

			<div class="col">
				<div class="card h-100">
					<img src="Imagenes/turismo-peru-scaled.jpg" class="card-img-top"
						alt="City tour">
					<div class="card-body">
						<h5 class="card-title">City tour</h5>
						<a href="actividad.html?id=2" class="btn btn-primary">Detalles</a>
					</div>
				</div>
			</div>

			<div class="col">
				<div class="card h-100">
					<img src="Imagenes/turismo_sostenible.avif" class="card-img-top"
						alt="Turismo sostenible">
					<div class="card-body">
						<h5 class="card-title">Turismo sostenible</h5>
						<a href="actividad.html?id=3" class="btn btn-primary">Detalles</a>
					</div>
				</div>
			</div>

			<!-- …duplicá más cards según necesites -->
		</div>
	</div>

	<%@ include file="footer.jsp"%>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
		crossorigin="anonymous"></script>
</body>
</html>

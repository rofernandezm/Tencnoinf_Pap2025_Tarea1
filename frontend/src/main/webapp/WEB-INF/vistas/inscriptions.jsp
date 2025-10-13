
<%@page import="turismouyapp.core.dto.DtTouristActivity"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="turismouyapp.core.dto.DtActivityWithOutings"%>
<%@ page import="turismouyapp.core.dto.DtTouristOuting"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
List<DtActivityWithOutings> actWtOuts = (List<DtActivityWithOutings>) request.getAttribute("activitiesWithOutings");
if (actWtOuts == null) {
	actWtOuts = Collections.emptyList();
}

SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm");
SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>TurismoUy</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/app.css">
<link rel="stylesheet" 
	href="<%=request.getContextPath()%>/assets/css/inscriptionsStyles.css">
</head>

<body class="d-flex flex-column min-vh-100">
	<!-- Navbar -->
	<%
	request.setAttribute("navActive", "inscriptions"); // activities | outings | inscriptions
	%>
	<jsp:include page="/WEB-INF/partials/header.jsp" />
	<!-- End Navbar-->
	<main class="flex-fill pt-5 mt-5">
		<div class="container pt-1">
			<!-- Start Searchbar -->
			<jsp:include page="/WEB-INF/partials/searchbar.jsp" />
			<!-- End Searchbar -->

			<!-- Start Cards -->
			<%
			if (actWtOuts.size()>1) {
			%>
			<div class="alert alert-info">Por favor, ingrese el nombre de la actividad a la que desea inscribirse en la barra de búsqueda.</div>
			<%
			} else if (actWtOuts.isEmpty()) {
			%>
			<div class="alert alert-info">No hay coincidencias.</div>
			<%
			} else {
				
				int aIdx = 0;
				DtActivityWithOutings act = actWtOuts.get(aIdx);
				DtTouristActivity a = act.getActivity();
				String accId = "acc_" + aIdx;
			%>
			<section class="card mb-3">
				<div class="row g-0">
					<div class="col-md-4">
						<img
							src="<%=request.getContextPath()%>/assets/img/actividad_img.jpg"
							class="img-fluid rounded-start" alt="Imagen de la actividad">
					</div>

					<div class="card-body p-0 col-md-8">
						<div class="row h-100 g-0">
							<div class="container p-3 col-md-4">
								<h5 class="card-title"><%=a.getActivityName()%></h5>
								<ul class="list-unstyled mb-0 small">
									<li><strong>Descripción:</strong> <%=a.getDescription()%></li>
									<li><strong>Duración:</strong> <%=a.getDuration()%></li>
									<li><strong>Costo por turista:</strong> $<%=a.getCostTurist()%></li>
									<li><strong>Ciudad:</strong> <%=a.getCity()%></li>
									<li><strong>Proveedor:</strong> <%=a.getSupplierNickname()%></li>
								</ul>
							</div>

							<div class="col-md-8">

								<div class="p-3 col-md-12 border-bottom">
									<h5 class="card-title">Salida</h5>
									<ul class="list-unstyled my-0 small">
										<li><strong>Cupos:</strong> 10</li>
										<li><strong>Disponibilidad:</strong> 4 cupos libres</li>
										<li><strong>Punto de salida:</strong> Terminal Tres
											Cruces</li>
										<li><strong>Fecha de salida:</strong> 10/10/2025 15:30</li>
									</ul>
								</div>
								<div class="p-3">
									<h5 class="card-title mb-3">Datos de la inscripción</h5>
									<form class="small">
										<div class="row align-items-center">
											<label for="touristName"
												class="col-sm-4 col-form-label fw-bold">Turista:</label>
											<div class="col-sm-8">
												<input type="text" id="touristName"
													class="form-control form-control-sm"
													placeholder="Nombre del turista" readonly>
											</div>
										</div>

										<div class="row align-items-center">
											<label for="touristSeats"
												class="col-sm-4 col-form-label fw-bold">Cupos a
												reservar:</label>
											<div class="col-sm-8">
												<input type="number" id="touristSeats"
													class="form-control form-control-sm" min="1"
													placeholder="Ej: 2">
											</div>
										</div>

										<div class="row align-items-center">
											<label for="totalCost"
												class="col-sm-4 col-form-label fw-bold">Costo total:</label>
											<div class="col-sm-8">
												<input type="text" id="totalCost"
													class="form-control form-control-sm" placeholder="$12.000"
													readonly>
											</div>
										</div>

										<div class="row align-items-center">
											<label for="departureDate"
												class="col-sm-4 col-form-label fw-bold">Fecha de
												salida:</label>
											<div class="col-sm-8">
												<input type="datetime-local" id="departureDate"
													class="form-control form-control-sm"
													value="2025-10-10T15:30">
											</div>
										</div>
									</form>

									<div class="d-flex justify-content-end gap-5 mt-3">
										<button class="btn btn-secondary" type="button">Cancelar</button>
										<button class="btn btn-primary" type="submit">Inscribirme</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<%
			} 
			%>
			<!-- End Cards -->
		</div>
	</main>
	<!-- Footer -->
	<jsp:include page="/WEB-INF/partials/footer.jsp" />
	<!-- End Footer -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
		crossorigin="anonymous"></script>
	<script src="<%=request.getContextPath()%>/assets/js/app.js" defer></script>


</body>

</html>
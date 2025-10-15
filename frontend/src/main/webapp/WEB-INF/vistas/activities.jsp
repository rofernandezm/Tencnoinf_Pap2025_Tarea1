
<%@page import="turismouyapp.core.dto.DtTouristActivity"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ page import="turismouyapp.core.dto.DtActivityWithOutings"%>
<%@ page import="turismouyapp.core.dto.DtTouristOuting"%>
<%@ page import="java.text.SimpleDateFormat"%>


<%
String ctx = request.getContextPath();

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
<link rel="icon" type="image/png" href="<%=ctx%>/res/turismouyAppIcon.png">
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
	href="<%=request.getContextPath()%>/assets/css/activitiesStyles.css">

</head>

<body class="d-flex flex-column min-vh-100">
	<!-- Navbar -->
	<%
	request.setAttribute("navActive", "activities"); // activities | outings | inscriptions
	%>
	<jsp:include page="/WEB-INF/partials/header.jsp" />
	<!-- End Navbar-->

	<!--  	<c:if -->
	<%--  		test="${not empty sessionScope.logged_user and  --%>
	<%--                        sessionScope.logged_user.userType eq 'SUPPLIER'}">  --%>



	<!-- CONTENEDOR AGREGAR ACTIVIDAD-->
	<section class="py-3">
		<div class="container">
			<div class="card shadow-sm border-0 card-agregar"
				style="margin-top: 5rem;">
				<div
					class="card-body d-flex flex-column flex-md-row align-items-start align-items-md-center justify-content-between gap-3">
					<div>
						<h5 class="mb-1">Agregar actividad</h5>
						<p class="text-body-secondary mb-0">Publicá una nueva
							actividad para que aparezca en el listado.</p>
					</div>
					<button type="button" class="btn btn-outline-primary"
						data-bs-toggle="modal" data-bs-target="#modalActividadForm">
						<i class="bi bi-plus-circle me-2"></i> Agregar actividad
					</button>
				</div>
			</div>
		</div>
	</section>

	<!--  	</c:if>  -->

	<main class="flex-fill pt-5 mt-5">
		<div class="container pt-1">
			<!-- Start Searchbar -->
			<jsp:include page="/WEB-INF/partials/searchbar.jsp" />
			<!-- End Searchbar -->

 			<!-- CONTENEDOR PARA LAS CARDS DE ACTIVIDADES -->
			<div class="container my-4 card-grid">

				<!-- PRIMER LINEA -->

				<div
					class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 g-4">

			<!-- Start Cards -->
			<%
			if (actWtOuts.isEmpty()) {
			%>
			<div class="alert alert-info">No hay coincidencias.</div>
			<%
			} else {

			int aIdx = 0;
			for (DtActivityWithOutings act : actWtOuts) {
				DtTouristActivity a = act.getActivity();
				String accId = "acc_" + aIdx;
				String modalId = "modal_" + aIdx;
			%>
					<section class="card mb-3">
						<div class="col">
							<div class="card h-100">
								<img src="<%=request.getContextPath()%>/assets/img/actividad_img.jpg" class="card-img-top" alt="...">
								<div class="card-body">
									<h5 class="card-title"><%=a.getActivityName()%></h5>
									<!-- BOTON DETALLES CARGANDO EL MODAL -->
									<button type="button" class="btn btn-primary"
										data-bs-toggle="modal" data-bs-target="#<%=modalId%>">
										Detalles</button>
								</div>
							</div>
						</div>
		
			<!-- MODAL POPUP VER DETALLES DE ACTIVIDAD -->
			<div class="modal fade" id="<%=modalId%>" tabindex="-1"
				aria-hidden="true">
				<div class="modal-dialog modal-lg modal-dialog-centered">
					<div class="modal-content">
						<div class="modal-header bg-primary text-white">
							<h5 class="modal-title"><%=a.getActivityName()%></h5>

							<!-- Nombre de la actividad -->
							<button type="button" class="btn-close btn-close-white"
								data-bs-dismiss="modal" aria-label="Cerrar"></button>
						</div>
						<div class="modal-body">
							<img id="actividadImg" src="Imagenes/turis.jpg"
								alt="Imagen de la actividad" class="img-fluid rounded mb-3"
								style="height: 300px; width: 100%; object-fit: cover;">
							<!-- Detalles -->
							<p class="mb-3">
								<strong>Descripción:</strong>
								<%=a.getDescription()%>
							</p>
							<dl class="row mb-0">
								<dt class="col-sm-4">Proveedor</dt>
								<dd class="col-sm-8"><%=a.getSupplierNickname()%></dd>
								<dt class="col-sm-4">Ciudad</dt>
								<dd class="col-sm-8"><%=a.getCity()%></dd>
								<dt class="col-sm-4">Duración</dt>
								<dd class="col-sm-8"><%=a.getDuration()%></dd>
								<dt class="col-sm-4">Costo por turista</dt>
								<dd class="col-sm-8">
									$<%=a.getCostTurist()%></dd>
							</dl>
						</div>

						<!-- VER TEMA DE NAVEGACION -->

						<div class="modal-footer">
							<a href="salidas.html" class="btn btn-primary">Ver salidas</a>
						</div>
					</div>
				</div>
			</div>



			</section>
			<%
			aIdx++;
			}
			}
			%>
			
				</div>
			</div>

			<!-- End Cards -->
		</div>
	</main>

	<!-- MODAL PARA AGREGAR ACTIVIDAD-->
	<div class="modal fade" id="modalActividadForm" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header bg-primary text-white">
					<h5 class="modal-title" id="formTitulo">Agregar actividad</h5>
					<button type="button" class="btn-close btn-close-white"
						data-bs-dismiss="modal" aria-label="Cerrar"></button>
				</div>
				<form id="formActividad" novalidate enctype="multipart/form-data">
					<div class="modal-body">
						<input type="hidden" id="actId" name="id">
						<div class="row g-3">
							<div class="col-12">
								<label for="actName" class="form-label">Nombre de la
									actividad *</label> <input type="text" class="form-control"
									id="actName" name="title" required>
								<div class="invalid-feedback">Ingresá el nombre.</div>
							</div>
							<div class="col-md-6">
								<label for="actProvider" class="form-label">Proveedor *</label>
								<input type="text" class="form-control" id="actProvider"
									name="provider" required>
								<div class="invalid-feedback">Ingresá el proveedor.</div>
							</div>
							<div class="col-md-6">
								<label for="actCity" class="form-label">Ciudad *</label> <input
									type="text" class="form-control" id="actCity" name="city"
									required>
								<div class="invalid-feedback">Ingresá la ciudad.</div>
							</div>
							<div class="col-md-6">
								<label class="form-label">Duración *</label>
								<div class="input-group">
									<input type="number" min="1" step="1" class="form-control"
										id="actDurationHours" name="durationHours" required> <span
										class="input-group-text">horas</span>
									<div class="invalid-feedback">Ingresá la duración en
										horas.</div>
								</div>
							</div>
							<div class="col-md-6">
								<label class="form-label">Costo por turista *</label>
								<div class="input-group">
									<span class="input-group-text">UYU</span> <input type="number"
										min="0" step="1" class="form-control" id="actCost" name="cost"
										required>
									<div class="invalid-feedback">Ingresá el costo en UYU.</div>
								</div>
							</div>
							<div class="col-12">
								<label for="actDescription" class="form-label">Descripción
									*</label>
								<textarea class="form-control" id="actDescription"
									name="description" rows="4" required></textarea>
								<div class="invalid-feedback">Ingresá la descripción.</div>
							</div>
							<div class="col-12">
								<label for="actImage" class="form-label">Imagen</label> <input
									type="file" class="form-control" id="actImage" name="image"
									accept="image/*">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="btnSubmit" class="btn btn-primary">Crear
							actividad</button>
					</div>
				</form>
			</div>
		</div>
	</div>
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
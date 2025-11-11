
<%@page import="turismouyapp.utils.DateUtils"%>
<%@page import="turismouyapp.webservices.DtTouristActivity"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ page import="turismouyapp.webservices.DtActivityWithOutings"%>
<%@ page import="turismouyapp.webservices.DtTouristOuting"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
String ctx = request.getContextPath();
String activityImgPath = ctx + "/activity_img";
String defaultImgPath = ctx + "/res/default_activity.jpg";

@SuppressWarnings("unchecked")
List<DtActivityWithOutings> actWtOuts = (List<DtActivityWithOutings>) request.getAttribute("activitiesWithOutings");
if (actWtOuts == null) {
	actWtOuts = Collections.emptyList();
}


//SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>TurismoUy</title>
<link rel="icon" type="image/png"
	href="<%=ctx%>/res/turismouyAppIcon.png">
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

	<main class="flex-fill pt-5 mt-5">
		<div class="container pt-1">
			<!-- Start Searchbar -->
			<jsp:include page="/WEB-INF/partials/searchbar.jsp" />
			<!-- End Searchbar -->

			<%
			if (actWtOuts.isEmpty()) {
			%>
			<div class="alert alert-info">No hay coincidencias.</div>
			<%
			} else {

			int aIdx = 0;
			%>

			<!-- CONTENEDOR PARA LAS CARDS DE ACTIVIDADES -->
			<div class="container my-4 card-grid">

				<!-- PRIMER LINEA -->

				<div
					class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 g-4">
					<!-- Start Cards -->
					<%
					for (DtActivityWithOutings act : actWtOuts) {
						DtTouristActivity a = act.getActivity();
						String accId = "acc_" + aIdx;
						String modalId = "modal_" + aIdx;
					%>

					<section>
						<div class="col">
							<div class="card h-100">
								<img
									src="<%=(a.getImageActPath() != null && !a.getImageActPath().isEmpty())
		? activityImgPath + "/" + a.getImageActPath()
		: defaultImgPath%>"
									alt="Imagen de actividad" class="card-img-top"
									style="object-fit: contain; object-position: center;">
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
										<img
											src="<%=(a.getImageActPath() != null && !a.getImageActPath().isEmpty())
		? activityImgPath + "/" + a.getImageActPath()
		: defaultImgPath%>"
											id="actividadImg" alt="Imagen de la actividad"
											class="modal-activity-img rounded mb-3">
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
											<dd class="col-sm-8"><%=DateUtils.parseDurationToHoursString(a.getDuration())%></dd>
											<dt class="col-sm-4">Costo por turista</dt>
											<dd class="col-sm-8">
												$<%=a.getCostTurist()%></dd>
										</dl>
									</div>

									<!-- VER TEMA DE NAVEGACION -->

									<div class="modal-footer">
										<form method="get"
											action="<%=request.getContextPath()%>/outings"
											class="d-inline">
											<input type="hidden" name="q"
												value="<%=a.getActivityName()%>">
											<button type="submit" class="btn btn-primary">Ver
												salidas</button>
										</form>
									</div>
								</div>
							</div>
						</div>



					</section>
					<%
					aIdx++;
					}
					%>
				</div>
			</div>
			<%
			}
			%>


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
				<form action="<%=ctx%>/activities" method="post" id="formActividad"
					enctype="multipart/form-data">
					<div class="modal-body">
						<input type="hidden" id="actId" name="id">
						<div class="row g-3">
							<div class="col-12">
								<label for="actName" class="form-label">Nombre de la
									actividad *</label> <input type="text"
									class="form-control <%= request.getAttribute("activityError") != null ? "is-invalid" : "" %>"
									id="actName" name="title"
									value="${not empty activityError ? draftedActivity.activityName : ''}"
									required>
								<div class="invalid-feedback">
									<%=request.getAttribute("activityError") != null ? request.getAttribute("activityError") : "Ingrese el nombre."%>
								</div>
							</div>
							<div class="col-md-6">
								<label for="actProvider" class="form-label">Proveedor *</label>
								<input type="text" class="form-control" id="actProvider"
									name="supplier" value="${sessionScope.logged_user.nickname}"
									readonly>
								<div class="invalid-feedback">Ingresá el proveedor.</div>
							</div>
							<div class="col-md-6">
								<label for="actCity" class="form-label">Ciudad *</label> <input
									type="text" class="form-control" id="actCity" name="city"
									value="${not empty activityError ? draftedActivity.city : ''}"
									required>
								<div class="invalid-feedback">Ingresá la ciudad.</div>
							</div>
							<div class="col-md-6">
								<label class="form-label">Duración *</label>
								<div class="input-group">
									<input type="number" min="1" step="1" class="form-control"
										id="actDurationHours" name="durationHours"
										value="${not empty activityError ? DateUtils.parseDurationToHoursString(draftedActivity.getDuration()) : ''}"
										required> <span class="input-group-text">horas</span>
									<div class="invalid-feedback">Ingresá la duración en
										horas.</div>
								</div>
							</div>
							<div class="col-md-6">
								<label class="form-label">Costo por turista *</label>
								<div class="input-group">
									<span class="input-group-text">UYU</span> <input type="number"
										min="0" step="1" class="form-control" id="actCost" name="cost"
										value="${not empty activityError ? draftedActivity.costTurist : ''}"
										required>
									<div class="invalid-feedback">Ingresá el costo en UYU.</div>
								</div>
							</div>
							<div class="col-12">
								<label for="actDescription" class="form-label">Descripción
									*</label>
								<textarea class="form-control" id="actDescription"
									name="description" rows="4" required>${not empty activityError ? draftedActivity.description : ''}</textarea>
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
	<c:if test="${not empty activityError}">
		<script>
			document.addEventListener('DOMContentLoaded', function() {
				var modal = new bootstrap.Modal(document
						.getElementById('modalActividadForm'));
				modal.show();
			});
		</script>
	</c:if>

</body>

</html>

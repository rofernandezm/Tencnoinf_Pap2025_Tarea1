<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%
String ctx = request.getContextPath();
request.setAttribute("navActive", "consult-user");
%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Consulta de Usuario - TurismoUy</title>
	<link rel="icon" type="image/png" href="<%=ctx%>/res/turismouyAppIcon.png">
	
	<!-- Bootstrap CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
	<!-- Bootstrap Icons -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	<!-- Custom CSS -->
	<link rel="stylesheet" href="<%=ctx%>/assets/css/app.css">
	<link rel="stylesheet" href="<%=ctx%>/assets/css/consultUserStyles.css">
</head>

<body class="d-flex flex-column min-vh-100">
	
	<!-- Header -->
	<jsp:include page="/WEB-INF/partials/header.jsp" />
	
	<!-- Main Content -->
	<main class="flex-grow-1">
		<div class="container py-5">
			<div class="row justify-content-center">
				<div class="col-12 col-lg-10">
					<div class="card shadow-sm">
						<div class="card-body p-4">
							<h3 class="text-center mb-4">
								<i class="bi bi-person-circle me-2"></i>Consulta de Usuario
							</h3>
							
							<!-- Selector de Usuario -->
							<div class="row justify-content-center mb-4">
								<div class="col-md-8 col-lg-6">
									<form action="<%=ctx%>/consult-user" method="GET">
										<label for="nickname-select" class="form-label fw-semibold">
											Seleccione el nickname de usuario:
										</label>
										<div class="input-group">
											<select class="form-select" id="nickname-select" name="nickname" required>
												<option value="" selected disabled>-- Seleccione un usuario --</option>
												<c:forEach var="nick" items="${usuarios}">
													<option value="${nick}" ${nick eq param.nickname ? 'selected' : ''}>${nick}</option>
												</c:forEach>
											</select>
											<button type="submit" class="btn btn-primary">
												<i class="bi bi-search me-1"></i>Buscar
											</button>
										</div>
									</form>
								</div>
							</div>
							
							<!-- Información del Usuario -->
							<c:if test="${not empty usuario}">
								<hr class="my-4">
								
								<div class="row">
									<!-- Columna izquierda: Foto -->
									<div class="col-md-3 text-center mb-3 mb-md-0">
										<img src="<%=ctx%>/profile_img/${usuario.imagePath}" 
											 alt="Foto de ${usuario.name}" 
											 class="profile-pic"
											 onerror="this.src='<%=ctx%>/assets/images/default_profile.jpg'">
									</div>
									
									<!-- Columna derecha: Datos -->
									<div class="col-md-9">
										<div class="info-card">
											<h6 class="border-bottom pb-2 mb-3">
												<i class="bi bi-info-circle me-2"></i>Información Personal
											</h6>
											
											<div class="row mb-3">
												<div class="col-md-6">
													<div class="info-label">Nickname</div>
													<input type="text" class="form-control form-control-sm" 
														   value="${usuario.nickname}" readonly>
												</div>
												
												<div class="col-md-6">
													<div class="info-label">Correo electrónico</div>
													<input type="email" class="form-control form-control-sm" 
														   value="${usuario.email}" readonly>
												</div>
											</div>
											
											<div class="row mb-3">
												<div class="col-md-6">
													<div class="info-label">Nombre</div>
													<input type="text" class="form-control form-control-sm" 
														   value="${usuario.name}" readonly>
												</div>
												
												<div class="col-md-6">
													<div class="info-label">Apellido</div>
													<input type="text" class="form-control form-control-sm" 
														   value="${usuario.lastName}" readonly>
												</div>
											</div>
											
											<div class="row">
												<div class="col-md-6">
													<div class="info-label">Fecha de nacimiento</div>
													<input type="text" class="form-control form-control-sm" 
														   value="${usuario.birthDate}" readonly>
												</div>
												
												<c:if test="${usuario.userType == 'TOURIST'}">
													<div class="col-md-6">
														<div class="info-label">Nacionalidad</div>
														<input type="text" class="form-control form-control-sm" 
															   value="${usuario.nationality}" readonly>
													</div>
												</c:if>
											</div>
										</div>
										
										<!-- Información específica según tipo de usuario -->
										<c:if test="${usuario.userType == 'SUPPLIER'}">
											<div class="info-card">
												<h6 class="border-bottom pb-2 mb-3">
													<i class="bi bi-briefcase me-2"></i>Información del Proveedor
												</h6>
												
												<div class="mb-3">
													<div class="info-label">Descripción</div>
													<textarea class="form-control form-control-sm" rows="3" readonly>${usuario.description}</textarea>
												</div>
												
												<div class="mb-0">
													<div class="info-label">Sitio web</div>
													<c:choose>
														<c:when test="${not empty usuario.webSite}">
															<a href="${usuario.webSite}" target="_blank" class="btn btn-sm btn-outline-primary">
																<i class="bi bi-link-45deg me-1"></i>Visitar sitio web
															</a>
														</c:when>
														<c:otherwise>
															<input type="text" class="form-control form-control-sm" 
																   value="No especificado" readonly>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</c:if>
									</div>
								</div>
							</c:if>
							
							<!-- Mensaje si no hay usuario seleccionado -->
							<c:if test="${empty usuario && not empty param.nickname}">
								<div class="alert alert-warning text-center" role="alert">
									<i class="bi bi-exclamation-triangle me-2"></i>
									No se encontró el usuario seleccionado.
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>
	
	<!-- Footer -->
	<jsp:include page="/WEB-INF/partials/footer.jsp" />
	
	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

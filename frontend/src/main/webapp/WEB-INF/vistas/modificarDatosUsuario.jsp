<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%
String ctx = request.getContextPath();
request.setAttribute("navActive", ""); // Sin menú activo
%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Modificar datos de usuario - TurismoUy</title>
	<link rel="icon" type="image/png" href="<%=ctx%>/res/turismouyAppIcon.png">
	
	<!-- Bootstrap CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
	<!-- Bootstrap Icons -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	<!-- Custom CSS -->
	<link rel="stylesheet" href="<%=ctx%>/assets/css/app.css">
	
	<style>
		main {
			padding-top: 80px; /* Espacio para navbar fixed */
			min-height: calc(100vh - 120px);
		}
		
		.profile-pic {
			width: 180px;
			height: 180px;
			border-radius: 50%;
			object-fit: cover;
			border: 4px solid var(--primary-color, #1E375A);
			margin-bottom: 20px;
		}
		
		.photo-section {
			text-align: center;
			padding: 30px 20px;
			border-right: 1px solid #dee2e6;
		}
		
		.data-section {
			padding: 30px 20px;
		}
		
		.form-control:read-only {
			background-color: #e9ecef;
			cursor: not-allowed;
		}
		
		.btn-custom {
			background-color: var(--primary-color, #1E375A);
			color: #fff;
			border: none;
			padding: 10px 30px;
			border-radius: 0.375rem;
		}
		
		.btn-custom:hover {
			background-color: var(--secondary-color, #122949);
			color: #fff;
		}
		
		.btn-secondary {
			border-radius: 0.375rem;
		}
		
		@media (max-width: 767px) {
			.photo-section {
				border-right: none;
				border-bottom: 1px solid #dee2e6;
				padding: 20px;
			}
			
			.data-section {
				padding: 20px;
			}
			
			.profile-pic {
				width: 150px;
				height: 150px;
			}
			
			main {
				padding-top: 70px;
			}
		}
	</style>
</head>

<body class="d-flex flex-column min-vh-100">
	
	<!-- Header -->
	<jsp:include page="/WEB-INF/partials/header.jsp" />
	
	<!-- Main Content -->
	<main class="flex-grow-1">
		<div class="container py-5">
			<div class="row justify-content-center">
				<div class="col-12 col-lg-10 col-xl-9">
					<div class="card shadow-sm">
						<div class="card-body p-0">
							<h3 class="text-center pt-4 mb-4">Modificar datos de usuario</h3>
							
							<!-- Mostrar errores si existen -->
							<c:if test="${not empty error}">
								<div class="alert alert-danger mx-4" role="alert">
									<i class="bi bi-exclamation-triangle-fill me-2"></i>${error}
								</div>
							</c:if>
							
							<form action="<%=ctx%>/modify-data-user" method="POST" enctype="multipart/form-data">
								<div class="row g-0">
									<!-- Columna izquierda: Foto de perfil -->
									<div class="col-md-4 photo-section">
										<img src="<%=ctx%>/profile_img/${sessionScope.logged_user.imagePath}" 
											 alt="Foto de perfil" 
											 class="profile-pic">
										<div class="px-3">
											<label for="new-profilephoto" class="form-label fw-semibold">
												<i class="bi bi-camera-fill me-1"></i>Cambiar foto
											</label> 
											<input class="form-control form-control-sm" 
												   type="file"
												   id="new-profilephoto" 
												   name="new-profilephoto" 
												   accept="image/*">
											<small class="text-muted d-block mt-1">JPG, PNG o GIF (máx. 5MB)</small>
										</div>
									</div>
									
									<!-- Columna derecha: Datos del usuario -->
									<div class="col-md-8 data-section">
										<div class="row">
											<div class="col-md-6 mb-3">
												<label for="nickname-user" class="form-label">Nickname</label>
												<input type="text" class="form-control" id="nickname-user" 
													   value="${sessionScope.logged_user.nickname}" readonly>
											</div>
											
											<div class="col-md-6 mb-3">
												<label for="email-user" class="form-label">Correo electrónico</label> 
												<input type="email" class="form-control" id="email-user" 
													   value="${sessionScope.logged_user.email}" readonly>
											</div>
										</div>
										
										<div class="row">
											<div class="col-md-6 mb-3">
												<label for="name-user" class="form-label">Nombre</label> 
												<input type="text" class="form-control" id="name-user" name="name-user" 
													   value="${sessionScope.logged_user.name}">
											</div>
											
											<div class="col-md-6 mb-3">
												<label for="lastname-user" class="form-label">Apellido</label>
												<input type="text" class="form-control" id="lastname-user" name="lastname-user" 
													   value="${sessionScope.logged_user.lastName}">
											</div>
										</div>
										
										<div class="row">
											<div class="col-md-6 mb-3">
												<label for="password-user" class="form-label">
													Nueva contraseña 
													<small class="text-muted">(opcional)</small>
												</label>
												<input type="password" class="form-control" id="password-user" name="password-user" 
													   placeholder="Dejar vacío para no cambiar">
											</div>
											
											<div class="col-md-6 mb-3">
												<label for="passwordconf-user" class="form-label">Confirmar contraseña</label> 
												<input type="password" class="form-control" id="passwordconf-user" name="passwordconf-user" 
													   placeholder="Confirmar nueva contraseña">
											</div>
										</div>
										
											<div class="mb-3">
											<label for="birthdate-user" class="form-label">Fecha de nacimiento</label> 
											<input type="date" class="form-control" id="birthdate-user" name="birthdate-user" 
												   value="${sessionScope.logged_user.birthDate}">
										</div>
										
										<!-- Botones -->
										<div class="d-grid gap-3 d-md-flex justify-content-md-center mt-4 mb-3">
											<button type="submit" class="btn btn-custom px-4" style="min-width: 180px;">
												<i class="bi bi-check-circle me-2"></i>Guardar cambios
											</button>
											<button type="button" class="btn btn-secondary px-4" style="min-width: 180px;" onclick="window.location.href='<%=ctx%>/home'">
												<i class="bi bi-x-circle me-2"></i>Cancelar
											</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>
	
	<!-- Footer -->
	<jsp:include page="/WEB-INF/partials/footer.jsp"/>
	
	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%
String ctx = request.getContextPath();

// Pestaña activa por defecto
String activeTab = "login";

// Si desde el servlet te mandan un atributo "activeTab", lo usás
if (request.getAttribute("activeTab") != null) {
	activeTab = (String) request.getAttribute("activeTab");
} else if (request.getAttribute("registerError") != null) {
	activeTab = "register";
}
%>

<!DOCTYPE html>
<html lang="es">

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Inicio de Sesión</title>
<link rel="icon" type="image/png"
	href="<%=ctx%>/res/turismouyAppIcon.png">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="<%=ctx%>/assets/css/app.css">

<style>
body {
	background-color: #ffffff;
}

.form-control {
	border-radius: 50px;
}

.card {
	border: none;
}

.nav-tabs {
	border-bottom: 2px solid var(--primary-color);
}

.nav-tabs .nav-link {
	color: var(--primary-color);
	border: none;
	border-radius: 0;
	padding: 0.75rem 1.5rem;
	font-weight: 500;
}

.nav-tabs .nav-link:hover {
	color: var(--secondary-color);
	border: none;
}

.nav-tabs .nav-link.active {
	color: #ffffff !important;
	background-color: var(--primary-color);
	border: none;
	border-radius: 10px 10px 0 0;
}

h3 {
	color: var(--primary-color);
	font-weight: 600;
}

.form-label {
	color: var(--primary-color);
	font-weight: 500;
}

a {
	color: var(--primary-color);
}

a:hover {
	color: var(--secondary-color);
}
</style>

</head>

<body class="d-flex flex-column min-vh-100">

	<main
		class="flex-grow-1 d-flex align-items-center justify-content-center">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5">
					<div class="card shadow rounded-3">
						<div class="card-body p-4">

							<%-- Mostrar mensaje de registro --%>
							<%
							if (request.getAttribute("mensaje") != null) {
							%>
							<div class="alert alert-info"><%=request.getAttribute("mensaje")%></div>
							<%
							}
							%>

							<%-- Mostrar error de login --%>
							<%
							if (request.getAttribute("loginError") != null) {
							%>
							<div class="alert alert-danger"><%=request.getAttribute("loginError")%></div>
							<%
							}
							%>

							<%-- Mostrar error de registro --%>
							<%
							if (request.getAttribute("registerError") != null) {
							%>
							<div class="alert alert-danger"><%=request.getAttribute("registerError")%></div>
							<%
							}
							%>

							<h3 class="text-center mb-4">Bienvenido a tu próxima
								experiencia</h3>

							<!-- Pestañas ingresar/registrarse -->
							<ul class="nav nav-tabs mb-4" role="tablist">
								<li class="nav-item" role="presentation">
									<button
										class="nav-link <%="login".equals(activeTab) ? "active" : ""%>"
										id="login-tab" data-bs-toggle="tab" data-bs-target="#login"
										type="button" role="tab">Ingresar</button>
								</li>
								<li class="nav-item" role="presentation">
									<button
										class="nav-link <%="register".equals(activeTab) ? "active" : ""%>"
										id="register-tab" data-bs-toggle="tab"
										data-bs-target="#register" type="button" role="tab">Registrarse</button>
								</li>
							</ul>

							<!-- Contenido de las pestañas -->
							<div class="tab-content">
								<!-- Pestaña Ingresar -->
								<div
									class="tab-pane fade <%="login".equals(activeTab) ? "show active" : ""%>"
									id="login" role="tabpanel">
									<form action="login" method="POST">
										<!-- <input type="hidden" name="action" value="login">-->

										<!--Luego del login -->
										<input type="text" name="next"
											value="<%=request.getParameter("next")%>" />

										<!--Nickname -->
										<div class="mb-3">
											<label for="nickname-or-email" class="form-label">Nickname
												o correo electrónico</label> <input type="text" class="form-control"
												id="nickname-or-email" name="nickname-or-email"
												placeholder="Ingrese su nickname o correo electrónico"
												required>
										</div>

										<!--Contraseña -->
										<div class="mb-3">
											<label for="password-login" class="form-label">Contraseña</label>
											<input type="password" class="form-control"
												id="password-login" name="password"
												placeholder="Ingrese su contraseña" required>
										</div>

										<!-- Botones -->
										<div class="d-flex justify-content-center gap-3">
											<button type="submit" name="action" value="login"
												class="btn btn-primary">Ingresar</button>
											<button type="reset" class="btn btn-secondary">Cancelar</button>
										</div>

										<!-- Ingresar como visitante -->
										<div class="text-center mt-3">
											<a href="login?action=guest&next=<%=request.getParameter("next")%>" class="text-decoration-none">Ingresar
												como visitante</a>
										</div>
									</form>
								</div>

								<!-- Pestaña Registrarse -->
								<div
									class="tab-pane fade <%="register".equals(activeTab) ? "show active" : ""%>"
									id="register" role="tabpanel">
									<form action="login" method="POST"
										enctype="multipart/form-data">
										<!-- <input type="hidden" name="action" value="register">-->
										
										<!--Luego del login -->
										<input type="text" name="next"
											value="<%=request.getParameter("next")%>" />
											
										<!--Nickname -->
										<div class="mb-3">
											<label for="new-nickname" class="form-label">Nickname</label>
											<input type="text" class="form-control" id="new-nickname"
												name="new-nickname" placeholder="Elija un nickname" required>
										</div>
										<!--Nombre -->
										<div class="mb-3">
											<label for="new-name" class="form-label">Nombre</label> <input
												type="text" class="form-control" id="new-name"
												name="new-name" placeholder="Ingrese su nombre" required>
										</div>
										<!--Apellido -->
										<div class="mb-3">
											<label for="new-lastname" class="form-label">Apellido</label>
											<input type="text" class="form-control" id="new-lastname"
												name="new-lastname" placeholder="Ingrese su apellido"
												required>
										</div>
										<!--Contraseña -->
										<div class="mb-3">
											<label for="password-register" class="form-label">Contraseña</label>
											<input type="password" class="form-control"
												id="password-register" name="password"
												placeholder="Ingrese una nueva contraseña" required>
										</div>
										<!--Confirmación de contraseña -->
										<div class="mb-3">
											<label for="passwordconf" class="form-label">Confirmación
												de contraseña</label> <input type="password" class="form-control"
												id="passwordconf" name="passwordconf"
												placeholder="Ingrese nuevamente la contraseña" required>
										</div>
										<!--Correo electrónico -->
										<div class="mb-3">
											<label for="new-email" class="form-label">Correo
												electrónico</label> <input type="email" class="form-control"
												id="new-email" name="new-email"
												placeholder="Ingrese su correo electrónico" required>
										</div>
										<!--Fecha de nacimiento -->
										<div class="mb-3">
											<label for="new-birthdate" class="form-label">Fecha
												de nacimiento</label> <input type="date" class="form-control"
												id="new-birthdate" name="new-birthdate"
												placeholder="Ingrese su fecha de nacimiento" required>
										</div>
										<!--Tipo de usuario -->
										<div class="mb-3">
											<label class="form-label">Tipo de usuario</label>
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio"
													name="user-type" id="tourist" value="TOURIST" checked>
												<label class="form-check-label" for="tourist">Turista</label>
											</div>
											<div class="form-check form-check-inline">
												<input class="form-check-input" type="radio"
													name="user-type" id="supplier" value="SUPPLIER"> <label
													class="form-check-label" for="supplier">Proveedor</label>
											</div>
										</div>

										<!--Si es turista-->
										<!--Nacionalidad -->
										<c:if test="${userType == 'supplier'}">
										</c:if>

										<div id="tourist-fields">
											<div class="mb-3">
												<label for="nationality" class="form-label">Nacionalidad</label>
												<input type="text" class="form-control" id="nationality"
													name="nationality" placeholder="Ingrese su nacionalidad">
											</div>
										</div>

										<!--Si es proveedor-->
										<div id="supplier-fields" style="display: none;">
											<!--Descripción -->
											<div class="mb-3">
												<label for="new-description" class="form-label">Descripción</label>
												<textarea class="form-control" id="new-description"
													name="description"
													placeholder="Ingrese una breve descripción"></textarea>
											</div>
											<!--Sitio web -->
											<div class="mb-3">
												<label for="new-website" class="form-label">Sitio
													web</label> <input type="url" class="form-control" id="new-website"
													name="website" placeholder="Ingrese la URL de su sitio web">
											</div>
										</div>

										<!--Foto de perfil -->
										<div class="mb-3">
											<label for="new-profilephoto" class="form-label">Foto
												de perfil</label> <input class="form-control" type="file"
												id="new-profilephoto" name="new-profilephoto"
												accept="image/*">
										</div>

										<!-- Botones -->
										<div class="d-flex justify-content-center gap-3">
											<button type="submit" name="action" value="register"
												class="btn btn-primary">Registrarse</button>
											<button type="reset" class="btn btn-secondary">Cancelar</button>
										</div>
									</form>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</main>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>

	<script>
		// Mostrar u ocultar campos según tipo de usuario
		const touristRadio = document.getElementById('tourist');
		const supplierRadio = document.getElementById('supplier');
		const touristFields = document.getElementById('tourist-fields');
		const supplierFields = document.getElementById('supplier-fields');
		const nationalityInput = document.getElementById('nationality');
		const descriptionInput = document.getElementById('new-description');

		function displayUserFields() {
			if (touristRadio.checked) {

				nationalityInput.setAttribute('required', '');
				touristFields.style.display = 'block';
				touristFields.setAttribute('required', '')
				supplierFields.style.display = 'none';
				descriptionInput.removeAttribute('required');
			} else if (supplierRadio.checked) {
				descriptionInput.setAttribute('required', '');
				touristFields.style.display = 'none';
				nationalityInput.removeAttribute("required");
				supplierFields.style.display = 'block';
			}
		}

		touristRadio.addEventListener('change', displayUserFields);
		supplierRadio.addEventListener('change', displayUserFields);

		// Inicializar
		displayUserFields();
	</script>

</body>

</html>

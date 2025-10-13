<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="es">

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Modificar datos de usuario</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">

<style>
body {
	background-color: #ffffff;
	/* Fondo blanco */
}

.form-control {
	border-radius: 50px;
	/* Textboxs redondeados */
}

.btn-custom {
	background-color: #2979f1;
	color: #fff;
	border-radius: 20px;
	padding: 6px 20px;
	font-weight: bold;
}

.equal-columns {
	display: flex;
	flex-wrap: wrap;
}

.profile-pic {
	width: 150px;
	height: 150px;
	border-radius: 50%;
	object-fit: cover;
	border: 3px solid #2979f1;
	margin-bottom: 15px;
}

.photo-section {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	text-align: center;
	padding: 30px;
}

.data-section {
	display: flex;
	flex-direction: column;
	justify-content: center;
	padding: 30px;
}

@media ( max-width : 767px) {
	.equal-columns {
		flex-direction: column;
	}
	.photo-section, .data-section {
		width: 100%;
	}
}
</style>

</head>

<body class="d-flex flex-column min-vh-100">

	<header>
		<!-- Copiar header de Gaston -->
	</header>

	<main
		class="flex-grow-1 d-flex align-items-center justify-content-center">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5">
					<div class="card shadow rounded-3">

						<h3 class="text-center mb-4">Modificar datos</h3>

						<div class="equal-columns">

							<div class="col-md-6 data-section">
								<!-- Datos a modificar  -->
								<form action="change-data" method="POST">
									<!--Nickname, no modificable -->
									<div class="mb-3">
										<label for="nickname-user" class="form-label">Nickname</label>
										<input type="text" class="form-control" id="nickname-user" name="nickname-user"
											value="${sessionScope.nickname}" readonly>
									</div>
									<!--Nombre -->
									<div class="mb-3">
										<label for="name-user" class="form-label">Nombre</label> 
										<input type="text" class="form-control" id="name-user" name="name-user" 
											value="${sessionScope.name}">
									</div>
									<!--Apellido -->
									<div class="mb-3">
										<label for="lastname-user" class="form-label">Apellido</label>
										<input type="text" class="form-control" id="lastname-user" name="lastname-user" 
											value="${sessionScope.lastname}">
									</div>
									<!--Contraseña -->
									<div class="mb-3">
										<label for="password-user" class="form-label">Contraseña</label>
										<input type="password" class="form-control" id="password-user" name="password-user" 
											placeholder="Ingrese una nueva contraseña">
									</div>
									<!--Confirmación de contraseña -->
									<div class="mb-3">
										<label for="passwordconf-user" class="form-label">Confirmación de contraseña</label> 
										<input type="password" class="form-control" id="passwordconf-user" name="passwordconf-user" 
											placeholder="Ingrese nuevamente la contraseña">
									</div>
									<!--Correo electrónico, no modificable-->
									<div class="mb-3">
										<label for="email-user" class="form-label">Correo electrónico</label> 
										<input type="email" class="form-control" id="email-user" name="email-user" 
											value="${sessionScope.email}" readonly>
									</div>
									<!--Fecha de nacimiento -->
									<div class="mb-3">
										<label for="birthdate-user" class="form-label">Fecha de nacimiento</label> 
										<input type="date" class="form-control" id="birthdate-user" name="birthdate-user" 
											value="${sessionScope.birthday}">
									</div>

									<c:if test="${userType == 'tourist'}">
										<!--Nacionalidad -->
										<div id="tourist-fields">
											<div class="mb-3">
												<label for="nationality-user" class="form-label">Nacionalidad</label>
												<input type="text" class="form-control" id="nationality-user" name="nationality-user"
												value="${sessionScope.nacionality}">
											</div>
										</div>
									</c:if>

									<c:if test="${userType == 'supplier'}">
										<!--Descripción -->
										<div class="mb-3">
											<label for="description-user" class="form-label">Descripción</label>
											<textarea class="form-control" id="description-user" name="description-user">${sessionScope.description}</textarea>
										</div>
										<!--Sitio web -->
										<div class="mb-3">
											<label for="website-user" class="form-label">Sitio
												web</label> <input type="url" class="form-control" id="website-user" name="website-user" value="${sessionScope.website}">
										</div>
									</c:if>

									<!-- Botones -->
									<div class="d-flex justify-content-center gap-3">
										<button type="submit" class="btn btn-custom">Confirmar</button>
										<button type="reset" class="btn btn-custom">Cancelar</button>
									</div>
								</form>
							</div>

							<!--Foto de perfil -->
							<div class="col-md-6 photo-section">
								<img src="ruta/a/foto_perfil.jpg" alt="Profile photo" class="profile-pic">

								<form action="change-photo" method="POST" enctype="multipart/form-data">
									<div class="mb-3 w-100">
										<label for="new-profilephoto" class="form-label">Cambiar foto</label> <input class="form-control" type="file"
											id="new-profilephoto" name="new-profilephoto" accept="image/*">
									</div>
									<button type="submit" class="btn btn-custom btn-sm">Actualizar</button>
								</form>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</main>

	<footer>
		<!--Copiar footer de Gaston -->
	</footer>
</body>
</html>
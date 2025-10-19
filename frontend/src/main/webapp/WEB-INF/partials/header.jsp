<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%
// Recibo qué item va activo (activities | outings | inscriptions)
String active = (String) request.getAttribute("navActive");
if (active == null)
	active = "";
String ctx = request.getContextPath();
%>
<%-- Verificar si hay usuario logueado --%>
<c:set var="isGuest" value="${sessionScope.guest_mode eq true}" />
<c:set var="isLogged" value="${not empty sessionScope.logged_user}" />
<c:set var="userRole" value="${sessionScope.user_role}" />

<header>
	<nav class="navbar navbar-expand-lg fixed-top py-0">
		<div class="container-fluid">
			<a class="navbar-brand me-auto" href="<%=ctx%>/home">TurismoUy</a>

			<div class="offcanvas offcanvas-end" tabindex="-1"
				id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
				<div class="offcanvas-header">
					<h5 class="offcanvas-title" id="offcanvasNavbarLabel">TurismoUy</h5>
					<button type="button" class="btn-close" data-bs-dismiss="offcanvas"
						aria-label="Close"></button>
				</div>

				<div class="offcanvas-body">
					<ul class="navbar-nav justify-content-center flex-grow-1 pe-3 fs-5">
						<li class="nav-item"><a
							class="nav-link px-lg-2 <%="activities".equals(active) ? "active" : ""%>"
							href="<%=ctx%>/activities">Actividades</a></li>
						<li class="nav-item"><a
							class="nav-link px-lg-2 <%="outings".equals(active) ? "active" : ""%>"
							href="<%=ctx%>/outings">Salidas</a></li>
						<li class="nav-item <% if(turismouyapp.core.dto.UserType.TOURIST != request.getSession().getAttribute("user_role")) {%>d-none<% }%>"><a
							class="nav-link px-lg-2 <%="inscriptions".equals(active) ? "active" : ""%>"
							href="<%=ctx%>/inscriptions">Inscripción</a></li>
						<li class="nav-item"><a
							class="nav-link px-lg-2 <%="consult-user".equals(active) ? "active" : ""%>"
							href="<%=ctx%>/consult-user">Usuarios</a></li>
					</ul>
					<ul class="navbar-nav">
						<c:choose>
							<c:when test="${isGuest}">
								<%-- Usuario invitado --%>
								<li class="nav-item">
									<a class="nav-link" href="<%=ctx%>/login">
										<i class="bi bi-box-arrow-in-right me-1"></i> Iniciar Sesión
									</a>
								</li>
							</c:when>
							<c:when test="${isLogged}">
								<%-- Usuario autenticado --%>
								<li class="nav-item dropdown">
									<a class="nav-link dropdown-toggle d-flex align-items-center"
										href="#" role="button" data-bs-toggle="dropdown"
										aria-expanded="false">
										<img src="<%=ctx%>/profile_img/${sessionScope.logged_user.imagePath}" 
											 alt="${sessionScope.logged_user.name}" 
											 class="rounded-circle me-2"
											 style="width: 32px; height: 32px; object-fit: cover;">
										<span class="d-none d-lg-inline">${sessionScope.logged_user.name}</span>
									</a>
									<ul class="dropdown-menu dropdown-menu-end">
										<li>
											<div class="dropdown-header text-white d-flex align-items-center">
												<img src="<%=ctx%>/profile_img/${sessionScope.logged_user.imagePath}" 
													 alt="${sessionScope.logged_user.name}" 
													 class="rounded-circle me-2"
													 style="width: 40px; height: 40px; object-fit: cover;">
												<div>
													<div class="fw-bold">${sessionScope.logged_user.name} ${sessionScope.logged_user.lastName}</div>
													<small class="text-white-50">@${sessionScope.logged_user.nickname}</small>
												</div>
											</div>
										</li>
										<li><hr class="dropdown-divider bg-light opacity-25"></li>
										<li><a class="dropdown-item" href="<%=ctx%>/modify-data-user">
											<i class="bi bi-person-gear me-2"></i>Modificar Usuario
										</a></li>
										<li><a class="dropdown-item" href="<%=ctx%>/logout">
											<i class="bi bi-box-arrow-right me-2"></i>Cerrar Sesión
										</a></li>
									</ul>
								</li>
							</c:when>
							<c:otherwise>
								<%-- Sin sesión --%>
								<li class="nav-item">
									<a class="nav-link" href="<%=ctx%>/login">
										<i class="bi bi-box-arrow-in-right me-1"></i> Iniciar Sesión
									</a>
								</li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>

			<button class="navbar-toggler" type="button"
				data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar"
				aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
		</div>
	</nav>
</header>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="turismouyapp.utils.DateUtils"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<c:if test="${not empty usuario}">
	<div class="modal fade" id="modalUserData" tabindex="-1"
		aria-labelledby="modalUserDataLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modalUserDataLabel">
						<i
							class="bi ${usuario.userType == 'SUPPLIER' ? 'bi-list-task' : 'bi-bookmark-check'} me-2"></i>
						${usuario.userType == 'SUPPLIER' ? 'Actividades y Salidas' : 'Inscripciones'}
					</h5>
					<button type="button" class="btn-close btn-close-white"
						data-bs-dismiss="modal" aria-label="Cerrar"></button>
				</div>

				<div class="modal-body">
					<!-- Contenido para SUPPLIER -->
					<c:if test="${usuario.userType == 'SUPPLIER'}">
						<c:choose>
							<c:when test="${not empty activitiesWithOutings}">
								<div class="accordion" id="accordionActivities">
									<c:forEach var="activityWithOutings"
										items="${activitiesWithOutings}" varStatus="actStatus">
										<div class="accordion-item">
											<h2 class="accordion-header" id="heading${actStatus.index}">
												<button
													class="accordion-button ${actStatus.index != 0 ? 'collapsed' : ''}"
													type="button" data-bs-toggle="collapse"
													data-bs-target="#collapse${actStatus.index}"
													aria-expanded="${actStatus.index == 0 ? 'true' : 'false'}"
													aria-controls="collapse${actStatus.index}">
													<i class="bi bi-geo-alt me-2"></i> <strong>${activityWithOutings.activity.activityName}</strong>
												</button>
											</h2>
											<div id="collapse${actStatus.index}"
												class="accordion-collapse collapse ${actStatus.index == 0 ? 'show' : ''}"
												aria-labelledby="heading${actStatus.index}"
												data-bs-parent="#accordionActivities">
												<div class="accordion-body">
													<!-- Información de la actividad -->
													<div class="mb-3 p-3 bg-light rounded">
														<div
															class="d-flex justify-content-between align-items-start mb-3">
															<h6 class="mb-0">
																<i class="bi bi-info-circle me-2"></i>Información de la
																Actividad
															</h6>
															<c:if
																test="${activityWithOutings.activity.status == 'CONFIRMED'}">
																<a
																	href="${pageContext.request.contextPath}/activities?q=${activityWithOutings.activity.activityName}"
																	class="btn btn-sm btn-primary"
																	title="Ver información completa de la actividad"> <i
																	class="bi bi-box-arrow-up-right me-1"></i>Ver detalle
																	completo
																</a>
															</c:if>
														</div>
														<div class="row">
															<div class="col-md-6">
																<p class="mb-2">
																	<strong>Descripción:</strong><br>
																	${activityWithOutings.activity.description}
																</p>
																<c:if test="${isOwnProfile}">
																	<p class="mb-2">
																		<strong>Estado:</strong>
																		<c:choose>
																			<c:when
																				test="${activityWithOutings.activity.status == 'CONFIRMED'}">
																				<span class="badge bg-success">Confirmada</span>
																			</c:when>
																			<c:when
																				test="${activityWithOutings.activity.status == 'ADDED'}">
																				<span class="badge bg-warning text-dark">Agregada</span>
																			</c:when>
																			<c:when
																				test="${activityWithOutings.activity.status == 'REJECTED'}">
																				<span class="badge bg-danger">Rechazada</span>
																			</c:when>
																		</c:choose>
																	</p>
																</c:if>
															</div>
															<div class="col-md-6">
																<p class="mb-2">
																	<strong>Duración:</strong>
																	${DateUtils.parseDurationToHoursString(activityWithOutings.activity.duration)}																</p>
																<p class="mb-2">
																	<strong>Costo por turista:</strong>
																	$${activityWithOutings.activity.costTurist}
																</p>
																<p class="mb-2">
																	<strong>Ciudad:</strong>
																	${activityWithOutings.activity.city}
																</p>
																<p class="mb-0">
																	<strong>Fecha de registro:</strong>
																	${DateUtils.parseIsoStringToFormattedDate(activityWithOutings.activity.registrationDate)}
																</p>
															</div>
														</div>
													</div>

													<!-- Salidas de la actividad -->
													<h6 class="border-top pt-3 mb-3">
														<i class="bi bi-calendar-event me-2"></i>Salidas
														Turísticas
													</h6>
													<c:choose>
														<c:when test="${not empty activityWithOutings.outings}">
															<div class="table-responsive">
																<table class="table table-sm table-hover">
																	<thead class="table-light">
																		<tr>
																			<th>Nombre</th>
																			<th>Fecha de salida</th>
																			<th>Punto de salida</th>
																			<th>Cupos máximos</th>
																			<th>Fecha de alta</th>
																			<th>Acciones</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach var="outing"
																			items="${activityWithOutings.outings.getOuting()}">
																			<tr>
																				<td><strong>${outing.outingName}</strong></td>
																				<td>${DateUtils.parseIsoStringToFormattedDateTime(outing.getDepartureDate())}</td>
																				<td>${outing.departurePoint}</td>
																				<td><span class="badge bg-info">
																						${outing.maxNumTourists} </span></td>
																				<td>${DateUtils.parseIsoStringToFormattedDate(outing.getDischargeDate())}</td>
																				<td><a
																					href="${pageContext.request.contextPath}/outings?q=${activityWithOutings.activity.activityName}"
																					class="btn btn-sm btn-outline-primary"
																					title="Ver detalles de la salida"> <i
																						class="bi bi-eye me-1"></i>Ver
																				</a></td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
														</c:when>
														<c:otherwise>
															<div class="alert alert-info">
																<i class="bi bi-info-circle me-2"></i>Esta actividad no
																tiene salidas turísticas registradas.
															</div>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-info text-center">
									<i class="bi bi-info-circle me-2"></i> Este proveedor no tiene
									actividades turísticas registradas.
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>

					<!-- Contenido para TOURIST -->
					<c:if test="${usuario.userType == 'TOURIST'}">
						<c:choose>
							<c:when test="${not empty outingInscriptions}">
								<div class="accordion" id="accordionInscriptions">
									<c:forEach var="inscription" items="${outingInscriptions}"
										varStatus="insStatus">
										<div class="accordion-item">
											<h2 class="accordion-header"
												id="headingIns${insStatus.index}">
												<button
													class="accordion-button ${insStatus.index != 0 ? 'collapsed' : ''}"
													type="button" data-bs-toggle="collapse"
													data-bs-target="#collapseIns${insStatus.index}"
													aria-expanded="${insStatus.index == 0 ? 'true' : 'false'}"
													aria-controls="collapseIns${insStatus.index}">
													<i class="bi bi-calendar-event me-2"></i> <strong>${inscription.turistOuting.outingName}</strong>
													<span class="mx-2 text-muted">—</span> <span
														class="text-muted small">${inscription.turistOuting.activityName}</span>
												</button>
											</h2>
											<div id="collapseIns${insStatus.index}"
												class="accordion-collapse collapse ${insStatus.index == 0 ? 'show' : ''}"
												aria-labelledby="headingIns${insStatus.index}"
												data-bs-parent="#accordionInscriptions">
												<div class="accordion-body">
													<!-- Información de la salida -->
													<div class="mb-3 p-3 bg-light rounded">
														<div
															class="d-flex justify-content-between align-items-start mb-3">
															<h6 class="mb-0">
																<i class="bi bi-info-circle me-2"></i>Información de la
																Salida
															</h6>
															<div>
																<a
																	href="${pageContext.request.contextPath}/outings?q=${inscription.turistOuting.activityName}"
																	class="btn btn-sm btn-outline-primary me-2"
																	title="Ver información completa de la salida"> <i
																	class="bi bi-calendar-event me-1"></i>Ver salida
																</a> <a
																	href="${pageContext.request.contextPath}/activities?q=${inscription.turistOuting.activityName}"
																	class="btn btn-sm btn-primary"
																	title="Ver información completa de la actividad"> <i
																	class="bi bi-geo-alt me-1"></i>Ver actividad
																</a>
															</div>
														</div>
														<div class="row">
															<div class="col-md-6">
																<p class="mb-2">
																	<strong>Salida:</strong>
																	${inscription.turistOuting.outingName}
																</p>
																<p class="mb-2">
																	<strong>Actividad:</strong>
																	${inscription.turistOuting.activityName}
																</p>
																<p class="mb-2">
																	<strong>Fecha de salida:</strong>
																	${DateUtils.parseIsoStringToFormattedDateTime(inscription.turistOuting.departureDate)}
																	</p>
																<p class="mb-0">
																	<strong>Punto de salida:</strong>
																	${inscription.turistOuting.departurePoint}
																</p>
															</div>
															<div class="col-md-6">
																<p class="mb-2">
																	<strong>Cupos máximos:</strong> <span
																		class="badge bg-info">${inscription.turistOuting.maxNumTourists}</span>
																</p>
																<c:if test="${isOwnProfile}">
																	<p class="mb-2">
																		<strong>Fecha de inscripción:</strong>
																		${DateUtils.parseIsoStringToFormattedDate(inscription.getInscriptionDate())}
																	</p>
																	<p class="mb-2">
																		<strong>Cantidad de turistas:</strong> <span
																			class="badge bg-primary">${inscription.touristAmount}</span>
																	</p>
																	<p class="mb-0">
																		<strong>Costo total:</strong> <span
																			class="text-success fw-bold">$${inscription.totalCost}</span>
																	</p>
																</c:if>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-info text-center">
									<i class="bi bi-info-circle me-2"></i> Este turista no tiene
									inscripciones a salidas turísticas.
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">
						<i class="bi bi-x-circle me-2"></i>Cerrar
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>

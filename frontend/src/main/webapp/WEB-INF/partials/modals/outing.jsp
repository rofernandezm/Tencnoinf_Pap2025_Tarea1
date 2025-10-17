<div class="modal fade" id="modalOuting" tabindex="-1"
	aria-labelledby="modalOutingLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modalOutingLabel">Alta de salida
					turística</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Cerrar"></button>
			</div>
			<div class="modal-body pb-0">
				<form id="outingForm" method="post"
					action="<%=request.getContextPath()%>/outings/add">
					<div class="row align-items-center mb-3">
						<label for="activitySelect" class="col-sm-6 col-form-label">Actividades
							turísticas</label>
						<div class="col-sm-6">

							<select id="activitySelect" name="activitySelect" required class="form-select">
								<%
								String[] userActivities = (String[]) request.getAttribute("userActivities");
								for (String a : userActivities) {
								%>
								<option value="<%=a%>">
									<%=a%>
								</option>
								<%
								}
								%>
							</select>
						</div>
					</div>
					<div class="row align-items-center mb-3">
						<label for="outingName" class="col-sm-6 col-form-label">Nombre
							de salida turística:</label>
						<div class="col-sm-6">
							<input type="text" id="outingName" name="outingName" required class="form-control">
						</div>
					</div>

					<div class="row align-items-center mb-3">
						<label for="maxTourists" class="col-sm-6 col-form-label">Máxima
							cantidad de turistas:</label>
						<div class="col-sm-6">
							<input type="number" id="maxTourists" name="maxTourists" required class="form-control">
						</div>
					</div>
					<div class="row align-items-center mb-3">
						<label for="outingPlace" class="col-sm-6 col-form-label">Lugar
							de salida:</label>
						<div class="col-sm-6">
							<input type="text" id="outingPlace" name="outingPlace" required class="form-control">
						</div>
					</div>
					<div class="row align-items-center mb-3">
						<label for="outingDate" class="col-sm-6 col-form-label">Fecha
							y hora de salida:</label>
						<div class="col-sm-6">
							<input type="datetime-local" id="outingDate" name="outingDate" required class="form-control">
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" id="confirmOuting">Confirmar</button>
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cancelar</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

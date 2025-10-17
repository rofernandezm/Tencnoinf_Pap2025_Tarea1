<div class="modal fade" id="modalActivity" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <form method="post" action="<%=request.getContextPath()%>/activities">
        <div class="modal-header">
          <h5 class="modal-title">Agregar Actividad</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <!-- campos -->
          <input type="text" class="form-control" name="nombre" placeholder="Nombre">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
          <button type="submit" class="btn btn-primary">Guardar</button>
        </div>
      </form>
    </div>
  </div>
</div>

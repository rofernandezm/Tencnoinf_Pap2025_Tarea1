<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="es">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Agregar actividad</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

  <style>
    :root { --bs-primary: rgb(68,111,175); --bs-primary-rgb: 68,111,175; --bs-body-bg: #fff; }
  </style>
</head>
<body class="d-flex flex-column min-vh-100">

	<%@ include file="header.jsp" %>

  <!-- Formulario -->
  <main class="py-4">
    <div class="container">
      <div class="card shadow-sm border-0">
        <div class="card-header bg-primary text-white">
          <h5 class="mb-0" id="formTitulo">Agregar actividad</h5>
        </div>

        <form id="formActividad" class="needs-validation" novalidate enctype="multipart/form-data">
          <div class="card-body">
            <input type="hidden" id="actId" name="id">

            <div class="row g-3">
              <div class="col-12">
                <label for="actTitle" class="form-label">Nombre de la actividad *</label>
                <input type="text" class="form-control" id="actTitle" name="title" required>
                <div class="invalid-feedback">Ingresá el nombre.</div>
              </div>

              <div class="col-md-6">
                <label for="actProvider" class="form-label">Proveedor *</label>
                <input type="text" class="form-control" id="actProvider" name="provider" required>
                <div class="invalid-feedback">Ingresá el proveedor.</div>
              </div>

              <div class="col-md-6">
                <label for="actCity" class="form-label">Ciudad *</label>
                <input type="text" class="form-control" id="actCity" name="city" required>
                <div class="invalid-feedback">Ingresá la ciudad.</div>
              </div>

              <div class="col-md-6">
                <label class="form-label">Duración *</label>
                <div class="input-group">
                  <input type="number" min="1" step="1" class="form-control" id="actDurationHours" name="durationHours" required>
                  <span class="input-group-text">horas</span>
                  <div class="invalid-feedback">Ingresá la duración en horas.</div>
                </div>
              </div>

              <div class="col-md-6">
                <label class="form-label">Costo por turista *</label>
                <div class="input-group">
                  <span class="input-group-text">UYU</span>
                  <input type="number" min="0" step="1" class="form-control" id="actCost" name="cost" required>
                  <div class="invalid-feedback">Ingresá el costo en UYU.</div>
                </div>
              </div>

              <div class="col-12">
                <label for="actDescription" class="form-label">Descripción *</label>
                <textarea class="form-control" id="actDescription" name="description" rows="4" required></textarea>
                <div class="invalid-feedback">Ingresá la descripción.</div>
              </div>

              <div class="col-12">
                <label for="actImage" class="form-label">Imagen</label>
                <input type="file" class="form-control" id="actImage" name="image" accept="image/*">
              </div>
            </div>
          </div>

          <div class="card-footer d-flex justify-content-end gap-2">
            <a href="actividades.html" class="btn btn-outline-secondary">Cancelar</a>
            <button type="submit" id="btnSubmit" class="btn btn-primary">Crear actividad</button>
          </div>
        </form>
      </div>
    </div>
  </main>

  <%@ include file="footer.jsp" %>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>

  <!-- Validación + redirección de ejemplo -->
  <script>
    (function () {
      const form = document.getElementById('formActividad');
      form.addEventListener('submit', function (e) {
        if (!form.checkValidity()) {
          e.preventDefault();
          e.stopPropagation();
          form.classList.add('was-validated');
          return;
        }
        // Simula guardado y vuelve al listado
        e.preventDefault();
        alert('Actividad creada (simulado). Redirigiendo al listado…');
        window.location.href = 'actividades.html';
      });
    })();
  </script>
</body>
</html>

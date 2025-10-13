<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Detalle de actividad</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

  <style>
    :root { --bs-primary: rgb(68,111,175); --bs-primary-rgb: 68,111,175; --bs-body-bg: #fff; }
    .hero-img { height: 320px; width: 100%; object-fit: cover; }
  </style>
</head>

<body class="d-flex flex-column min-vh-100">

  <%@ include file="header.jsp" %>

  <!-- Contenido -->
  <main class="py-4">
    <div class="container">
      <div class="card border-0 shadow-sm">
        <div class="card-header bg-primary text-white">
          <h5 class="mb-0">Caminata por la Rambla</h5>
        </div>
        <div class="card-body">
          <img src="Imagenes/turis.jpg" alt="Imagen de la actividad" class="img-fluid rounded mb-3 hero-img">

          <p class="mb-3">
            <strong>Descripción:</strong>
            Recorrido guiado con vistas a la costa. Punto de encuentro: Rambla y Ejido. Ideal para familias.
          </p>

          <dl class="row mb-4">
            <dt class="col-sm-4">Proveedor</dt><dd class="col-sm-8">Montevideo Aventura</dd>
            <dt class="col-sm-4">Ciudad</dt><dd class="col-sm-8">Montevideo</dd>
            <dt class="col-sm-4">Duración</dt><dd class="col-sm-8">2 horas</dd>
            <dt class="col-sm-4">Costo por turista</dt><dd class="col-sm-8">UYU 850</dd>
          </dl>

          <div class="d-flex gap-2">
            <a href="salidas.html?actividad=1" class="btn btn-primary">Ver salidas</a>
            <a href="actividades.html" class="btn btn-outline-secondary">Volver</a>
          </div>
        </div>
      </div>
    </div>
  </main>

  <%@ include file="footer.jsp" %>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>

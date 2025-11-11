
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="java.util.*"%>
<%@page import="turismouyapp.utils.DateUtils"%>
<%@page import="turismouyapp.webservices.DtTouristActivity"%>
<%@ page import="turismouyapp.webservices.DtActivityWithOutings"%>
<%@ page import="turismouyapp.webservices.DtTouristOuting"%>

<%
String ctx = request.getContextPath();
@SuppressWarnings("unchecked")
List<DtActivityWithOutings> actWtOuts = (List<DtActivityWithOutings>) request.getAttribute("activitiesWithOutings");
if (actWtOuts == null) {
    actWtOuts = Collections.emptyList();
}

String activityImgPath = ctx + "/activity_img";
String defaultImgPath = ctx + "/res/default_activity.jpg";
%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>TurismoUy</title>
<link rel="icon" type="image/png" href="<%=ctx%>/res/turismouyAppIcon.png">
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
    crossorigin="anonymous">
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<link rel="stylesheet"
    href="<%=request.getContextPath()%>/assets/css/app.css">
<link rel="stylesheet"
    href="<%=request.getContextPath()%>/assets/css/inscriptionsStyles.css">
</head>

<body class="d-flex flex-column min-vh-100">
    <!-- Navbar -->
    <%
    request.setAttribute("navActive", "inscriptions"); // activities | outings | inscriptions
    %>
    <jsp:include page="/WEB-INF/partials/header.jsp" />
    <!-- End Navbar-->
    <main class="flex-fill pt-5 mt-5">
        <div class="container pt-1">
            <!-- Start Searchbar -->
            <jsp:include page="/WEB-INF/partials/searchbar.jsp" />
            <!-- End Searchbar -->

            <!-- Start Cards -->
            <%
            String status = request.getParameter("status");
            @SuppressWarnings("unchecked")
            List<String> errs = (List<String>) request.getAttribute("errors");
            if ("ok".equals(status)) {
            %>
            <div class="alert alert-success">Inscripción registrada con
                éxito.</div>
            <%
            } else if (errs != null && !errs.isEmpty()) {
            %>
            <div class="alert alert-danger alert-dismissible fade show">
                <ul class="mb-0">
                    <%
                    for (String e : errs) {
                    %><li><%=e%></li>
                    <%
                    }
                    %>
                </ul>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <%
            }

            if (actWtOuts.isEmpty()) {
            %>
            <div class="alert alert-info alert-dismissible fade show"><%=request.getAttribute("info")%><button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>

            <%
            } else {

            int aIdx = 0;
            DtActivityWithOutings act = actWtOuts.get(aIdx);
            DtTouristActivity a = act.getActivity();
            String accId = "acc_" + aIdx;
            %>
            <section class="card mb-3">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img
                            src="<%= (a.getImageActPath() != null && !a.getImageActPath().isEmpty()) ? activityImgPath + "/" + a.getImageActPath() : defaultImgPath %>"
                            class="img-fluid rounded-start" alt="Imagen de la actividad">
                    </div>

                    <div class="card-body p-0 col-md-8">
                        <div class="row h-100 g-0">
                            <div class="container p-3 col-md-4">
                                <h5 class="card-title">Datos de la actividad</h5>
                                <ul class="list-unstyled mb-0 small">
                                    <li><strong>Nombre:</strong> <%=a.getActivityName()%></li>
                                    <li><strong>Descripción:</strong> <%=a.getDescription()%></li>
                                    <li><strong>Duración:</strong> <%=DateUtils.parseDurationToHoursString(a.getDuration())%></li>
                                    <li><strong>Costo por turista:</strong> $<%=a.getCostTurist()%></li>
                                    <li><strong>Ciudad:</strong> <%=a.getCity()%></li>
                                    <li><strong>Proveedor:</strong> <%=a.getSupplierNickname()%></li>
                                </ul>
                            </div>

                            <div class="col-md-8">

                                <%
                                List<DtTouristOuting> outs = act.getOutings() != null ? act.getOutings().getOuting() : java.util.Collections.emptyList();
                                String outingParam = request.getAttribute("outing") != null ? (String)request.getAttribute("outing") : request.getParameter("outing") != null ? request.getParameter("outing"): null;
                                DtTouristOuting sel = null;
                                if (!outs.isEmpty()) {
                                    if (outingParam != null) {
                                        for (DtTouristOuting o : outs) {
                                            if (outingParam.equals(o.getOutingName())) {
                                                sel = o;
                                                break;
                                            }
                                        }
                                    }
                                    if (sel == null)
                                        sel = outs.get(0);
                                }
								@SuppressWarnings("unchecked")
                                Map<String, Integer> dispMap = (Map<String, Integer>) request.getAttribute("dispPorSalida");
                                %>

                                <div class="p-3 col-md-12 border-bottom">
                                    <h5 class="card-title">Datos de la salida</h5>

                                    <!-- Combo de salidas -->
                                    <div class="row g-3 align-items-center">
                                        <div class="col-auto">
                                            <label class="form-label small fw-bold m-0"
                                                for="outingSelect">Nombre:</label>
                                        </div>
                                        <div class="col-auto">
                                            <select id="outingSelect"
                                                class="form-select form-select-sm m-0">
                                                <%
                                                for (DtTouristOuting o : outs) {
                                                %>
                                                <option value="<%=o.getOutingName()%>"
                                                    data-point="<%=o.getDeparturePoint()%>"
                                                    data-max="<%=o.getMaxNumTourists()%>"
                                                    data-cost="<%=a.getCostTurist()%>"
                                                    data-date="<%=DateUtils.parseIsoStringToFormattedDateTime(o.getDepartureDate())%>"
                                                    data-disp=<%=dispMap != null && dispMap.get(o.getOutingName()) != null ? dispMap.get(o.getOutingName()) : 0%>
                                                    <%=(sel != null && sel.getOutingName().equals(o.getOutingName())) ? "selected" : ""%>>
                                                    <%=o.getOutingName()%>
                                                </option>
                                                <%
                                                }
                                                %>
                                            </select>
                                        </div>
                                    </div>

                                    <!-- Datos de la salida seleccionada -->
                                    <ul class="list-unstyled my-0 small">
                                        <li><strong>Cupos:</strong> <span id="capMax"><%=sel != null ? sel.getMaxNumTourists() : "-"%></span></li>
                                        <li><strong>Disponibilidad:</strong> <span id="capAvail"><%=dispMap != null && sel != null ? dispMap.get(sel.getOutingName()) : "-"%></span></li>
                                        <li><strong>Punto de salida:</strong> <span id="depPoint"><%=sel != null ? sel.getDeparturePoint() : "-"%></span></li>
                                        <li><strong>Fecha de salida:</strong> <span id="depDate"><%=sel != null ? sel.getDepartureDate() : ""%></span></li>
                                    </ul>
                                </div>
                                <div class="p-3">
                                    <h5 class="card-title mb-3">Datos de la inscripción</h5>
                                    <form class="small" method="post"
                                        action="<%=request.getContextPath()%>/inscriptions">
                                        <input type="hidden" name="activity"
                                            value="<%=a.getActivityName()%>">
                                            <input type="hidden" id="outingHidden" name="outing"
                                            value="<%=(sel != null ? sel.getOutingName() : "")%>">
                                        <input type="hidden" id="inscriptionDate"
                                            name="inscriptionDate">
                                        <div class="row align-items-center">
                                            <label for="touristSeats"
                                                class="col-sm-4 col-form-label fw-bold">Cupos a
                                                reservar:</label>
                                            <div class="col-sm-8">
                                                <input type="number" id="touristSeats" name="seats"
                                                    class="form-control form-control-sm" min="1" required
                                                    placeholder="Ej: 2">
                                            </div>
                                        </div>
                                        <div class="row align-items-center">
                                            <label for="totalCost"
                                                class="col-sm-4 col-form-label fw-bold">Costo total:</label>
                                            <div class="col-sm-8">
                                                <input type="text" id="totalCost" name="cost"
                                                    class="form-control form-control-sm" placeholder="$"
                                                    readonly>
                                            </div>
                                        </div>

                                        <div class="d-flex justify-content-end gap-5 mt-3">
                                            <a class="btn btn-secondary"
                                                href="<%=request.getContextPath()%>/inscriptions">Cancelar</a>
                                            <button class="btn btn-primary" type="submit">Inscribirme</button>
                                        </div>
                                    </form>



                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <%
            }
            %>
            <!-- End Cards -->
        </div>
    </main>
    <!-- Footer -->
    <jsp:include page="/WEB-INF/partials/footer.jsp" />
    <!-- End Footer -->
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
    <script src="<%=request.getContextPath()%>/assets/js/app.js" defer></script>
    <script>
        (function() {
            var sel = document.getElementById('outingSelect');
            var hiddOut = document.getElementById('outingHidden');
            var capMax = document.getElementById('capMax');
            var capAvail = document.getElementById('capAvail');
            var depPoint = document.getElementById('depPoint');
            var depDate = document.getElementById('depDate');
            var seatsInp = document.getElementById('touristSeats');
            var totalInp = document.getElementById('totalCost');

            function getCostPer() {
                if (!sel || !sel.options.length)
                    return 0;
                var opt = sel.options[sel.selectedIndex];
                return Number(opt.getAttribute('data-cost')) || 0;
            }

            function refreshFromSelect() {
                if (!sel || !sel.options.length)
                    return;
                var opt = sel.options[sel.selectedIndex];
                var name = opt.value;
                var max = opt.getAttribute('data-max') || '-';
                var disp = opt.getAttribute('data-disp') || '-';
                var point = opt.getAttribute('data-point') || '-';
                var date = opt.getAttribute('data-date') || '';


                hiddOut.value = name;
                capMax.textContent = max;
                capAvail.textContent = disp;
                depPoint.textContent = point;
                depDate.textContent = date ? date.replace('T', ' ') : '-';

                // opcional: restringir cupos máximos
                if (seatsInp)
                    seatsInp.max = (disp && disp !== '-') ? disp : '';

                recalcTotal();
            }

            function recalcTotal() {
                if (!totalInp)
                    return;
                var costPer = getCostPer();
                var n = parseInt(seatsInp && seatsInp.value ? seatsInp.value
                        : '0', 10) || 0;
                var total = n * costPer;
                totalInp.value = total > 0 ? ('$' + total
                        .toLocaleString('es-UY')) : '';
            }

            if (sel)
                sel.addEventListener('change', refreshFromSelect);
            if (seatsInp)
                seatsInp.addEventListener('input', recalcTotal);

            // estado inicial
            refreshFromSelect();
        })();
    </script>




</body>

</html>

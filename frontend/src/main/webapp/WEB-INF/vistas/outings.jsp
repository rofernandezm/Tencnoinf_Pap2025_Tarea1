
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@page import="turismouyapp.utils.DateUtils"%>
<%@page import="turismouyapp.webservices.DtTouristActivity"%>
<%@ page import="java.util.*"%>
<%@ page import="turismouyapp.webservices.DtActivityWithOutings"%>
<%@ page import="turismouyapp.webservices.DtTouristOuting"%>

<%
String ctx = request.getContextPath();
String activityImgPath = ctx + "/activity_img";
String outingImgPath = ctx + "/outing_img";

@SuppressWarnings("unchecked")
List<DtActivityWithOutings> actWtOuts = (List<DtActivityWithOutings>) request.getAttribute("activitiesWithOutings");
if (actWtOuts == null) {
    actWtOuts = Collections.emptyList();
}
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
    href="<%=request.getContextPath()%>/assets/css/outingsStyles.css">
</head>

<body class="d-flex flex-column min-vh-100">
    <!-- Navbar -->
    <%
    request.setAttribute("navActive", "outings"); // activities | outings | inscriptions
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
            <div class="alert alert-success">Salida registrada con
                éxito.</div>
            <%
            } else if (errs != null && !errs.isEmpty()) {
            %>
            <div class="alert alert-danger">
                <ul class="mb-0">
                    <%
                    for (String e : errs) {
                    %><li><%=e%></li>
                    <%
                    }
                    %>
                </ul>
            </div>
            <%
            }
            %>
            <%
            if (actWtOuts.isEmpty()) {
            %>
            <div class="alert alert-info">No hay coincidencias.</div>
            <%
            } else {

            int aIdx = 0;
            @SuppressWarnings("unchecked")
            Map<String, Integer> dispMap = (Map<String, Integer>) request.getAttribute("dispPorSalida");
            for (DtActivityWithOutings actWtOut : actWtOuts) {
                DtTouristActivity act = actWtOut.getActivity();
                String accId = "acc_" + aIdx;
            %>
            <section class="card mb-3">
                <div class="row g-0">
                    <div class="col-md-3">
                        <img
                            src="<%= activityImgPath + "/" + act.getImageActPath() %>"
                            class="img-fluid rounded-start" alt="Imagen de la actividad">
                    </div>

                    <div class="card-body p-0 col-md-9">
                        <div class="row h-100 g-0">
                            <div class="container p-3 col-md-4">
                                <h5 class="card-title"><%=act.getActivityName()%></h5>
                                <ul class="list-unstyled mb-0 small">
                                    <li><strong>Descripción:</strong> <%=act.getDescription()%></li>
                                    <li><strong>Duración:</strong> <%=DateUtils.parseDurationToHoursString(act.getDuration())%></li>
                                    <li><strong>Costo por turista:</strong> $<%=act.getCostTurist()%></li>
                                    <li><strong>Ciudad:</strong> <%=act.getCity()%></li>
                                    <li><strong>Proveedor:</strong> <%=act.getSupplierNickname()%></li>
                                </ul>
                            </div>
                            <div class="col-md-8">
                                <!-- Start Accordion -->
                                <div class="accordion accordion-flush" id="<%=accId%>">
                                    <%
                                    List<DtTouristOuting> outs = actWtOut.getOutings().getOuting();
                                    if (outs == null || outs.isEmpty()) {
                                    %>
                                    <div class="p-3">
                                        <em>No hay salidas para esta actividad.</em>
                                    </div>
                                    <%
                                    } else {
                                    int oIdx = 0;
                                    for (DtTouristOuting outing_ : outs) {
                                        String collapseId = "col_" + aIdx + "_" + oIdx;
                                        String modalId = "modal_" + aIdx + "_" + oIdx;
                                    %>
                                    <div class="accordion-item">
                                        <h2 class="accordion-header">
                                            <button class="accordion-button collapsed" type="button"
                                                data-bs-toggle="collapse" data-bs-target="#<%=collapseId%>"
                                                aria-expanded="false" aria-controls="<%=collapseId%>">
                                                <%=outing_.getOutingName()%>
                                            </button>
                                        </h2>

                                        <div id="<%=collapseId%>" class="accordion-collapse collapse"
                                            data-bs-parent="#<%=accId%>">
                                            <div class="accordion-body pt-0">
                                                <div class="row h-100 g-0">
                                                    <div class="col-md-8">
                                                        <ul class="list-unstyled my-0 small">
                                                            <li><strong>Cupos:</strong> <%=outing_.getMaxNumTourists()%></li>
                                                            <li><strong>Disponibilidad:</strong> <%=dispMap != null && dispMap.get(outing_.getOutingName()) != null ? dispMap.get(outing_.getOutingName()) : 0%></li>
                                                            <li><strong>Punto de salida:</strong> <%=outing_.getDeparturePoint()%></li>
                                                            <li><strong>Fecha de salida:</strong> <%=DateUtils.parseIsoStringToFormattedDateTime(outing_.getDepartureDate())%></li>
                                                        </ul>
                                                    </div>
                                                    <div
                                                        class="col-md-4 d-flex align-items-center justify-content-center">
                                                        <!-- BOTÓN QUE ABRE EL MODAL -->
                                                        <button class="btn btn-primary mb-0" type="button"
                                                            data-bs-toggle="modal" data-bs-target="#<%=modalId%>">
                                                            Más info.</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- MODAL PARA ESTA SALIDA -->
                                    <div class="modal fade" id="<%=modalId%>" tabindex="-1"
                                        aria-hidden="true">
                                        <div class="modal-dialog modal-lg modal-dialog-centered">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">
                                                        <%=act.getActivityName()%>
                                                        —
                                                        <%=outing_.getOutingName()%>
                                                    </h5>
                                                    <button type="button" class="btn-close btn-close-white"
                                                        data-bs-dismiss="modal" aria-label="Cerrar"></button>
                                                </div>

                                                <div class="modal-body">

                                                    <div class="row g-3">
                                                        <div class="col-md-6">
                                                            <img
                                                                src="<%= outingImgPath + "/" + outing_.getImageOutPath() %>"
                                                                class="img-fluid" alt="Imagen de la actividad">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <h6 class="mb-1">Actividad</h6>
                                                            <ul class="list-unstyled small mb-2">
                                                                <li><strong>Descripción:</strong> <%=act.getDescription()%></li>
                                                                <li><strong>Duración:</strong> <%=DateUtils.parseDurationToHoursString(act.getDuration())%></li>
                                                                <li><strong>Costo por turista:</strong> $<%=act.getCostTurist()%></li>
                                                                <li><strong>Ciudad:</strong> <%=act.getCity()%></li>
                                                                <li><strong>Proveedor:</strong> <%=act.getSupplierNickname()%></li>
                                                            </ul>
                                                            <h6 class="mb-1">Salida</h6>
                                                            <ul class="list-unstyled small mb-0">
                                                                <li><strong>Punto de salida:</strong> <%=outing_.getDeparturePoint()%></li>
                                                                <li><strong>Fecha de salida:</strong> <%=DateUtils.parseIsoStringToFormattedDateTime(outing_.getDepartureDate())%></li>
                                                                <li><strong>Cupos totales:</strong> <%=outing_.getMaxNumTourists()%></li>
                                                                <%-- Si tuvieramos disponibilidad real, mostrar aca --%>
                                                                <li><strong>Disponibles:</strong> <%=dispMap != null && dispMap.get(outing_.getOutingName()) != null ? dispMap.get(outing_.getOutingName()) : 0%></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                        data-bs-dismiss="modal">Cerrar</button>

                                                    <form method="get"
                                                        action="<%=request.getContextPath()%>/inscriptions"
                                                        class="d-inline">
                                                        <input type="hidden" name="q"
                                                            value="<%=act.getActivityName()%>"> <input
                                                            type="hidden" name="outing"
                                                            value="<%=outing_.getOutingName()%>">
                                                        <button type="submit" class="btn btn-primary <% if(turismouyapp.webservices.UserType.TOURIST != request.getSession().getAttribute("user_role")) {%>d-none<% }%>">Inscribirme</button>
                                                    </form>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <%
                                    oIdx++;
                                    } // for t
                                    } // else
                                    %>
                                    <!-- End Accordion -->
                                </div>

                            </div>
                        </div>
                    </div>

                </div>
            </section>
            <%
            aIdx++;
            }
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


</body>

</html>

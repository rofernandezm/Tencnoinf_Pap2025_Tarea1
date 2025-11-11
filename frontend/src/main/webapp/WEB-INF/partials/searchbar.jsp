<%@page import="turismouyapp.webservices.UserType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String active = (String) request.getAttribute("navActive");
if (active == null) active = "";
String q = request.getParameter("q");
if (q == null)
    q = "";
@SuppressWarnings("unchecked")
List<String> activities = (List<String>) request.getAttribute("activities");
if (activities == null) activities = new ArrayList<String>();
%>

<section class="searchbar px-3 mb-3 d-flex flex-wrap justify-content-center align-items-center gap-2">
    <form id="searchForm" class="flex-grow-1 d-flex" method="get" action="<%=request.getContextPath()%>/<%=active%>">
        <div class="input-group flex-grow-1">
            <input id="searchInput"
                   class="form-control"
                   type="text"
                   name="q"
                   list="actSuggestions"
                   autocomplete="off"
                   placeholder="Buscar actividad por nombre..."
                   value="<%=q%>">
            <datalist id="actSuggestions">
                <% for (String name : activities) { if (name != null && !name.isBlank()) { %>
                    <option value="<%= name %>"></option>
                <% }} %>
            </datalist>

            <button class="btn btn-primary" type="submit">
                <i class="bi bi-search"></i>
            </button>
        </div>
    </form>

    <button type="button" class="btn btn-secondary" id="refreshButton">
        <i class="bi bi-arrow-clockwise"></i>
    </button>

    <button type="button" class="btn btn-success <% if(UserType.SUPPLIER != request.getSession().getAttribute("user_role") || (!active.equals("outings") && !active.equals("activities"))) {%>d-none<% }%>" id="addButton">
        <i class="bi bi-plus-lg"></i> Agregar
    </button>
</section>


<!-- Modales -->
<jsp:include page="/WEB-INF/partials/modals/outing.jsp" />

<script>
document.addEventListener("DOMContentLoaded", function() {
    const active = "<%=active%>";
    const addButton = document.getElementById("addButton");

    addButton.addEventListener("click", function() {

        if (active === "activities") {
            const modalEl = document.getElementById("modalActividadForm");
            new bootstrap.Modal(modalEl).show();
        } else if (active === "outings") {
            const modalEl = document.getElementById("modalOuting");
            new bootstrap.Modal(modalEl).show();
        }
    });
    refreshButton.addEventListener("click", function() {
        window.location.href = "<%=request.getContextPath()%>/<%=active%>";
    });
});

</script>

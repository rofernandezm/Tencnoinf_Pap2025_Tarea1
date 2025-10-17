<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String active = (String) request.getAttribute("navActive");
if (active == null) active = "";
String q = request.getParameter("q");
if (q == null)
    q = "";
String[] activities = (String[]) request.getAttribute("activities");
if (activities == null) activities = new String[0];
%>

<section class="searchbar px-3 mb-3 d-flex justify-content-center">
    <form id="searchForm" class="w-100" method="get"
        action="<%=request.getContextPath()%>/<%=active%>">
        <div class="input-group">
            <div class="position-relative flex-grow-1">
                <input id="searchInput" 
                    class="form-control pe-5" 
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
                <button type="button" id="searchClear"
                    class="btn-close position-absolute top-50 end-0 translate-middle-y me-2"
                    aria-label="Limpiar" hidden></button>
            </div>

            <button class="btn btn-primary search-btn" type="submit">
                <i class="bi bi-search"></i>
            </button>
	
        </div>
    </form>
	 <button type="button" class="btn btn-secondary ms-2" id="refreshButton">
                <i class="bi bi-arrow-clockwise"></i>
            </button>
            <!-- Nuevo botón para abrir el modal -->
            <button 
                type="button" 
                class="btn btn-success ms-2 <% if(turismouyapp.core.dto.UserType.SUPPLIER != request.getSession().getAttribute("user_role") || (!active.equals("outings") && !active.equals("activities"))) {%>d-none<% }%>"
                id="addButton">
                <i class="bi bi-plus-lg"></i> Agregar
            </button>
</section>

<!-- Modales -->
<jsp:include page="/WEB-INF/partials/modals/activity.jsp" />
<jsp:include page="/WEB-INF/partials/modals/outing.jsp" />

<script>
document.addEventListener("DOMContentLoaded", function() {
    const active = "<%=active%>";
    const addButton = document.getElementById("addButton");

    addButton.addEventListener("click", function() {

        if (active === "activities") {
            const modalEl = document.getElementById("modalActivity");
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

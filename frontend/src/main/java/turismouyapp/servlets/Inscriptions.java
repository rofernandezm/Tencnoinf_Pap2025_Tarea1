package turismouyapp.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtInscriptionTouristOuting;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.interfaces.ITouristActivityController;
import turismouyapp.core.interfaces.ITouristOutingAndInscriptionController;

@WebServlet("/inscriptions")
public class Inscriptions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Inscriptions() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FactoryUyTourism fabrica = FactoryUyTourism.getInstance();
		ITouristActivityController itac = fabrica.getITouristActivityController();

		// cargo una lista con los nombres de las actividades para sugirir en la
		// busqueda
		String[] activities = null;
		try {
			activities = itac.listTouristActivities();
		} catch (ActivityDoesNotExistException e) {
			activities = new String[0];
		}
		request.setAttribute("activities", activities);

		// obtengo la busqueda
		String q = request.getParameter("q");
		String needle = (q == null) ? "" : q.trim().toLowerCase();

		// traigo todas las actividades con sus salidas
		List<DtActivityWithOutings> all;
		try {
			all = itac.listTouristActivityData();
		} catch (ActivityDoesNotExistException e) {
			all = java.util.Collections.emptyList();
		}

		// filtro en base a la busqueda
		List<DtActivityWithOutings> filtered = all;
		if (!needle.isEmpty()) {
			filtered = new java.util.ArrayList<>();
			for (DtActivityWithOutings awo : all) {
				boolean matchActivity = awo.getActivity() != null && awo.getActivity().getActivityName() != null
						&& awo.getActivity().getActivityName().toLowerCase().equals(needle);

				if (matchActivity) {
					filtered.add(awo);
				}
			}
		}

		// mando la lista filtrada y muestro pantalla
		request.setAttribute("activitiesWithOutings", filtered);
		request.getRequestDispatcher("WEB-INF/vistas/inscriptions.jsp").forward(request, response);

		// Imprimo por consola el resultado filtrado
		System.out.println("Listado filtrado de actividades con salidas");
		for (DtActivityWithOutings res : filtered) {
			System.out.println("|--" + res.getActivity().getActivityName());
			for (DtTouristOuting dtOuting : res.getOutings()) {
				System.out.println("| |--" + dtOuting.getOutingName());
			}
			System.out.println("| .");
		}
		System.out.println(".");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {

			HttpSession session = request.getSession(false);
			
		    request.setCharacterEncoding("UTF-8");

		    String activity = request.getParameter("activity");
		    String outing   = request.getParameter("outing");
		    String seatsStr = request.getParameter("seats");
		    DtUser loggedUser = (DtUser) session.getAttribute("logged_user");

		    List<String> errors = new ArrayList<>();
		    if (activity == null || activity.isBlank()) errors.add("Actividad requerida.");
		    if (outing == null || outing.isBlank())     errors.add("Salida requerida.");
		    if (loggedUser == null) 					errors.add("Usuario no autenticado.");

		    int seats = 0;
		    try {
		        seats = Integer.parseInt(seatsStr);
		        if (seats <= 0) errors.add("Cupos debe ser mayor a 0.");
		    } catch (Exception e) {
		        errors.add("Cupos inválidos.");
		    }

		    if (!errors.isEmpty()) {
		        request.setAttribute("errors", errors);
//		        doGet(request, response);
		        response.sendRedirect(
			            request.getContextPath() + "/inscriptions?q=" +
			            java.net.URLEncoder.encode(activity, java.nio.charset.StandardCharsets.UTF_8)
			        );
		        return;
		    }

		    try {
		        FactoryUyTourism f = FactoryUyTourism.getInstance();
		        ITouristOutingAndInscriptionController itoaic = f.getITouristOutingAndInscriptionController();
		        ITouristActivityController itac = f.getITouristActivityController();

		        // Traigo datos para validar/calcular
		        DtActivityWithOutings activWithOut = itac.consultTouristActivityData(activity);
		        DtTouristActivity dtactiv = activWithOut.getActivity();
		        DtTouristOuting dtouting  = itoaic.consultTouristOutingData(outing);

		        // Fecha actual del servidor (Montevideo)
		        LocalDate inscriptionDate = LocalDate.now(ZoneId.of("America/Montevideo"));

		        // Costo total (unitario x cupos)
		        float cost = dtactiv.getCostTurist() * seats;

		        // Armo el DTO e (idealmente) persisto
		        DtInscriptionTouristOuting dtinscription =
		            new DtInscriptionTouristOuting(seats, cost, inscriptionDate, dtouting);

		        // TODO: Llamá al método real que guarda la inscripción en tu capa core
		        itoaic.inscriptionDataEntry(dtinscription, loggedUser.getNickname(), outing);

		        // PRG
		        response.sendRedirect(
		            request.getContextPath() + "/inscriptions?status=ok&q=" +
		            java.net.URLEncoder.encode(activity, java.nio.charset.StandardCharsets.UTF_8)
		        );

		    } catch (Exception ex) {
		        errors.add(ex.getMessage() != null ? ex.getMessage() : "No se pudo registrar la inscripción.");
		        request.setAttribute("errors", errors);
		        response.sendRedirect(
			            request.getContextPath() + "/inscriptions?q=" +
			            java.net.URLEncoder.encode(activity, java.nio.charset.StandardCharsets.UTF_8)
			        );
		    }
		}

}
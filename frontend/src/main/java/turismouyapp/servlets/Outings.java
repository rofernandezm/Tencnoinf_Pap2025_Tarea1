package turismouyapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import turismouyapp.webservices.ActivityDoesNotExistException;
import turismouyapp.webservices.ActivityPortType;
import turismouyapp.webservices.ActivityService;
import turismouyapp.webservices.DtActivityWithOutings;
import turismouyapp.webservices.DtInscriptionTouristOuting;
import turismouyapp.webservices.DtTouristOuting;
import turismouyapp.webservices.DtUser;
import turismouyapp.webservices.OutingAndInscriptionPortType;
import turismouyapp.webservices.OutingAndInscriptionService;
import turismouyapp.webservices.TouristActivityStatus;
import turismouyapp.webservices.UserType;


@WebServlet("/outings")
public class Outings extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final ActivityPortType activityWebService;
	private final OutingAndInscriptionPortType outingAndInscriptionWebService;

	public Outings() {
		super();
		this.activityWebService = new ActivityService().getActivityPort();
		this.outingAndInscriptionWebService = new OutingAndInscriptionService().getOutingAndInscriptionPort();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// cargo una lista con los nombres de las actividades para sugirir en la
		// busqueda
		List<String> activities = null;
		try {
			activities = activityWebService.listTouristActivitiesByStatus(TouristActivityStatus.CONFIRMED);
		} catch (IllegalArgumentException e) {
			activities = new ArrayList<String>();
		}
		request.setAttribute("activities", activities);
		HttpSession session = request.getSession(false);
		// si es un supplier, cargo las actividades que le pertenecen
		if (session.getAttribute("user_role") == UserType.SUPPLIER) {
			DtUser user = (DtUser) session.getAttribute("logged_user");
			List<String> userActivities = null;
			try {
				userActivities = activityWebService.listTouristActivitiesBySupplierNickname(user.getNickname());
			} catch (IllegalArgumentException e) {
				userActivities = new ArrayList<>();
			}
			request.setAttribute("userActivities", userActivities);
		} else {
			request.setAttribute("userActivities", new String[0]);
		}

		// obtengo la busqueda
		String q = request.getParameter("q");
		String needle = (q == null) ? "" : q.trim().toLowerCase();

		// traigo todas las actividades con sus salidas
		List<DtActivityWithOutings> all;
		try {
			all = activityWebService.listTouristActivityData();
		} catch (ActivityDoesNotExistException e) {
			all = Collections.emptyList();
		}

		// filtro en base a la busqueda
		List<DtActivityWithOutings> filtered = all;
		if (!needle.isEmpty()) {
			filtered = new java.util.ArrayList<>();
			for (DtActivityWithOutings awo : all) {
				boolean matchActivity = awo.getActivity() != null && awo.getActivity().getActivityName() != null
						&& awo.getActivity().getActivityName().toLowerCase().contains(needle);

				// (Opcional) también matchear por nombre de salida
				boolean matchOuting = false;
				if (!matchActivity && awo.getOutings() != null) {
					for (DtTouristOuting o : awo.getOutings().getOuting()) {
						if (o.getOutingName() != null && o.getOutingName().toLowerCase().contains(needle)) {
							matchOuting = true;
							break;
						}
					}
				}

				if (matchActivity || matchOuting) {
					filtered.add(awo);
				}
			}
		}

		Map<String, Integer> disponibilidadPorSalida = new HashMap<>();

		for (DtActivityWithOutings awo : all) {
			for (DtTouristOuting salida : awo.getOutings().getOuting()) {
				List<DtInscriptionTouristOuting> inscripciones = outingAndInscriptionWebService
						.listOutingInscription(salida.getOutingName());
				int totalInscriptos = 0;
				if (inscripciones != null) {
					for (DtInscriptionTouristOuting insc : inscripciones) {
						totalInscriptos += insc.getTouristAmount();
					}
				}
				int cantDisp = salida.getMaxNumTourists() - totalInscriptos;
				disponibilidadPorSalida.put(salida.getOutingName(), cantDisp < 0 ? 0 : cantDisp);
			}
		}

		request.setAttribute("dispPorSalida", disponibilidadPorSalida);

		// mando la lista filtrada y muestro pantalla
		request.setAttribute("activitiesWithOutings", filtered);
		request.getRequestDispatcher("WEB-INF/vistas/outings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}

package turismouyapp.servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
import turismouyapp.webservices.ActivityService;
import turismouyapp.webservices.ActivityPortType;
import turismouyapp.webservices.OutingAndInscriptionService;
import turismouyapp.webservices.OutingAndInscriptionPortType;
import turismouyapp.webservices.DtActivityWithOutings;
import turismouyapp.webservices.DtInscriptionTouristOuting;
import turismouyapp.webservices.DtTouristActivity;
import turismouyapp.webservices.DtTouristOuting;
import turismouyapp.webservices.DtUser;
import turismouyapp.webservices.TouristActivityStatus;
import turismouyapp.utils.DateUtils;
import turismouyapp.webservices.ActivityDoesNotExistException;

@WebServlet("/inscriptions")
public class Inscriptions extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final ActivityPortType activityWebService;
	private final OutingAndInscriptionPortType outingAndInscriptionWebService;

	public Inscriptions() {
		super();
		this.activityWebService = new ActivityService().getActivityPort();
		this.outingAndInscriptionWebService = new OutingAndInscriptionService().getOutingAndInscriptionPort();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.handleShowInscription(request, response);

	}

	protected void handleShowInscription(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Cargo una lista con los nombres de las actividades para sugerir en la busqueda
		List<String> activities = null;
		try {

			activities = activityWebService.listTouristActivitiesByStatus(TouristActivityStatus.CONFIRMED);

		} catch (IllegalArgumentException e) {
			activities = new ArrayList<>();
		}
		request.setAttribute("activities", activities);

		// Obtengo la busqueda
		String q = request.getParameter("q");

		if (q == null || q.isEmpty()) {
			q = request.getAttribute("activity") != null ? (String) request.getAttribute("activity") : "";

			if (q == null || q.isEmpty()) {
				request.setAttribute("info", "Por favor, ingrese el nombre de la actividad a la que desea inscribirse en la barra de búsqueda.");
				request.setAttribute("activitiesWithOutings", new ArrayList<>());
				request.getRequestDispatcher("WEB-INF/vistas/inscriptions.jsp").forward(request, response);
				return;
			}
		}
		String needle = q.trim().toLowerCase();

		// Traigo todas las actividades con sus salidas
		List<DtActivityWithOutings> all;
		try {
			all = activityWebService.listTouristActivityData();
		} catch (ActivityDoesNotExistException e) {
			all = Collections.emptyList();
		}

		// Filtro en base a la busqueda
		List<DtActivityWithOutings> filtered = new ArrayList<>();
		int coincidencias = 0;
		for (DtActivityWithOutings awo : all) {
			DtTouristActivity act = awo.getActivity();
			if (act != null && !act.getActivityName().isEmpty()) {

				if (act.getActivityName().equalsIgnoreCase(needle)) {
					filtered.add(awo);
					coincidencias++;
				}
			}
		}
		if (coincidencias == 0) {
			request.setAttribute("info",
					"No hay coincidencias. Asegurese de ingresar el nombre completo de la actividad.");
		} else if (coincidencias > 1) {
			request.setAttribute("info",
					"Más de un resultado para la búsqueda, ingrese el nombre completo de la actividad.");

		} else {
			Map<String, Integer> disponibilidadPorSalida = new HashMap<>();

			for (DtActivityWithOutings awo : all) {
				DtActivityWithOutings.Outings outingsWrapper = awo.getOutings();
				if (outingsWrapper != null && outingsWrapper.getOuting() != null) {
					for (DtTouristOuting salida : outingsWrapper.getOuting()) {
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
			}

			request.setAttribute("dispPorSalida", disponibilidadPorSalida);
		}

		// Mando la lista filtrada y muestro pantalla
		request.setAttribute("activitiesWithOutings", filtered);
		request.getRequestDispatcher("WEB-INF/vistas/inscriptions.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		request.setCharacterEncoding("UTF-8");

		String activity = request.getParameter("activity");
		String outing = request.getParameter("outing");
		String seatsStr = request.getParameter("seats");
		DtUser loggedUser = (DtUser) session.getAttribute("logged_user");

		List<String> errors = new ArrayList<>();
		if (activity == null || activity.isBlank())
			errors.add("Actividad requerida.");
		if (outing == null || outing.isBlank())
			errors.add("Salida requerida.");
		if (loggedUser == null)
			errors.add("Usuario no autenticado.");

		int seats = 0;
		try {
			seats = Integer.parseInt(seatsStr);
			if (seats <= 0)
				errors.add("Cupos debe ser mayor a 0.");
		} catch (Exception e) {
			errors.add("Cupos inválidos.");
		}

		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.setAttribute("activity", activity);
			request.setAttribute("outing", outing);
			this.handleShowInscription(request, response);
			return;
		}

		try {

			// Traigo datos para validar/calcular
			DtActivityWithOutings activWithOut = activityWebService.consultTouristActivityData(activity);
			DtTouristActivity dtactiv = activWithOut.getActivity();
			DtTouristOuting dtouting = outingAndInscriptionWebService.consultTouristOutingData(outing);

			// Verifico que el numero de turistas a inscribir no sea mayor a la cantidad de turistas admitidos en la salida
			if (seats <= dtouting.getMaxNumTourists()) {

				// Verifico que la cantidad de inscriptos mas la nueva inscripcion no supera la cantidad de turistas admitidos en la salida
				List<DtInscriptionTouristOuting> totalInscripTouristOuting = outingAndInscriptionWebService
						.listOutingInscription(outing);
				int totalInscriptos = 0;

				if (totalInscripTouristOuting != null && !totalInscripTouristOuting.isEmpty()) {
					for (DtInscriptionTouristOuting insc : totalInscripTouristOuting) {
						totalInscriptos += insc.getTouristAmount();
					}
				}

				int cantDisp = dtouting.getMaxNumTourists() - totalInscriptos;

				// Supongo que hay cupos suficientes
				if ((cantDisp - seats) >= 0) {
					// Fecha actual del servidor (Montevideo)
					String inscriptionDate = DateUtils.getCurrentDateIso();

					// Costo total (unitario x cupos)
					float cost = dtactiv.getCostTurist() * seats;

					// Armo el DTO e (idealmente) persisto
					DtInscriptionTouristOuting dtinscription = new DtInscriptionTouristOuting();
					dtinscription.setTouristAmount(seats);
					dtinscription.setTotalCost(cost);
					dtinscription.setInscriptionDate(inscriptionDate);
					dtinscription.setTuristOuting(dtouting);

					// cantDisp me da cuantos cupos hay al dia de hoy disponibles para esa salida
					// cantDisp >= 0

					outingAndInscriptionWebService.inscriptionDataEntry(dtinscription, loggedUser.getNickname(),
							outing);

					// PRG
					response.sendRedirect(request.getContextPath() + "/inscriptions?status=ok&q="
							+ URLEncoder.encode(activity, StandardCharsets.UTF_8) + "&outing="
							+ URLEncoder.encode(outing, StandardCharsets.UTF_8));
				} else {
					errors.add("Lamentablemente solo quedan " + cantDisp
							+ " cupos disponibles. No se pudo realizar la inscripcion.");
				}
			} else {
				errors.add("Los cupos a reservar debe ser menor a " + dtouting.getMaxNumTourists() + ".");
			}

			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				request.setAttribute("activity", activity);
				request.setAttribute("outing", outing);
				this.handleShowInscription(request, response);
				return;
			}
		} catch (Exception ex) {
			errors.add(ex.getMessage() != null ? ex.getMessage() : "No se pudo registrar la inscripción.");
			request.setAttribute("errors", errors);
			request.setAttribute("activity", activity);
			request.setAttribute("outing", outing);
			this.handleShowInscription(request, response);
		}
	}

}

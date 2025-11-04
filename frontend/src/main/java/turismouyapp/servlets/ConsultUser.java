package turismouyapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.RequestDispatcher;

// Web Service Stubs (generados por wsimport)
import turismouyapp.webservices.UserService;
import turismouyapp.webservices.UserPortType;
import turismouyapp.webservices.ActivityService;
import turismouyapp.webservices.ActivityPortType;
import turismouyapp.webservices.OutingAndInscriptionService;
import turismouyapp.webservices.OutingAndInscriptionPortType;

// DTOs (generados por wsimport)
import turismouyapp.webservices.DtUser;
import turismouyapp.webservices.DtActivityWithOutings;
import turismouyapp.webservices.DtInscriptionTouristOuting;
import turismouyapp.webservices.UserType;

// Excepciones SOAP (generadas por wsimport)
import turismouyapp.webservices.ActivityDoesNotExistException;

@WebServlet("/consult-user")
public class ConsultUser extends HttpServlet {

	/**
	 * Identificador de versión para serialización.
	 */
	private static final long serialVersionUID = 1L;

	private final UserPortType userWebService;
	private final ActivityPortType activityWebService;
	private final OutingAndInscriptionPortType outingAndInscriptionWebService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConsultUser() {
		super();
		this.userWebService = new UserService().getUserPort();
		this.activityWebService = new ActivityService().getActivityPort();
		this.outingAndInscriptionWebService = new OutingAndInscriptionService().getOutingAndInscriptionPort();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		this.loadUsersList(request);

		String requestedNickname = request.getParameter("nickname");
		if (this.isValidNickname(requestedNickname)) {
			DtUser selectedUser = userWebService.consultUserData(requestedNickname);
			if (selectedUser != null) {

				DtUser loggedUser = (DtUser) session.getAttribute("logged_user");
				request.setAttribute("usuario", selectedUser);

				// Verificar si es el propio perfil (solo si hay un usuario logueado)
				boolean isOwnProfile = loggedUser != null
						&& requestedNickname.equalsIgnoreCase(loggedUser.getNickname());
				request.setAttribute("isOwnProfile", isOwnProfile);

				this.loadUserActivitiesOrInscriptions(request);
			}
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vistas/consultUser.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Carga la lista de usuarios para el combobox.
	 */
	private void loadUsersList(HttpServletRequest request) {
		List<String> userNicknames = userWebService.listUsers();
		request.setAttribute("usuarios", userNicknames);
	}

	/**
	 * Valida que el nickname no sea null ni vacío.
	 */
	private boolean isValidNickname(String nickname) {
		return nickname != null && !nickname.trim().isEmpty();
	}

	/**
	 * Carga las actividades (SUPPLIER) o inscripciones (TOURIST) según el tipo de
	 * usuario.
	 */
	private void loadUserActivitiesOrInscriptions(HttpServletRequest request) {
		DtUser requestedUser = (DtUser) request.getAttribute("usuario");

		if (requestedUser.getUserType() == UserType.SUPPLIER) {
			// Si es proveedor/a se muestra también la información básica de las actividades
			// actividad que ofrece (en estado “Confirmada”) y salidas asociadas.
			this.loadSupplierActivities(request, requestedUser.getNickname());

		} else if (requestedUser.getUserType() == UserType.TOURIST) {
			// Si es turista se muestra también la información de las salidas a las que se
			// inscribió

			// En caso de que un Turista consulte su propio perfil, adicionalmente verá la
			// información de las inscripciones
			// a las salidas (costo, fecha, cantidad de turistas, etc)

			List<DtInscriptionTouristOuting> inscriptions = outingAndInscriptionWebService
					.listDtInscriptionTouristOutingByTouristNickname(requestedUser.getNickname());
			request.setAttribute("outingInscriptions", inscriptions);
		}
	}

	/**
	 * Carga las actividades con salidas de un proveedor.
	 */
	private void loadSupplierActivities(HttpServletRequest request, String supplierNickname) {
		boolean isOwnProfile = request.getAttribute("isOwnProfile") != null
				&& (boolean) request.getAttribute("isOwnProfile");

		// En caso de que un Proveedor consulte su propio perfil, adicionalmente verá
		// las actividades turísticas en los estados “Agregada” y “Rechazada”.
		List<DtActivityWithOutings> activities = Collections.emptyList();
		if (isOwnProfile) {
			try {
				activities = activityWebService.arrayListTouristActivitiesBySupplierNickName(supplierNickname);
			} catch (ActivityDoesNotExistException e) {
				e.printStackTrace();
			}
		} else {
			String[] activityNames = activityWebService.listTouristActivitiesBySupplierNickname(supplierNickname);
			if (activityNames != null && activityNames.length > 0)
				activities = this.buildActivitiesWithOutings(activityNames);
		}
		request.setAttribute("activitiesWithOutings", activities);
	}

	/**
	 * Construye una lista de DtActivityWithOutings a partir de nombres de
	 * actividades.
	 */
	private List<DtActivityWithOutings> buildActivitiesWithOutings(String[] activityNames) {

		List<DtActivityWithOutings> activities = new ArrayList<>();
		if (activityNames != null) {
			for (String activityName : activityNames) {
				DtActivityWithOutings activity = this.getActivityWithOutings(activityName);
				if (activity != null) {
					activities.add(activity);
				}
			}
		}
		return activities;
	}

	/**
	 * Obtiene una actividad con sus salidas, retorna null si no existe.
	 */
	private DtActivityWithOutings getActivityWithOutings(String activityName) {
		try {
			return activityWebService.consultTouristActivityData(activityName);
		} catch (ActivityDoesNotExistException e) {
			return null;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

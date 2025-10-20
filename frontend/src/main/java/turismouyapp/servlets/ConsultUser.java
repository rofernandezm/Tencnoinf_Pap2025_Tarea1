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
import turismouyapp.core.interfaces.IUserController;
import turismouyapp.core.interfaces.ITouristActivityController;
import turismouyapp.core.interfaces.ITouristOutingAndInscriptionController;
import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtInscriptionTouristOuting;
import turismouyapp.core.dto.UserType;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;

@WebServlet("/consult-user")
public class ConsultUser extends HttpServlet {

	/**
	 * Identificador de versión para serialización.
	 */
	private static final long serialVersionUID = 1L;

	private final IUserController iUserController;
	private final ITouristActivityController iActivityController;
	private final ITouristOutingAndInscriptionController iOutingAndInscriptionController;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConsultUser() {
		super();
		FactoryUyTourism factory = FactoryUyTourism.getInstance();
		this.iUserController = factory.getIUserController();
		this.iActivityController = factory.getITouristActivityController();
		this.iOutingAndInscriptionController = factory.getITouristOutingAndInscriptionController();
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
			DtUser selectedUser = iUserController.consultUserData(requestedNickname);
			if (selectedUser != null) {
		    	
		    	DtUser loggedUser = (DtUser) session.getAttribute("logged_user");
				request.setAttribute("usuario", selectedUser);
				request.setAttribute("isOwnProfile", requestedNickname.equalsIgnoreCase(loggedUser.getNickname()));
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
		String[] userNicknames = iUserController.listUsers();
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
			// Si es proveedor/a se muestra también la información básica de las actividades actividad que ofrece (en estado “Confirmada”) y salidas asociadas. 
			this.loadSupplierActivities(request, requestedUser.getNickname());
				
		} else if (requestedUser.getUserType() == UserType.TOURIST) {
			// Si es turista se muestra también la información de las salidas a las que se inscribió
			
			// En caso de que un Turista consulte su propio perfil, adicionalmente verá la información de las inscripciones 
			// a las salidas (costo, fecha, cantidad de turistas, etc)
			
			List<DtInscriptionTouristOuting> inscriptions =  iOutingAndInscriptionController.listDtInscriptionTouristOutingByTouristNickname(requestedUser.getNickname());
			request.setAttribute("outingInscriptions", inscriptions);
		}
	}

	/**
	 * Carga las actividades con salidas de un proveedor.
	 */
	private void loadSupplierActivities(HttpServletRequest request, String supplierNickname) {
		boolean isOwnProfile = request.getAttribute("isOwnProfile") != null && (boolean)request.getAttribute("isOwnProfile");
		
		// En caso de que un Proveedor consulte su propio perfil, adicionalmente verá las actividades turísticas en los estados “Agregada” y “Rechazada”.
		List<DtActivityWithOutings> activities = Collections.emptyList();
		if(isOwnProfile) {
			try {
				activities = iActivityController.listTouristActivitiesBySupplierNickName(supplierNickname);
			}
			catch(ActivityDoesNotExistException e) {
				e.printStackTrace();
			}
		}
		else {
			String[] activityNames = iActivityController.listTouristActivitiesBySupplierNickname(supplierNickname);
			if(activityNames == null || activityNames.length == 0)
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
		for (String activityName : activityNames) {
			DtActivityWithOutings activity = this.getActivityWithOutings(activityName);
			if (activity != null) {
				activities.add(activity);
			}
		}
		return activities;
	}

	/**
	 * Obtiene una actividad con sus salidas, retorna null si no existe.
	 */
	private DtActivityWithOutings getActivityWithOutings(String activityName) {
		try {
			return iActivityController.consultTouristActivityData(activityName);
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

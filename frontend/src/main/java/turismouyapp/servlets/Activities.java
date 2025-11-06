package turismouyapp.servlets;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import turismouyapp.utils.ImageManager;
import turismouyapp.utils.ImageManager.UploadFolderType;
import turismouyapp.webservices.ActivityDoesNotExistException;
import turismouyapp.webservices.ActivityPortType;
import turismouyapp.webservices.ActivityService;
import turismouyapp.webservices.DtActivityWithOutings;
import turismouyapp.webservices.DtTouristActivity;
import turismouyapp.webservices.DtTouristOuting;
import turismouyapp.webservices.RepeatedActivityNameException;
import turismouyapp.webservices.TouristActivityStatus;

@WebServlet("/activities")
@MultipartConfig
public class Activities extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ActivityPortType activityWebService;

	public Activities() {
		super();
		this.activityWebService = new ActivityService().getActivityPort();
	}

	protected void handleShowActivities(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// cargo una lista con los nombres de las actividades para sugirir en la
		// busqueda
		List<String> activities = null;
		try {
			activities = activityWebService.listTouristActivities();
		} catch (ActivityDoesNotExistException ex) {
			ex.printStackTrace();
			activities = new ArrayList<>();
		}
		request.setAttribute("activities", activities);

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
			filtered = new ArrayList<>();
			for (DtActivityWithOutings awo : all) {
				boolean matchActivity = awo.getActivity() != null && awo.getActivity().getActivityName() != null
						&& awo.getActivity().getActivityName().toLowerCase().contains(needle);

				if (matchActivity) {
					filtered.add(awo);
				}
			}
		}

		// mando la lista filtrada y muestro pantalla
		request.setAttribute("activitiesWithOutings", filtered);
		request.getRequestDispatcher("WEB-INF/vistas/activities.jsp").forward(request, response);

		// Imprimo por consola el resultado filtrado
		System.out.println("Listado filtrado de actividades con salidas");
		for (DtActivityWithOutings res : filtered) {
			System.out.println("|--" + res.getActivity().getActivityName());
			DtActivityWithOutings.Outings outingsWrapper = res.getOutings();
			if (outingsWrapper != null && outingsWrapper.getOuting() != null) {
				for (DtTouristOuting dtOuting : outingsWrapper.getOuting()) {
					System.out.println("| |--" + dtOuting.getOutingName());
				}
			}
			System.out.println("| .");
		}
		System.out.println(".");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Mostrar formulario de modificación
		this.handleShowActivities(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.handleRegisterActivity(request, response);
	}

	private void handleRegisterActivity(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// Obtener datos del registro
		String activityName = request.getParameter("title");
		String supplier = request.getParameter("supplier"); // deberia ser el usuario loggeado?
		String city = request.getParameter("city");
		String description = request.getParameter("description");
		LocalDate hora = LocalDate.now();
		Duration duration = null;
		float cost = 0.0f;
		
		try {
			duration = Duration.ofHours(Integer.parseInt(request.getParameter("durationHours")));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			// TODO: Falta manejo de mensajes en caso de error parseInt de durationHours
		}
		
		try {
			cost = Float.parseFloat(request.getParameter("cost"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			// TODO: Falta manejo de mensajes en caso de error parseFloat de cost
		}
		
		// Foto de actividad
		Part activityPhotoPart = request.getPart("image");
		
		// Valida imagen guardada antes del error de duplicado.
		if(request.getAttribute("draftedActivityImgPart") != null) {
			String draftedHash = (String)request.getAttribute("draftedActivityImgHash");
			String currentHash = ImageManager.getFileHash(activityPhotoPart);
			
			if(activityPhotoPart == null || draftedHash.equals(currentHash))
				// Se lanzo error por duplicado, no se solicita nueva carga de imagen.
				activityPhotoPart = (Part)request.getAttribute("draftedActivityImgPart");
			
		}
				
		String fileName = (activityPhotoPart != null && activityPhotoPart.getSize() > 0)
				? ImageManager.generateFileName(activityPhotoPart)
				: ImageManager.resolveDefaultImageName(UploadFolderType.ACTIVITY);
		
		DtTouristActivity newActivity = new DtTouristActivity();
		newActivity.setActivityName(activityName);
		newActivity.setDescription(description);
		newActivity.setDuration(duration != null ? duration.toString() : null);
		newActivity.setCostTurist(cost);
		newActivity.setCity(city);
		newActivity.setRegistrationDate(hora.toString());
		newActivity.setSupplierNickname(supplier);
		newActivity.setStatus(TouristActivityStatus.ADDED);
		newActivity.setImageActPath(fileName);

		try {
			activityWebService.activityDataEntry(newActivity);
			
			// Persistir imagen
			try {
				ImageManager.persistFile(this.getServletContext(), activityPhotoPart, fileName, UploadFolderType.ACTIVITY);
			} catch (IOException ex) {
				
				// Setea imagen default
				String defaultImage = ImageManager.resolveDefaultImageName(UploadFolderType.ACTIVITY);
				newActivity.setImageActPath(defaultImage);

				activityWebService.modifyActivity(newActivity);
			}
			
			request.setAttribute("mensaje",
					"Se ha ingresado correctamente la actividad turística: " + activityName + " en el sistema.");
			request.getRequestDispatcher("/WEB-INF/vistas/activities.jsp").forward(request, response);

		} catch (RepeatedActivityNameException | ActivityDoesNotExistException e) {
			request.setAttribute("activityError", "La actividad \"" + activityName + "\" ya existe.");
			request.setAttribute("draftedActivity", newActivity);
			request.setAttribute("draftedActivityImgPart", activityPhotoPart);
			request.setAttribute("draftedActivityImgHash", ImageManager.getFileHash(activityPhotoPart));
			this.handleShowActivities(request, response);
		}
	}
}

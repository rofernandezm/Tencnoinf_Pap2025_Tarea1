package turismouyapp.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.annotation.MultipartConfig;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.dto.TouristActivityStatus;
import turismouyapp.core.entity.TouristOuting;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.exceptions.RepeatedActivityNameException;
import turismouyapp.core.exceptions.TouristOutingDoesNotExistException;
import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.interfaces.ITouristActivityController;

@WebServlet("/activities")
@MultipartConfig
public class Activities extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ITouristActivityController iTouristActivityController;

	public Activities() {
		super();
		FactoryUyTourism factory = FactoryUyTourism.getInstance();
		this.iTouristActivityController = factory.getITouristActivityController();
	}

	protected void handleShowActivities(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// cargo una lista con los nombres de las actividades para sugirir en la
		// busqueda
		String[] activities = null;
		try {
			activities = iTouristActivityController.listTouristActivities();
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
			all = iTouristActivityController.listTouristActivityData();
		} catch (ActivityDoesNotExistException e) {
			all = java.util.Collections.emptyList();
		}

		// filtro en base a la busqueda
		List<DtActivityWithOutings> filtered = all;
		if (!needle.isEmpty()) {
			filtered = new java.util.ArrayList<>();
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
			for (DtTouristOuting dtOuting : res.getOutings()) {
				System.out.println("| |--" + dtOuting.getOutingName());
			}
			System.out.println("| .");
		}
		System.out.println(".");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Mostrar formulario de modificación
//    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vistas/activities.jsp");
//    	dispatcher.forward(request, response);
		handleShowActivities(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRegisterActivity(request, response);
	}

	private void handleRegisterActivity(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// Obtener datos del registro
		String activityName = request.getParameter("title");
		String supplier = request.getParameter("supplier"); // deberia ser el usuario loggeado?
		String city = request.getParameter("city");
		Duration duration = Duration.ofHours(Integer.parseInt(request.getParameter("durationHours")));
		float cost = Float.parseFloat(request.getParameter("cost"));
		String description = request.getParameter("description");
		LocalDate hora = LocalDate.now();

		// Foto de perfil
		Part activityPhotoPart = request.getPart("image");
		String rawPath_out = getServletContext().getInitParameter("uploadActivityFolder");
		String rawPath_def = getServletContext().getInitParameter("defaultPath_img");

		// Reemplaza la variable ${catalina.base} por su valor real
		String catalinaToken = "${catalina.base}";
		String catalinaBase = System.getProperty("catalina.base");
		String outPath = rawPath_out.replace(catalinaToken, catalinaBase);
		String defPath = rawPath_def.replace(catalinaToken, catalinaBase);

		File uploadDir = new File(outPath);
		if (!uploadDir.exists())
			uploadDir.mkdirs();

		String imagePath = "default_activity.jpg";

		if (activityPhotoPart != null && activityPhotoPart.getSize() > 0) {

			// Obtiene el nombre original (ej: "foto.png")
			String originalName = Path.of(activityPhotoPart.getSubmittedFileName()).getFileName().toString();

			// Extrae la extensión (todo después del último '.')
			String extension = "";
			int i = originalName.lastIndexOf('.');
			if (i > 0) {
				extension = originalName.substring(i); // incluye el punto, ej: ".png"
			}

			// Genera nombre único + extensión
			String fileName = UUID.randomUUID().toString() + extension;

			// Guardar el archivo en el servidor
			activityPhotoPart.write(outPath + File.separator + fileName);

			// Guardar la ruta relativa
			imagePath = fileName;

		} else {

			File out_img = new File(outPath + File.separator + imagePath);
			if (!out_img.exists()) {
				File default_img = new File(defPath + File.separator + imagePath);
				try {
					Files.copy(default_img.toPath(), out_img.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			DtTouristActivity newActivity = new DtTouristActivity(activityName, description, duration, cost, city, hora,
					supplier, TouristActivityStatus.ADDED, imagePath);

			iTouristActivityController.activityDataEntry(newActivity);
			request.setAttribute("mensaje",
					"Se ha ingresado correctamente la actividad turística: " + activityName + " en el sistema.");
			request.getRequestDispatcher("/WEB-INF/vistas/activities.jsp").forward(request, response);

		}catch (RepeatedActivityNameException e) {
			    request.setAttribute("activityError", "La actividad \"" + activityName + "\" ya existe.");
			    handleShowActivities(request, response); 
			    return;
			}   
			
			
    }

}
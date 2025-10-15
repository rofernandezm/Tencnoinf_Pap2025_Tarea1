package turismouyapp.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtSupplier;
import turismouyapp.core.dto.DtTourist;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.dto.TouristActivityStatus;
import turismouyapp.core.entity.TouristOuting;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.exceptions.RepeatedActivityNameException;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;
import turismouyapp.core.exceptions.TouristOutingDoesNotExistException;
import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.interfaces.ITouristActivityController;
import turismouyapp.core.interfaces.ITouristOutingAndInscriptionController;
import turismouyapp.core.interfaces.IUserController;

@WebServlet("/activities")
public class Activities extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Activities() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FactoryUyTourism fabrica = FactoryUyTourism.getInstance();
		ITouristActivityController itac = fabrica.getITouristActivityController();
		
		// cargo una lista con los nombres de las actividades para sugirir en la busqueda
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
	
	private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Obtener datos del registro
        String name = request.getParameter("actName");
        String provider = request.getParameter("actProvider");
        String city = request.getParameter("actCity");
        Duration duration = Duration.ofHours(Integer.parseInt(request.getParameter("actDurationHours")));
        float cost = Float.parseFloat(request.getParameter("actCost"));
        String description = request.getParameter("actDescription");
        LocalDate hora= LocalDate.now();
        
         
		
        Part imagen = request.getPart("actImage");
        String imagePath = null;
        
        if (imagen != null && imagen.getSize() > 0) {
            
        	String rawPath = getServletContext().getInitParameter("uploadFolder");

        	// Reemplaza la variable ${catalina.base} por su valor real
        	String catalinaBase = System.getProperty("catalina.base");
        	String uploadPath = rawPath.replace("${catalina.base}", catalinaBase);

        	File uploadDir = new File(uploadPath);
        	if (!uploadDir.exists()) uploadDir.mkdirs();
        	
        	// Obtiene el nombre original (ej: "foto.png")
        	String originalName = Path.of(imagen.getSubmittedFileName()).getFileName().toString();

        	// Extrae la extensión (todo después del último '.')
        	String extension = "";
        	int i = originalName.lastIndexOf('.');
        	if (i > 0) {
        	    extension = originalName.substring(i); // incluye el punto, ej: ".png"
        	}

        	// Genera nombre único + extensión
        	String fileName = UUID.randomUUID().toString() + extension;

            // Guardar el archivo en el servidor
            imagen.write(uploadPath + File.separator + fileName);

            // Guardar la ruta relativa
            imagePath = fileName;
        }
        
        FactoryUyTourism factory = FactoryUyTourism.getInstance();
		ITouristActivityController itac = factory.getITouristActivityController();
		
		if (checkForm(request, response)) {
			try {
		        DtTouristActivity newActivity = new DtTouristActivity(name, description, duration, cost, imagePath, hora ,provider,TouristActivityStatus.ADDED );
		        
		        itac.activityDataEntry(newActivity);
		      		        
			} catch (RepeatedActivityNameException e) {
				// Muestro error de registro
				request.setAttribute("mensaje", "La actividad " + name + " ya existe.");
				request.setAttribute("activeTab", "register");
				RequestDispatcher rd = request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp");
				rd.forward(request, response);
			
			}   
		}else {
			request.setAttribute("mensaje", "Por favor, llene todos los campos.");
			request.setAttribute("activeTab", "register");
			RequestDispatcher rd = request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp");
			rd.forward(request, response);
		}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// String opcionSeleccionada = request.getParameter("opcionSeleccionada");
		// System.out.println(opcionSeleccionada); // Aca se recibe la seleccion del
		// como por su parametros y se carga en seleccion
		// para pasar al servlets de Consulta Salida
//		 HttpSession session = request.getSession();
//		 session.setAttribute("Salidas", result);

		// Ahora con la opcion seleccionada trago el Datatype para cargar una tabla con
		// ese caso

	}


private boolean checkForm (HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
	
	 String name = request.getParameter("actName");
     String provider = request.getParameter("actProvider");
     String city = request.getParameter("actCity");
     String duration = request.getParameter("actDurationHours");
     String cost = request.getParameter("actCost");
     String description = request.getParameter("actDescription");
  
    
	if(name == null || name.isBlank() || provider == null || provider.isBlank() || city == null || city.isBlank() ||
		duration == null || duration.isBlank() || cost == null || cost.isBlank() ||  description == null || description.isBlank() ) {
		return false;
		}	
	return true;	
}

}
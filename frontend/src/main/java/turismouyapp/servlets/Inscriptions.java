package turismouyapp.servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.entity.TouristOuting;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.exceptions.TouristOutingDoesNotExistException;
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
//		FactoryUyTourism fabrica = FactoryUyTourism.getInstance();
//		ITouristActivityController itac = fabrica.getITouristActivityController();
//		// obtengo la busqueda
//		String q = request.getParameter("q");
//		String needle = (q == null) ? "" : q.trim().toLowerCase();
//
//		// traigo todas las actividades con sus salidas
//		List<DtActivityWithOutings> all;
//		try {
//			all = itac.listTouristActivityData();
//		} catch (ActivityDoesNotExistException e) {
//			all = java.util.Collections.emptyList();
//		}
//
//		// filtro en base a la busqueda
//		List<DtActivityWithOutings> filtered = all;
//		if (!needle.isEmpty()) {
//			filtered = new java.util.ArrayList<>();
//			for (DtActivityWithOutings awo : all) {
//				boolean matchActivity = awo.getActivity() != null && awo.getActivity().getActivityName() != null
//						&& awo.getActivity().getActivityName().toLowerCase().contains(needle);
//
//				// (Opcional) también matchear por nombre de salida
//				boolean matchOuting = false;
//				if (!matchActivity && awo.getOutings() != null) {
//					for (DtTouristOuting o : awo.getOutings()) {
//						if (o.getOutingName() != null && o.getOutingName().toLowerCase().contains(needle)) {
//							matchOuting = true;
//							break;
//						}
//					}
//				}
//
//				if (matchActivity || matchOuting) {
//					filtered.add(awo);
//				}
//			}
//		}
//
//		// mando la lista filtrada y muestro pantalla
//		request.setAttribute("activitiesWithOutings", filtered);
		request.getRequestDispatcher("WEB-INF/vistas/inscriptions.jsp").forward(request, response);

		// Imprimo por consola el resultado filtrado
//		System.out.println("Listado filtrado de actividades con salidas e inscripciones ");
//		for (DtActivityWithOutings res : filtered) {
//			System.out.println("|--" + res.getActivity().getActivityName());
//			for (DtTouristOuting dtOuting : res.getOutings()) {
//				System.out.println("| |--" + dtOuting.getOutingName());
//			}
//			System.out.println("| .");
//		}
//		System.out.println(".");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		 //String opcionSeleccionada = request.getParameter("opcionSeleccionada");
		 //System.out.println(opcionSeleccionada); // Aca se recibe la seleccion del como por su parametros y se carga en seleccion 
		 // para pasar al servlets de Consulta Salida 
//		 HttpSession session = request.getSession();
//		 session.setAttribute("Salidas", result);
		
		 // Ahora con la opcion seleccionada trago el Datatype para cargar una tabla con ese caso
		 
		
	}
}
package turismouyapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import turismouyapp.core.factory.FactoryUyTourism;

/**
 * Servlet principal de la aplicación TurismoUY.
 * <p>
 * Este servlet maneja la página de inicio y proporciona una vista de prueba
 * que muestra todos los usuarios turistas y proveedores registrados en el sistema.
 * Está mapeado a la raíz de la aplicación ("/") y genera una página HTML simple
 * con listados de usuarios obtenidos desde la capa de lógica de negocio.
 * </p>
 * 
 * <p><strong>Ejemplo de uso:</strong></p>
 * <pre>
 * GET http://localhost:8080/turismouy.UI/
 * </pre>
 * 
 * <p>La respuesta incluirá:</p>
 * <ul>
 *   <li>Lista de usuarios turistas registrados</li>
 *   <li>Lista de proveedores de servicios turísticos</li>
 * </ul>
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * @see turismouyapp.core.factory.FactoryUyTourism
 * @see turismouyapp.core.interfaces.IUserController
 */
@WebServlet("/")
public class Login extends HttpServlet {
	
	/**
	 * Identificador de versión para serialización.
	 */
	private static final long serialVersionUID = 1L;
       
    /**
     * Constructor por defecto del servlet.
     * <p>
     * Invoca al constructor de la superclase {@link HttpServlet}.
     * La inicialización de componentes se realiza de forma lazy
     * a través de la factory cuando se procesa la primera petición.
     * </p>
     * 
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * Procesa peticiones HTTP GET para mostrar la página principal.
	 * <p>
	 * Este método genera una respuesta HTML que incluye:
	 * </p>
	 * <ol>
	 *   <li>Un encabezado mostrando el context path de la aplicación</li>
	 *   <li>Una lista de todos los usuarios turistas del sistema</li>
	 *   <li>Una lista de todos los proveedores de servicios</li>
	 * </ol>
	 * 
	 * <p>Los datos se obtienen mediante el patrón Factory para acceder
	 * al controlador de usuarios, asegurando la separación de capas
	 * entre la presentación y la lógica de negocio.</p>
	 * 
	 * <p><strong>Flujo de ejecución:</strong></p>
	 * <pre>
	 * 1. Obtener instancia del UserController vía Factory
	 * 2. Recuperar listas de turistas y proveedores
	 * 3. Generar HTML con los datos obtenidos
	 * 4. Enviar respuesta al cliente
	 * </pre>
	 * 
	 * @param request El objeto {@link HttpServletRequest} que contiene 
	 *                la petición del cliente
	 * @param response El objeto {@link HttpServletResponse} donde se 
	 *                 escribe la respuesta HTML
	 * 
	 * @throws ServletException Si ocurre un error específico del servlet
	 *                          durante el procesamiento de la petición
	 * @throws IOException Si ocurre un error de entrada/salida al 
	 *                     escribir la respuesta
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
	 * @see turismouyapp.core.interfaces.IUserController#listTourists()
	 * @see turismouyapp.core.interfaces.IUserController#listSuppliers()
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// Iniciar respuesta HTML
		response.getWriter().append("<!DOCTYPE html><html><body><h1>Served at: "
				+ request.getContextPath() + "</h1><main>");
		
		// Obtener datos desde el backend a través de la factory
		String[] users = FactoryUyTourism.getInstance().getIUserController().listTourists();
		String[] suppliers = FactoryUyTourism.getInstance().getIUserController().listSuppliers();
		
		// Renderizar lista de usuarios turistas
		if(users != null) {
			response.getWriter().append("<h2>Users</h2><ul>");
			for(String user : users) {
				response.getWriter().append("<li><p>" + user + "</p></li>");
			}
			response.getWriter().append("</ul>");
		}
		
		// Renderizar lista de proveedores
		if(suppliers != null) {
			response.getWriter().append("<h2>Suppliers</h2><ul>");
			for(String supplier : suppliers) {
				response.getWriter().append("<li><p>" + supplier + "</p></li>");
			}
			response.getWriter().append("</ul>");
		}
		
		// Cerrar documento HTML
		response.getWriter().append("</main></body></html>");
	}

	/**
	 * Procesa peticiones HTTP POST delegando al método {@link #doGet}.
	 * <p>
	 * Por defecto, este servlet trata las peticiones POST de la misma forma
	 * que las peticiones GET. Esta implementación es típica para servlets
	 * de solo lectura o para casos donde no se requiere distinción entre
	 * métodos HTTP.
	 * </p>
	 * 
	 * <p><strong>Nota:</strong> En una implementación futura orientada a
	 * producción, este método podría manejar formularios de login o
	 * autenticación de usuarios de forma específica.</p>
	 * 
	 * @param request El objeto {@link HttpServletRequest} que contiene 
	 *                la petición del cliente
	 * @param response El objeto {@link HttpServletResponse} donde se 
	 *                 escribe la respuesta
	 * 
	 * @throws ServletException Si ocurre un error específico del servlet
	 * @throws IOException Si ocurre un error de entrada/salida
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
	 * @see #doGet(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}

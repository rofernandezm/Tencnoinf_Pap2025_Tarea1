package turismouyapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import turismouyapp.core.interfaces.IUserController;
import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.dto.SessionState;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;
import turismouyapp.core.dto.DtSupplier;
import turismouyapp.core.dto.DtTourist;

import java.time.LocalDate;


/**
 * Servlet implementation class Login
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * <pre>
 * GET http://localhost:8080/turismouy.UI/
 * </pre>
 * 
 * @see turismouyapp.core.factory.FactoryUyTourism
 * @see turismouyapp.core.interfaces.IUserController
 */

@WebServlet("/login")
public class Login extends HttpServlet {
	
	/**
	 * Identificador de versión para serialización.
	 */
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }
    
    

	protected void handleLogin(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		HttpSession objSesion = request.getSession();        // Obtengo la sesion en objSesion
		
		String nicknameOrEmail = request.getParameter("nickname-or-email");
		String password = request.getParameter("password");  
		SessionState newSessionState=null;                   

		// buscar usuario
		FactoryUyTourism factory = FactoryUyTourism.getInstance();
		IUserController icon = factory.getIUserController();
	 	  	    
	    DtUser result=null;
		
			result = icon.consultUserData(nicknameOrEmail);
			  
			if (result == null) {
				result = icon.consultUserDataByEmail(nicknameOrEmail);
			}
			  
	  		if (result != null && password.equals(result.getPassword()))   {
	  			newSessionState = SessionState.LOGIN_SUCCESS;
	  			request.getSession().setAttribute("logged_user", result);
	  		} else {
	  			newSessionState = SessionState.LOGIN_UNSUCCESSFUL; 
	            request.setAttribute("loginError", "Usuario o contraseña incorrectos");
	  			// setea el usuario logueado
			}
		
		objSesion.setAttribute("estado_sesion",newSessionState);

		// redirige a la página principal para que luego rediriga a la página que corresponde
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/home");
		dispatcher.forward(request, response);
	}
	
	private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Obtener datos del registro
        String nickname = request.getParameter("new-nickname");
        String name = request.getParameter("new-name");
        String lastName = request.getParameter("new-lastname");
        String password = request.getParameter("password");
        String passwordConf = request.getParameter("passwordconf");
        String email = request.getParameter("new-email");
        String birthDateStr = request.getParameter("new-birthdate");
        LocalDate birthDate = null;
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            birthDate = LocalDate.parse(birthDateStr);
        }
        String userType = request.getParameter("user-type");

        // Campos opcionales según tipo de usuario
        String nationality = request.getParameter("nationality");
        String supplierDesc = request.getParameter("description");
        String webSite = request.getParameter("website");

        // Foto de perfil
//        Part profilePhotoPart = request.getPart("new-profilephoto");
//        byte[] profilePhoto = profilePhotoPart != null ? profilePhotoPart.getInputStream().readAllBytes() : null;

        // Validar contraseñas
        if (!password.equals(passwordConf)) {
            request.setAttribute("registerError", "Las contraseñas no coinciden");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        FactoryUyTourism factory = FactoryUyTourism.getInstance();
		IUserController icon = factory.getIUserController();
		
		if (checkForm(request, response)) {
			try {
		        DtUser newUser = null;
		
		        if ("Turista".equals(userType)) {
		        	newUser = new DtTourist(nickname, name, lastName, email, birthDate, password, nationality);
		            icon.dataEntry(newUser);
		            
		        } else if ("Proveedor".equals(userType)) {
		        	newUser = new DtSupplier(nickname, name, lastName, email, birthDate, password, supplierDesc, webSite);
		        	icon.dataEntry(newUser);
		        }
		
		        icon.confirmRegistration();
	            RequestDispatcher rd = null;
				request.setAttribute("mensaje", "Se ha ingresado correctamente el usuario " + nickname + " en el sistema.");
				rd.forward(request, response);
				
//		        newUser.setProfilePhoto(profilePhoto);
		
		        // / redirige a la página principal para que luego rediriga a la página que corresponde
		        response.sendRedirect("/home");
			} catch (RepeatedUserNicknameException e) {
				// Muestro error de registro
				RequestDispatcher rd = null;
				request.setAttribute("mensaje", "El usuario " + nickname + " ya existe.");
				rd.forward(request, response);
			} catch (RepeatedUserEmailException e) {
				// Muestro error de registro
				RequestDispatcher rd = null;
				request.setAttribute("mensaje", "El usuario con email: " + email + " ya existe.");
				rd.forward(request, response);
			}   
		}else {
			RequestDispatcher rd = null;
			request.setAttribute("mensaje", "Por favor, llene todos los campos.");
			rd.forward(request, response);
		}
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
//		// Iniciar respuesta HTML
//		response.getWriter().append("<!DOCTYPE html><html><head><title>turismouy.UI</title></head><body><h1>Served at: "
//				+ request.getContextPath() + "</h1><main>");
//		
//		// Obtener datos desde el backend a través de la factory
//		String[] users = FactoryUyTourism.getInstance().getIUserController().listTourists();
//		String[] suppliers = FactoryUyTourism.getInstance().getIUserController().listSuppliers();
//		
//		// Renderizar lista de usuarios turistas
//		if(users != null) {
//			response.getWriter().append("<h2>Users</h2><ul>");
//			for(String user : users) {
//				response.getWriter().append("<li><p>" + user + "</p></li>");
//			}
//			response.getWriter().append("</ul>");
//		}
//		
//		// Renderizar lista de proveedores
//		if(suppliers != null) {
//			response.getWriter().append("<h2>Suppliers</h2><ul>");
//			for(String supplier : suppliers) {
//				response.getWriter().append("<li><p>" + supplier + "</p></li>");
//			}
//			response.getWriter().append("</ul>");
//		}
//		
//		// Cerrar documento HTML
//		response.getWriter().append("</main></body></html>");
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
		
		String action = request.getParameter("action");

        if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("register".equals(action)) {
            handleRegister(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción desconocida");
        }
	}
	
	private boolean checkForm (HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
		
		String nickname = request.getParameter("new-nickname");
        String name = request.getParameter("new-name");
        String lastName = request.getParameter("new-lastname");
        String password = request.getParameter("password");
        String passwordConf = request.getParameter("passwordconf");
        String email = request.getParameter("new-email");
        String birthDateStr = request.getParameter("new-birthdate");
        LocalDate birthDate = null;
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            birthDate = LocalDate.parse(birthDateStr);
        }
        String userType = request.getParameter("user-type");

        // Campos opcionales según tipo de usuario
        String nationality = request.getParameter("nationality");
        String supplierDesc = request.getParameter("description");
        String webSite = request.getParameter("website");
        
		if(nickname == null || nickname.isBlank() || name == null || name.isBlank() || lastName == null || lastName.isBlank() ||
			password == null || password.isBlank() || passwordConf == null || passwordConf.isBlank() ||  email == null || email.isBlank() ||
			birthDateStr == null || birthDateStr.isBlank() || userType == null || userType.isBlank()) {
			return false;
		} else if ("Turista".equals(userType)) {
			if (nationality == null || nationality.isBlank()) {
				return false;
			}	
			return true;
		}else if ("Proveedor".equals(userType)){
			if (supplierDesc == null || supplierDesc.isBlank()) {
				return false;
			}	
			return true;
		}else {
			return false;
		}
	}

}

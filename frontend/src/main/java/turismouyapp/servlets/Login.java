package turismouyapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.Part;
import java.nio.file.Path;
import java.io.File;

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
import java.util.UUID;


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
@MultipartConfig
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
    
    protected void handleGuestLogin(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(true);
    	
    	session.setAttribute("guest_mode", true);
    	session.setAttribute("user_role", "GUEST");
    	
    	// Al ser invitado, no hay usuario logueado
		response.sendRedirect(request.getContextPath() + "/home");

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
	    if(nicknameOrEmail != null && !nicknameOrEmail.isBlank() && password != null && !password.isBlank()) {
			
	    	result = icon.consultUserData(nicknameOrEmail);
			  
			if (result == null) {
				result = icon.consultUserDataByEmail(nicknameOrEmail);
			}
			  
	  		if (result != null && password.equals(result.getPassword()))   {
	  			
	  	       // Invalidar sesión anterior si existe (seguridad)
	  			objSesion = request.getSession(false);
	  	        if (objSesion != null) {
	  	        	objSesion.invalidate();
	  	        }
	  	        
	  	        // Crear NUEVA sesión → genera nuevo JSESSIONID
	  	        objSesion = request.getSession(true);
	  	        objSesion.setAttribute("logged_user", result);
	  			
	  	        response.sendRedirect(request.getContextPath() + "/home");
	  			
//	  			newSessionState = SessionState.LOGIN_SUCCESS;
//	  			objSesion.invalidate();
//	  			objSesion.getSession(true);
//	  			request.getSession().setAttribute("logged_user", result);
//	  			
//	  	    	session.setAttribute("guest_mode", true);
//	  	    	session.setAttribute("user_role", "GUEST");
//	  	    	
//	  	    	// Al ser invitado, no hay usuario logueado
//	  			response.sendRedirect(request.getContextPath() + "/home");
//	  			RequestDispatcher dispatcher = request.getRequestDispatcher("/home"); 
//	  			dispatcher.forward(request, response);
	  		} else {
//	  			newSessionState = SessionState.LOGIN_UNSUCCESSFUL; 
	            request.setAttribute("loginError", "Usuario o contraseña incorrectos");
	            request.setAttribute("activeTab", "login");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp");
	            dispatcher.forward(request, response);
	            
	  			// setea el usuario logueado
			}
	    }else {
	    	request.setAttribute("mensaje", "Por favor, llene todos los campos.");
	    	request.setAttribute("activeTab", "login");
			RequestDispatcher rd = request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp");
			rd.forward(request, response);
	    }
//		objSesion.setAttribute("estado_sesion",newSessionState);

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
        Part profilePhotoPart = request.getPart("new-profilephoto");
    	String rawPath = getServletContext().getInitParameter("uploadFolder");

    	// Reemplaza la variable ${catalina.base} por su valor real
    	String catalinaBase = System.getProperty("catalina.base");
    	String uploadPath = rawPath.replace("${catalina.base}", catalinaBase);

    	File uploadDir = new File(uploadPath);
    	if (!uploadDir.exists()) uploadDir.mkdirs();
    	
        String imagePath = "default_profile.jpg";
        
        if (profilePhotoPart != null && profilePhotoPart.getSize() > 0) {
            
        	
        	// Obtiene el nombre original (ej: "foto.png")
        	String originalName = Path.of(profilePhotoPart.getSubmittedFileName()).getFileName().toString();

        	// Extrae la extensión (todo después del último '.')
        	String extension = "";
        	int i = originalName.lastIndexOf('.');
        	if (i > 0) {
        	    extension = originalName.substring(i); // incluye el punto, ej: ".png"
        	}

        	// Genera nombre único + extensión
        	String fileName = UUID.randomUUID().toString() + extension;

            // Guardar el archivo en el servidor
            profilePhotoPart.write(uploadPath + File.separator + fileName);

            // Guardar la ruta relativa
            imagePath = fileName;
        }
        
        System.out.println(System.getProperty("catalina.base"));
        System.out.println("getServletContext:" + getServletContext().getRealPath("") + File.separator + "profile_img");
        System.out.println("Ruta de imagen generada: " + imagePath);

        // Validar contraseñas
        if (!password.equals(passwordConf)) {
            request.setAttribute("registerError", "Las contraseñas no coinciden");
            request.setAttribute("activeTab", "register");
            request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp").forward(request, response);
            return;
        }

        FactoryUyTourism factory = FactoryUyTourism.getInstance();
		IUserController icon = factory.getIUserController();
		
		if (checkForm(request, response)) {
			try {
		        DtUser newUser = null;
		
		        if ("Turista".equals(userType)) {
		        	newUser = new DtTourist(nickname, name, lastName, email, birthDate, password, nationality,imagePath);
		            
		        } else if ("Proveedor".equals(userType)) {
		        	newUser = new DtSupplier(nickname, name, lastName, email, birthDate, password, supplierDesc, webSite,imagePath);
		        }
		
		        icon.dataEntry(newUser);
		        icon.confirmRegistration();
		        
				request.setAttribute("mensaje", "Se ha ingresado correctamente el usuario " + nickname + " en el sistema.");
				RequestDispatcher rd = request.getRequestDispatcher("/AccedeAlHome.jsp"); //poner el home que corresponda luego de que el ususario ingreso
				rd.forward(request, response);
		        
			} catch (RepeatedUserNicknameException e) {
				// Muestro error de registro
				request.setAttribute("mensaje", "El usuario " + nickname + " ya existe.");
				request.setAttribute("activeTab", "register");
				RequestDispatcher rd = request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp");
				rd.forward(request, response);
			} catch (RepeatedUserEmailException e) {
				// Muestro error de registro
				request.setAttribute("mensaje", "El usuario con email: " + email + " ya existe.");
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		
		String action = request.getParameter("action");

		if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("register".equals(action)) {
            handleRegister(request, response);
        } else if ("guest".equals(action)) {
        	handleGuestLogin(request, response);
        } else {
        	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción desconocida");
            return;
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		System.out.println(">>> Acción recibida: " + action);

        if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("register".equals(action)) {
            handleRegister(request, response);
        } else if ("guest".equals(action)) {
        	handleGuestLogin(request, response);
        } else {
        	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción desconocida");
            return;
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

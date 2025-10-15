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
import turismouyapp.core.dto.UserType;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;
import turismouyapp.core.dto.DtSupplier;
import turismouyapp.core.dto.DtTourist;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

/**
 * Servlet implementation class Login
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 *        <pre>
 * GET http://localhost:8080/turismouy.UI/
 *        </pre>
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

	private final IUserController iUserController;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		FactoryUyTourism factory = FactoryUyTourism.getInstance();
		this.iUserController = factory.getIUserController();
	}

	protected void handleGuestLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute("guest_mode", true);
		session.setAttribute("user_role", UserType.GUEST);

		// Al ser invitado, no hay usuario logueado
		response.sendRedirect(request.getContextPath() + "/home");
	}

	protected void handleLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nicknameOrEmail = request.getParameter("nickname-or-email");
		String password = request.getParameter("password");
		DtUser result = iUserController.consultUserData(nicknameOrEmail);
		if (result == null) {
			result = iUserController.consultUserDataByEmail(nicknameOrEmail);
		}

		if (result != null && password.equals(result.getPassword())) {
			createNewSessionAndAssingUser(request, response, result);
		} else {
			request.setAttribute("loginError", "Usuario o contraseña incorrectos");
			request.setAttribute("activeTab", "login");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/iniciarSesionRegistrarse.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void createNewSessionAndAssingUser(HttpServletRequest request, HttpServletResponse response, DtUser user)
			throws ServletException, IOException {

		String msg = (String) request.getAttribute("mensaje");

		// Invalidar sesión anterior si existe (seguridad)
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// Crear NUEVA sesión → genera nuevo JSESSIONID
		session = request.getSession(true);
		session.setAttribute("logged_user", user);
		session.setAttribute("user_role", user.getUserType());
		if (msg != null) {
			request.setAttribute("mensaje", msg);
		}
		response.sendRedirect(request.getContextPath() + "/home");
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
		try {
			birthDate = LocalDate.parse(birthDateStr);
		} catch (DateTimeParseException ex) {
			request.setAttribute("registerError", "Fecha de nacimiento inválida, reintente.");
			request.setAttribute("activeTab", "register");
			request.getRequestDispatcher("/iniciarSesionRegistrarse.jsp").forward(request, response);
			return;
		}

		// Foto de perfil
		Part profilePhotoPart = request.getPart("new-profilephoto");
		String rawPath = getServletContext().getInitParameter("uploadFolder");

		// Reemplaza la variable ${catalina.base} por su valor real
		String catalinaBase = System.getProperty("catalina.base");
		String uploadPath = rawPath.replace("${catalina.base}", catalinaBase);

		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdirs();

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

		// Validar contraseñas ANTES de procesar
		if (!password.equals(passwordConf)) {
			request.setAttribute("registerError", "Las contraseñas no coinciden");
			request.setAttribute("activeTab", "register");
			request.getRequestDispatcher("/IniciarSesionRegistrarse.jsp").forward(request, response);
			return;
		}

		UserType user = UserType.TOURIST.toString().equals(request.getParameter("user-type")) ? UserType.TOURIST
				: UserType.SUPPLIER;

		try {
			DtUser newUser = null;

			if (user == UserType.TOURIST) {
				String nationality = request.getParameter("nationality");
				newUser = new DtTourist(nickname, name, lastName, email, birthDate, password, nationality, imagePath);
			} else {
				String supplierDesc = request.getParameter("description");
				String webSite = request.getParameter("website");
				newUser = new DtSupplier(nickname, name, lastName, email, birthDate, password, supplierDesc, webSite,
						imagePath);
			}

			iUserController.dataEntry(newUser);
			iUserController.confirmRegistration();
	        
			request.setAttribute("mensaje", "Se ha ingresado correctamente el usuario " + nickname + " en el sistema.");
			
			createNewSessionAndAssingUser(request, response, newUser);

		} catch (RepeatedUserNicknameException e) {
			// Muestro error de registro
			request.setAttribute("registerError", "El usuario " + nickname + " ya existe.");
			request.setAttribute("activeTab", "register");
			RequestDispatcher rd = request.getRequestDispatcher("/iniciarSesionRegistrarse.jsp");
			rd.forward(request, response);
		} catch (RepeatedUserEmailException e) {
			// Muestro error de registro
			request.setAttribute("registerError", "El usuario con email: " + email + " ya existe.");
			request.setAttribute("activeTab", "register");
			RequestDispatcher rd = request.getRequestDispatcher("/iniciarSesionRegistrarse.jsp");
			rd.forward(request, response);
		}

		System.out.println(System.getProperty("catalina.base"));
		System.out.println("getServletContext:" + getServletContext().getRealPath("") + File.separator + "profile_img");
		System.out.println("Ruta de imagen generada: " + imagePath);
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

	private boolean checkForm(HttpServletRequest request, HttpServletResponse response)
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

		if (nickname == null || nickname.isBlank() || name == null || name.isBlank() || lastName == null
				|| lastName.isBlank() || password == null || password.isBlank() || passwordConf == null
				|| passwordConf.isBlank() || email == null || email.isBlank() || birthDateStr == null
				|| birthDateStr.isBlank() || userType == null || userType.isBlank()) {
			return false;
		} else if ("Turista".equals(userType)) {
			if (nationality == null || nationality.isBlank()) {
				return false;
			}
			return true;
		} else if ("Proveedor".equals(userType)) {
			if (supplierDesc == null || supplierDesc.isBlank()) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

}

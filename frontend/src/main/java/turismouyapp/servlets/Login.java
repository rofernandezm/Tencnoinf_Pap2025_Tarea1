package turismouyapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.Part;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import turismouyapp.core.interfaces.IUserController;
import turismouyapp.security.PasswordEncoder;
import turismouyapp.utils.ImageManager;
import turismouyapp.utils.ImageManager.UploadFolderType;
import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.dto.UserType;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;
import turismouyapp.core.dto.DtSupplier;
import turismouyapp.core.dto.DtTourist;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
	private static final int SESSION_TIMEOUT_SECONDS = 1800; // 30 minutos

	


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		FactoryUyTourism factory = FactoryUyTourism.getInstance();
		this.iUserController = factory.getIUserController();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action == null || action.isEmpty()) {
			// Mostrar formulario de login/registro
			request.getRequestDispatcher("/WEB-INF/vistas/iniciarSesionRegistrarse.jsp").forward(request, response);
		} else {
			switch (action) {
			case "login":
				this.handleLogin(request, response);
				break;
			case "register":
				this.handleRegister(request, response);
				break;
			case "guest":
				this.handleGuestLogin(request, response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción desconocida");
				break;
			}
		}
	}

	protected void handleGuestLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute("guest_mode", true);
		session.setAttribute("user_role", UserType.GUEST);
		session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);

		// Al ser invitado, no hay usuario logueado

		String next = sanitizeNext(request.getParameter("next"), request);
	    response.sendRedirect(next != null ? next : request.getContextPath() + "/home");
	    
	}

	protected void handleLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nicknameOrEmail = request.getParameter("nickname-or-email");
		DtUser requestedUser = this.findUserByNicknameOrEmail(nicknameOrEmail);
		boolean isValidAccess = false;

		if (requestedUser != null) {
			String password = request.getParameter("password");
//			String newHash = PasswordEncoder.encode(request.getParameter("password"));
			isValidAccess = PasswordEncoder.matches(password, requestedUser.getPassword());
		}

		if (isValidAccess) {
			try {
				this.createNewSessionAndAssingUser(request, response, requestedUser);
				return;
			} catch (Exception ex) {
				ex.printStackTrace();
				isValidAccess = false;
			}
		}

		if (requestedUser == null || !isValidAccess) {
			request.setAttribute("activeTab", "login");
			this.setErrorAndDispatchForward(request, response, "loginError", "Usuario o contraseña incorrectos");
		}
	}

	private DtUser findUserByNicknameOrEmail(String nicknameOrEmail) {
		DtUser requestedUser = iUserController.consultUserData(nicknameOrEmail);
		return requestedUser == null ? iUserController.consultUserDataByEmail(nicknameOrEmail) : requestedUser;
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
		session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);

		if (msg != null) {
			request.setAttribute("mensaje", msg);
		}
		String next = sanitizeNext(request.getParameter("next"), request);
	    response.sendRedirect(next != null ? next : request.getContextPath() + "/home");
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

		// Fecha de nacimiento
		try {
			birthDate = LocalDate.parse(birthDateStr);
		} catch (DateTimeParseException ex) {
			ex.printStackTrace();
			request.setAttribute("activeTab", "register");
			this.setErrorAndDispatchForward(request, response, "registerError", "Fecha de nacimiento inválida, reintente.");
			return;
		}

		// Validacion de contraseñas
		if (!password.equals(passwordConf)) {
			request.setAttribute("activeTab", "register");
			this.setErrorAndDispatchForward(request, response, "registerError", "Las contraseñas no coinciden");
			return;
		}

		// Usuario a registrar
		UserType user = UserType.TOURIST.toString().equals(request.getParameter("user-type")) ? UserType.TOURIST
				: UserType.SUPPLIER;

		// Foto de perfil
		Part profilePhotoPart = request.getPart("new-profilephoto");
		String fileName = (profilePhotoPart != null && profilePhotoPart.getSize() > 0)
				? ImageManager.generateFileName(profilePhotoPart)
				: ImageManager.resolveDefaultImageName(UploadFolderType.PROFILE);

		// Registro de usuario
		DtUser newUser = null;
		String hashedPassword = PasswordEncoder.encode(password);
		if (user == UserType.TOURIST) {
			String nationality = request.getParameter("nationality");
			newUser = new DtTourist(nickname, name, lastName, email, birthDate, hashedPassword, nationality,
					fileName);
		} else {
			String supplierDesc = request.getParameter("description");
			String webSite = request.getParameter("website");
			newUser = new DtSupplier(nickname, name, lastName, email, birthDate, hashedPassword, supplierDesc,
					webSite, fileName);
		}
		
		try {
			iUserController.dataEntry(newUser);
			iUserController.confirmRegistration();

			// Persistir imagen
			try {
				ImageManager.persistFile(this.getServletContext(), profilePhotoPart, fileName,
						UploadFolderType.PROFILE);
			} catch (IOException ex) {

				// Setea imagen default
				String defaultImage = ImageManager.resolveDefaultImageName(UploadFolderType.PROFILE);
				if (newUser.getUserType() == UserType.TOURIST) {
					newUser = new DtTourist(newUser.getNickname(), newUser.getName(), newUser.getLastName(),
							newUser.getEmail(), newUser.getBirthDate(), newUser.getPassword(),
							((DtTourist) newUser).getNationality(), defaultImage);
				} else {
					new DtSupplier(newUser.getNickname(), newUser.getName(), newUser.getLastName(), newUser.getEmail(),
							newUser.getBirthDate(), newUser.getPassword(), ((DtSupplier) newUser).getDescription(),
							((DtSupplier) newUser).getWebSite(), defaultImage);
				}
				iUserController.modifyUserData(newUser);
			}

			request.setAttribute("mensaje", "Se ha ingresado correctamente el usuario " + nickname + " en el sistema.");
			this.createNewSessionAndAssingUser(request, response, newUser);

		} catch (RepeatedUserNicknameException e) {

			// Muestro error de registro
			request.setAttribute("activeTab", "register");
			this.setErrorAndDispatchForward(request, response, "registerError", "El usuario " + nickname + " ya existe.");

		} catch (RepeatedUserEmailException e) {

			// Muestro error de registro
			request.setAttribute("activeTab", "register");
			this.setErrorAndDispatchForward(request, response, "registerError", "El usuario con email: " + email + " ya existe.");
		}
	}

	// Sanitiza 'next' para evitar open redirect
		private String sanitizeNext(String next, HttpServletRequest req) {
		    if (next == null || next.isBlank()) return null;

		    // Permitir rutas relativas internas
		    if (next.startsWith("/")) {
		        if (next.startsWith("//") || next.contains("\r") || next.contains("\n")) return null;
		        return next;
		    }

		    // Permitir URL absoluta solo si es mismo host/puerto/esquema
		    try {
		        java.net.URI n = java.net.URI.create(next);
		        String scheme = req.getScheme(); // http/https
		        String host = req.getServerName();
		        int port = req.getServerPort();
		        int defaultPort = scheme.equalsIgnoreCase("https") ? 443 : 80;

		        boolean sameScheme = scheme.equalsIgnoreCase(n.getScheme());
		        boolean sameHost = host.equalsIgnoreCase(n.getHost());
		        boolean samePort = (n.getPort() == -1 ? port == defaultPort : n.getPort() == port);

		        if (sameScheme && sameHost && samePort) {
		            return n.getRawPath() + (n.getRawQuery() != null ? "?" + n.getRawQuery() : "");
		        }
		    } catch (IllegalArgumentException ignored) {}
		    return null;
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	private void setErrorAndDispatchForward(HttpServletRequest request, HttpServletResponse response, String attributeName, String errorMsg) {
		try {
			request.setAttribute(attributeName, errorMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vistas/iniciarSesionRegistrarse.jsp");
			dispatcher.forward(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

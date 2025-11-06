package turismouyapp.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import turismouyapp.security.PasswordEncoder;
import turismouyapp.utils.ImageManager;
import turismouyapp.utils.ImageManager.UploadFolderType;
import turismouyapp.webservices.DtSupplier;
import turismouyapp.webservices.DtTourist;
import turismouyapp.webservices.DtUser;
import turismouyapp.webservices.UserPortType;
import turismouyapp.webservices.UserService;
import turismouyapp.webservices.UserType;

/**
 * Servlet implementation class ModifyDataUser
 *
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 *
 *        <pre>
 * GET http://localhost:8080/turismouy.UI/modify-data-user
 *        </pre>
 *
 * @see turismouyapp.core.factory.FactoryUyTourism
 * @see turismouyapp.core.interfaces.IUserController
 */

@WebServlet("/modify-data-user")
@MultipartConfig
public class ModifyDataUser extends HttpServlet {

	/**
	 * Identificador de versión para serialización.
	 */
	private static final long serialVersionUID = 1L;

	private final UserPortType userWebService;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyDataUser() {
		super();
		this.userWebService = new UserService().getUserPort();
	}

	protected void handleModifyData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO: CONTROL DE PERMISOS PARA USUARIO LOGGED_USER

		HttpSession session = request.getSession();
		DtUser loggedUser = (DtUser) session.getAttribute("logged_user");
		UserType userRole = (UserType) session.getAttribute("user_role");

		String name = this.isNullOrEmptyParameter(request, "name-user") ? loggedUser.getName()
				: request.getParameter("name-user");

		String lastname = this.isNullOrEmptyParameter(request, "lastname-user") ? loggedUser.getLastName()
				: request.getParameter("lastname-user");

		String password = this.isNullOrEmptyParameter(request, "password-user") ? loggedUser.getPassword()
				: request.getParameter("password-user");

		LocalDate birthDate = LocalDate.now();//loggedUser.getBirthDate();

		// Si passsword no es encriptada, es nueva
		if (!PasswordEncoder.isBCryptHash(password)) {
			String passwordConf = request.getParameter("passwordconf-user");
			if (!password.equals(passwordConf)) {
				this.setErrorAndDispatchForward(request, response, "Las contraseñas no coinciden");
				return;
			}
		}

		// Valida fecha nueva fecha de nacimiento
		if (!this.isNullOrEmptyParameter(request, "birthdate-user")) {

			try {

				String birthDateStr = request.getParameter("birthdate-user");
				birthDate = LocalDate.parse(birthDateStr);

			} catch (DateTimeParseException ex) {

				ex.printStackTrace();
				this.setErrorAndDispatchForward(request, response, "Fecha de nacimiento inválida, por favor verique.");
				return;
			}
		}

		// Foto de perfil
		Part profilePhotoPart = request.getPart("new-profilephoto");
		String fileName = (profilePhotoPart != null && profilePhotoPart.getSize() > 0)
				? ImageManager.generateFileName(profilePhotoPart)
				: loggedUser.getImagePath();

		// Actualizacion de perfil actual

		DtUser updatedUser = null;
		String hashedPassword = !PasswordEncoder.isBCryptHash(password)
				|| !PasswordEncoder.matches(password, loggedUser.getPassword()) ? PasswordEncoder.encode(password)
						: password;

		switch (userRole) {
		case TOURIST:
			String nationality = this.isNullOrEmptyParameter(request, "nationality-user")
					? ((DtTourist) loggedUser).getNationality()
					: request.getParameter("nationality-user");

			//updatedUser = new DtTourist(loggedUser.getNickname(), name, lastname, loggedUser.getEmail(), birthDate,
			//		hashedPassword, nationality, fileName);
			break;

		case SUPPLIER:

			String description = this.isNullOrEmptyParameter(request, "description-user")
					? ((DtSupplier) loggedUser).getDescription()
					: request.getParameter("description-user");

			String website = this.isNullOrEmptyParameter(request, "website-user")
					? ((DtSupplier) loggedUser).getWebSite()
					: request.getParameter("website-user");

			//updatedUser = new DtSupplier(loggedUser.getNickname(), name, lastname, loggedUser.getEmail(), birthDate,
			//		hashedPassword, description, website, fileName);
			break;

		default:
			this.setErrorAndDispatchForward(request, response, "Rol indefinido para esta funcionalidad: " + userRole);
			return;
		}

		try {

			userWebService.modifyUserData(updatedUser);

			// PERSISTIR IMAGEN
			if (!fileName.equals(loggedUser.getImagePath())) {
				try {
					ImageManager.persistFile(this.getServletContext(), profilePhotoPart, fileName,
							UploadFolderType.PROFILE);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			// Actualizar sesión con los nuevos datos
			session.setAttribute("logged_user", updatedUser);
			response.sendRedirect(request.getContextPath() + "/home");

		} catch (Exception e) {
			this.setErrorAndDispatchForward(request, response, e.getMessage());
		}
	}

	private boolean isNullOrEmptyParameter(HttpServletRequest request, String parameterName) {
		return request.getParameter(parameterName) == null
				|| ((String) request.getParameter(parameterName)).trim().isEmpty();
	}

	private void setErrorAndDispatchForward(HttpServletRequest request, HttpServletResponse response, String errorMsg) {
		try {
			request.setAttribute("error", errorMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vistas/modificarDatosUsuario.jsp");
			dispatcher.forward(request, response);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Mostrar formulario de modificación
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vistas/modificarDatosUsuario.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.handleModifyData(request, response);
	}

}

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
import turismouyapp.core.dto.DtSupplier;
import turismouyapp.core.dto.DtTourist;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Servlet implementation class ModifyDataUser
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * <pre>
 * GET http://localhost:8080/turismouy.UI/modify-data-user
 * </pre>
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
	
	private final IUserController iUserController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyDataUser() {
        super();
        FactoryUyTourism factory = FactoryUyTourism.getInstance();
        this.iUserController = factory.getIUserController();
    }
    
    protected void handleModifyData(HttpServletRequest request, HttpServletResponse response)	
    		throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();
    	DtUser loggedUser = (DtUser) session.getAttribute("logged_user");
    	UserType userRole = (UserType) session.getAttribute("user_role");
    	
        String name = request.getParameter("name-user");
        String lastname = request.getParameter("lastname-user");
        String password = request.getParameter("password-user");
        String passwordConf = request.getParameter("passwordconf-user");
        String birthdateStr = request.getParameter("birthdate-user");

        // Usar valores actuales si no se proporcionaron nuevos
        if (name == null || name.trim().isEmpty()) {
            name = loggedUser.getName();
        }
        if (lastname == null || lastname.trim().isEmpty()) {
            lastname = loggedUser.getLastName();
        }

        // Validar contraseñas solo si se intenta cambiar
        if (password != null && !password.trim().isEmpty()) {
            if (!password.equals(passwordConf)) {
                request.setAttribute("error", "Las contraseñas no coinciden");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vistas/modificarDatosUsuario.jsp");
                dispatcher.forward(request, response);
                return;
            }
        } else {
            // Si no se proporciona contraseña, usar la actual
            password = loggedUser.getPassword();
        }

        try {
            LocalDate birthdate = null;
            if (birthdateStr != null && !birthdateStr.isEmpty()) {
                birthdate = LocalDate.parse(birthdateStr);
            } else {
                birthdate = loggedUser.getBirthDate();
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

            String imagePath = loggedUser.getImagePath();

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

            DtUser updatedUser = null;
            
            if (userRole == UserType.TOURIST) {
                String nationality = request.getParameter("nationality-user");
                if (nationality == null || nationality.trim().isEmpty()) {
                    nationality = ((DtTourist) loggedUser).getNationality();
                }
                updatedUser = new DtTourist(
                    loggedUser.getNickname(), 
                    name, 
                    lastname, 
                    loggedUser.getEmail(), 
                    birthdate, 
                    password, 
                    nationality, 
                    imagePath
                );
            } else if (userRole == UserType.SUPPLIER) {
                String description = request.getParameter("description-user");
                String website = request.getParameter("website-user");
                if (description == null || description.trim().isEmpty()) {
                    description = ((DtSupplier) loggedUser).getDescription();
                }
                if (website == null || website.trim().isEmpty()) {
                    website = ((DtSupplier) loggedUser).getWebSite();
                }
                updatedUser = new DtSupplier(
                    loggedUser.getNickname(), 
                    name, 
                    lastname, 
                    loggedUser.getEmail(), 
                    birthdate, 
                    password, 
                    description, 
                    website, 
                    imagePath
                );
            }
            
            iUserController.modifyUserDate(updatedUser);
            
            // Actualizar sesión con los nuevos datos
            session.setAttribute("logged_user", updatedUser);
            
            response.sendRedirect(request.getContextPath() + "/home");
            
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vistas/modificarDatosUsuario.jsp");
            dispatcher.forward(request, response);
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
    	handleModifyData(request, response);
    }
    

}
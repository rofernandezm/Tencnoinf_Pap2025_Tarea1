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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyDataUser() {
        super();
    }
    
    protected void handleModifyData(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
    
    	HttpSession objSesion = request.getSession();
    	
    	DtUser loggedUser = (DtUser)objSesion.getAttribute("logged_user");
    	
    	String userType = (String) objSesion.getAttribute("userType");
    	String nickname = loggedUser.getNickname(); //no modificable
        String name = request.getParameter("name-user");
        String lastname = request.getParameter("lastname-user");
        String email = loggedUser.getEmail(); //no modificable
        String password = request.getParameter("password-user");
        String passwordConf = request.getParameter("passwordconf-user");
        String birthdateStr = request.getParameter("birthdate-user");

        LocalDate birthdate = null;
        if (birthdateStr != null && !birthdateStr.isEmpty()) {
            birthdate = LocalDate.parse(birthdateStr);
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
        // Validar contraseñas
        if (!password.equals(passwordConf)) {
            request.setAttribute("registerError", "Las contraseñas no coinciden");
            request.getRequestDispatcher("/ModifyDataUser.jsp").forward(request, response);
            return;
        }
        
        FactoryUyTourism factory = FactoryUyTourism.getInstance();
		IUserController icon = factory.getIUserController();
		
		if (checkForm(request, response)) {
			try {
		        if ("tourist".equalsIgnoreCase(userType)) {
		            String nationality = request.getParameter("nationality-user");
	
		            DtUser dtTourist = new DtTourist(nickname, name, lastname, email, birthdate, password, nationality, imagePath);
	
		            icon.modifyUserDate(dtTourist);
	
		        } else if ("supplier".equalsIgnoreCase(userType)) {
		            String description = request.getParameter("description-user");
		            String website = request.getParameter("website-user");
	
		            DtUser dtSupplier = new DtSupplier( nickname, name, lastname, email, birthdate, password, description, website, imagePath );
	
		            icon.modifyUserDate(dtSupplier);
		        }
	
		        // Actualizar sesión para mostrar cambios inmediatamente
		        objSesion.setAttribute("name", name);
		        objSesion.setAttribute("lastname", lastname);
		        if ("tourist".equalsIgnoreCase(userType)) {
		        	objSesion.setAttribute("nacionality", request.getParameter("nationality-user"));
		        } else if ("supplier".equalsIgnoreCase(userType)) {
		        	objSesion.setAttribute("description", request.getParameter("description"));
		        	objSesion.setAttribute("website", request.getParameter("website"));
		        }
	
		        request.setAttribute("message", "Datos actualizados correctamente");
		        RequestDispatcher rd = request.getRequestDispatcher("/modify-data-user.jsp");
		        rd.forward(request, response);
	
		    } catch (Exception e) {
		        request.setAttribute("error", e.getMessage());
		        RequestDispatcher rd = request.getRequestDispatcher("/modify-data-user.jsp");
		        rd.forward(request, response);
		    }
		}else {
			request.setAttribute("mensaje", "Por favor, no deje campos vacíos.");
			RequestDispatcher rd = request.getRequestDispatcher("/modify-data-user.jsp");
			rd.forward(request, response);
		}
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	handleModifyData(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
    	handleModifyData(request, response);
    }
    
    private boolean checkForm (HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
		
    	HttpSession objSesion = request.getSession();
    	String userType = (String) objSesion.getAttribute("userType");
    	
        String name = request.getParameter("name-user");
        String lastName = request.getParameter("lastname-user");
        String password = request.getParameter("password-user");
        String passwordConf = request.getParameter("passwordconf-user");
        String birthDateStr = request.getParameter("birthdate-user");
        LocalDate birthDate = null;
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            birthDate = LocalDate.parse(birthDateStr);
        }

        // Campos opcionales según tipo de usuario
        String nationality = request.getParameter("nationality");
        String supplierDesc = request.getParameter("description");
        
		if(name == null || name.isBlank() || lastName == null || lastName.isBlank() ||
			password == null || password.isBlank() || passwordConf == null || passwordConf.isBlank() ||
			birthDateStr == null || birthDateStr.isBlank()) {
			return false;
		} else if ("tourist".equalsIgnoreCase(userType)) {
			if (nationality == null || nationality.isBlank()) {
				return false;
			}	
			return true;
		}else if ("tourist".equalsIgnoreCase(userType)){
			if (supplierDesc == null || supplierDesc.isBlank()) {
				return false;
			}	
			return true;
		}else {
			return false;
		}
	}
}
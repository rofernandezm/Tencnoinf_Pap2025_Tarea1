package turismouyapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import turismouyapp.core.interfaces.IUserController;
import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.dto.DtUser;

/**
 * Servlet implementation class ConsultUser
 * 
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 * 
 * <pre>
 * GET http://localhost:8080/turismouy.UI/consult-user
 * GET http://localhost:8080/turismouy.UI/consult-user?nickname=usuario1
 * </pre>
 * 
 * @see turismouyapp.core.factory.FactoryUyTourism
 * @see turismouyapp.core.interfaces.IUserController
 */

@WebServlet("/consult-user")
public class ConsultUser extends HttpServlet {
	
	/**
	 * Identificador de versión para serialización.
	 */
	private static final long serialVersionUID = 1L;
	
	private final IUserController iUserController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultUser() {
        super();
        FactoryUyTourism factory = FactoryUyTourism.getInstance();
        this.iUserController = factory.getIUserController();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// Cargar lista de usuarios para el combobox
		String[] usuarios = iUserController.listUsers();
		request.setAttribute("usuarios", usuarios);
		
		// Si se proporciona un nickname, buscar sus datos
		String nickname = request.getParameter("nickname");
		if (nickname != null && !nickname.trim().isEmpty()) {
			DtUser usuario = iUserController.consultUserData(nickname);
			if (usuario != null) {
				request.setAttribute("usuario", usuario);
			}
		}
		
		// Forward a la vista
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vistas/ConsultUser.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
}

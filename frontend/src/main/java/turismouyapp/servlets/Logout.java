package turismouyapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import turismouyapp.webservices.UserType;

import java.io.IOException;

/**
 * Servlet implementation class Logout
 *
 * @author Equipo TurismoUY
 * @version 1.0.0
 * @since 2025
 *
 * <pre>
 * GET http://localhost:8080/turismouy.UI/logout
 * </pre>
 */

@WebServlet("/logout")
public class Logout extends HttpServlet {

    /**
     * Identificador de versión para serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la sesión, si existe
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Invalida la sesión
    		session.setAttribute("guest_mode", true);
    		session.setAttribute("user_role", UserType.GUEST);
    		session.setAttribute("logged_user", null);
            session.invalidate();
            
        }

        // Redirige al login
        response.sendRedirect(request.getContextPath() + "/login");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

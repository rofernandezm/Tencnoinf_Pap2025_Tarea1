package turismouyapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import turismouyapp.core.factory.FactoryUyTourism;

/**
 * Servlet implementation class Login
 */
@WebServlet("/")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("<!DOCTYPE html><html><body><h1>Served at: "+request.getContextPath()+"</h1><main>");
		String[] users = FactoryUyTourism.getInstance().getIUserController().listTourists();
		String[] suppliers = FactoryUyTourism.getInstance().getIUserController().listSuppliers();
		if(users!= null) {
			response.getWriter().append("<h2>Users</h2><ul>");
			for(String user : users) {
				response.getWriter().append("<li><p>"+user+"</p></li>");
			}
			response.getWriter().append("</ul>");
		}
		if(suppliers!= null) {
			response.getWriter().append("<h2>Suppliers</h2><ul>");
			for(String supplier : suppliers) {
				response.getWriter().append("<li><p>"+supplier+"</p></li>");
			}
			response.getWriter().append("</ul>");
		}
		response.getWriter().append("</main></body></html>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

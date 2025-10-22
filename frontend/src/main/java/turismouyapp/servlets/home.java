package turismouyapp.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "home", urlPatterns = {"/home"})
public class home extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public home() {
        super();

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
              throws ServletException, IOException {

            req.getRequestDispatcher("/WEB-INF/vistas/home.jsp").forward(req, resp);
          }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}

package turismouyapp.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import turismouyapp.core.factory.FactoryUyTourism;
import turismouyapp.core.interfaces.ITouristActivityController;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.TouristActivityStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "home", urlPatterns = {"/home"})
public class Home extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ITouristActivityController iActivityController;

    public Home() {
        super();
        FactoryUyTourism factory = FactoryUyTourism.getInstance();
        this.iActivityController = factory.getITouristActivityController();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
              throws ServletException, IOException {

            // Obtener actividades confirmadas para el carousel
            String[] activityNames = iActivityController.listTouristActivitiesByStatus(TouristActivityStatus.CONFIRMED);
            
            List<DtTouristActivity> confirmedActivities = new ArrayList<>();
            if (activityNames != null) {
                for (String actName : activityNames) {
                    try {
                        DtActivityWithOutings activityWithOutings = iActivityController.consultTouristActivityData(actName);
                        if (activityWithOutings != null && activityWithOutings.getActivity() != null) {
                            confirmedActivities.add(activityWithOutings.getActivity());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
            req.setAttribute("confirmedActivities", confirmedActivities);
            req.getRequestDispatcher("/WEB-INF/vistas/home.jsp").forward(req, resp);
          }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}

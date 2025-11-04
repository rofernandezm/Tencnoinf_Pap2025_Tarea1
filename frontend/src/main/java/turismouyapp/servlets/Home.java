package turismouyapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import turismouyapp.webservices.ActivityService;
import turismouyapp.webservices.ActivityPortType;
import turismouyapp.webservices.DtTouristActivity;
import turismouyapp.webservices.DtActivityWithOutings;
import turismouyapp.webservices.TouristActivityStatus;
import turismouyapp.webservices.ActivityDoesNotExistException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "home", urlPatterns = { "/home" })
public class Home extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final ActivityPortType activityWebService;

	public Home() {
		super();
		this.activityWebService = new ActivityService().getActivityPort();
	}
A
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Obtener actividades confirmadas para el carousel
		List<String> activityNames = activityWebService.listTouristActivitiesByStatus(TouristActivityStatus.CONFIRMED);

		List<DtTouristActivity> confirmedActivities = new ArrayList<>();
		if (activityNames != null) {
			for (String actName : activityNames) {
				try {
					DtActivityWithOutings activityWithOutings = activityWebService.consultTouristActivityData(actName);
					if (activityWithOutings != null && activityWithOutings.getActivity() != null) {
						confirmedActivities.add(activityWithOutings.getActivity());
					}
				} catch (ActivityDoesNotExistException e) {
					e.printStackTrace();
				}
			}
		}

		req.setAttribute("confirmedActivities", confirmedActivities);
		req.getRequestDispatcher("/WEB-INF/vistas/home.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

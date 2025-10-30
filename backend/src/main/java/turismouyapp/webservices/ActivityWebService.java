package turismouyapp.webservices;

import turismouyapp.core.controller.TouristActivityController;
import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.TouristActivityStatus;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.exceptions.RepeatedActivityNameException;
import turismouyapp.core.interfaces.ITouristActivityController;

import java.util.ArrayList;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

@SuppressWarnings("unchecked")
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class ActivityWebService {

	private Endpoint endpoint = null;
	private final ITouristActivityController iTouristActivityController;

	public ActivityWebService() {
		this.iTouristActivityController = new TouristActivityController();
	}

	// Operaciones disponibles en el webservice
	@WebMethod(exclude = true)
	public void publicar() {
		endpoint = Endpoint.publish("http://localhost:8007/ws/activity", this);
		System.out.println("[ActivityWebService] "+ endpoint.toString());
	}

	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
		return endpoint;
	}

	// Metodos de tourist activity
	@WebMethod
	public ArrayList<DtActivityWithOutings> listTouristActivityData() throws ActivityDoesNotExistException {
		return (ArrayList) iTouristActivityController.listTouristActivityData();
	}

	@WebMethod
	public String[] listTouristActivities() throws ActivityDoesNotExistException {
		return iTouristActivityController.listTouristActivities();
	}

	@WebMethod
	public void activityDataEntry(DtTouristActivity dtTouristActivity)
			throws ActivityDoesNotExistException, RepeatedActivityNameException {
		iTouristActivityController.activityDataEntry(dtTouristActivity);
	}

	@WebMethod
	public void modifyActivity(DtTouristActivity dtTouristActivity) {
		iTouristActivityController.modifyActivity(dtTouristActivity);
	}

	@WebMethod
	public String[] listTouristActivitiesByStatus(TouristActivityStatus status) {
		return iTouristActivityController.listTouristActivitiesByStatus(status);
	}

	@WebMethod
	public DtActivityWithOutings consultTouristActivityData(String activityName) throws ActivityDoesNotExistException {
		return iTouristActivityController.consultTouristActivityData(activityName);
	}

	@WebMethod
	public String[] listTouristActivitiesBySupplierNickname(String nickname) {
		return iTouristActivityController.listTouristActivitiesBySupplierNickname(nickname);
	}
}

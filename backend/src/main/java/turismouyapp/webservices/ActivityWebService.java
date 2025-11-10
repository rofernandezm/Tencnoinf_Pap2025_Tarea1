package turismouyapp.webservices;

import turismouyapp.core.controller.TouristActivityController;
import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.TouristActivityStatus;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.exceptions.RepeatedActivityNameException;
import turismouyapp.core.interfaces.ITouristActivityController;
import turismouyapp.webservices.interfaces.IActivityWebService;

import java.util.ArrayList;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;

@SuppressWarnings("unchecked")
@WebService(serviceName = "ActivityService", portName = "ActivityPort", targetNamespace = "http://ws.turismouyapp/schema", endpointInterface = "turismouyapp.webservices.interfaces.IActivityWebService")
public class ActivityWebService implements IActivityWebService {

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
	public ArrayList<DtActivityWithOutings> listTouristActivityData() throws ActivityDoesNotExistException {
		return (ArrayList) iTouristActivityController.listTouristActivityData();
	}

	public String[] listTouristActivities() throws ActivityDoesNotExistException {
		return iTouristActivityController.listTouristActivities();
	}

	public void activityDataEntry(DtTouristActivity dtTouristActivity)
			throws ActivityDoesNotExistException, RepeatedActivityNameException {
		// DEBUG: Log para ver qué está llegando
		System.out.println("=== DEBUG activityDataEntry ===");
		System.out.println("dtTouristActivity: " + dtTouristActivity);
		if (dtTouristActivity != null) {
			System.out.println("activityName: [" + dtTouristActivity.getActivityName() + "]");
			System.out.println("description: [" + dtTouristActivity.getDescription() + "]");
			System.out.println("duration: [" + dtTouristActivity.getDuration() + "]");
			System.out.println("costTurist: [" + dtTouristActivity.getCostTurist() + "]");
			System.out.println("city: [" + dtTouristActivity.getCity() + "]");
			System.out.println("registrationDate: [" + dtTouristActivity.getRegistrationDate() + "]");
			System.out.println("supplierNickname: [" + dtTouristActivity.getSupplierNickname() + "]");
			System.out.println("status: [" + dtTouristActivity.getStatus() + "]");
			System.out.println("imageActPath: [" + dtTouristActivity.getImageActPath() + "]");
		}
		System.out.println("===============================");
		
		iTouristActivityController.activityDataEntry(dtTouristActivity);
	}

	public void modifyActivity(DtTouristActivity dtTouristActivity) {
		iTouristActivityController.modifyActivity(dtTouristActivity);
	}

	public String[] listTouristActivitiesByStatus(TouristActivityStatus status) {
		return iTouristActivityController.listTouristActivitiesByStatus(status);
	}

	public DtActivityWithOutings consultTouristActivityData(String activityName) throws ActivityDoesNotExistException {
		return iTouristActivityController.consultTouristActivityData(activityName);
	}

	public String[] listTouristActivitiesBySupplierNickname(String nickname) {
		return iTouristActivityController.listTouristActivitiesBySupplierNickname(nickname);
	}
	
	public ArrayList<DtActivityWithOutings> arrayListTouristActivitiesBySupplierNickName(String nickname) throws ActivityDoesNotExistException{
		return (ArrayList) iTouristActivityController.listTouristActivitiesBySupplierNickName(nickname);
	}
	
}

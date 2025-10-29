package ServerTurismouy;

import turismouyapp.core.controller.TouristActivityController;
import turismouyapp.core.controller.TouristOutingAndInscriptionController;
import turismouyapp.core.controller.UserController;
import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtInscriptionTouristOuting;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.dto.TouristActivityStatus;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.exceptions.RepeatedActivityNameException;
import turismouyapp.core.exceptions.RepeatedInscriptionToTouristOutingException;
import turismouyapp.core.exceptions.RepeatedTouristOutingException;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;
import turismouyapp.core.exceptions.TouristOutingDoesNotExistException;
import turismouyapp.core.interfaces.ITouristActivityController;
import turismouyapp.core.interfaces.ITouristOutingAndInscriptionController;
import turismouyapp.core.interfaces.IUserController;

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
public class WebServices {

	private Endpoint endpoint = null;
	private final IUserController iUserController;
	private final ITouristActivityController iTouristActivityController;
	private final ITouristOutingAndInscriptionController iTouristOutingAndInscriptionController;

	// Constructor
	public WebServices() {
		this.iUserController = new UserController();
		this.iTouristActivityController = new TouristActivityController();
		this.iTouristOutingAndInscriptionController = new TouristOutingAndInscriptionController();
	}

	// Operaciones que quiero publicar

	@WebMethod(exclude = true)
	public void publicar() {
		endpoint = Endpoint.publish("http://localhost:8007/turismo", this);
	}

	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
		return endpoint;
	}

	// Metodos de user
	@WebMethod
	public DtUser consultUserData(String nicknameOrEmail) {
		return iUserController.consultUserData(nicknameOrEmail);
	}

	@WebMethod
	public DtUser consultUserDataByEmail(String nicknameOrEmail) {
		return iUserController.consultUserDataByEmail(nicknameOrEmail);
	}

	@WebMethod
	public void dataEntryUser(DtUser dtUser) throws RepeatedUserEmailException, RepeatedUserNicknameException {
		iUserController.dataEntry(dtUser);
	}

	@WebMethod
	public void confirmRegistration() {
		iUserController.confirmRegistration();
	}

	@WebMethod
	public void modifyUserData(DtUser dtUser) {
		iUserController.modifyUserData(dtUser);
	}

	@WebMethod
	public String[] listUsers() {
		return iUserController.listUsers();
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

	// Metodos de tourist outings and inscriptions
	@WebMethod
	public void outingDataEntry(DtTouristOuting dtTouristOuting) throws RepeatedTouristOutingException {
		iTouristOutingAndInscriptionController.outingDataEntry(dtTouristOuting);
	}

	@WebMethod
	public void updateOutingImageName(String outingName, String imageName) {
		iTouristOutingAndInscriptionController.updateOutingImageName(outingName, imageName);
	}

	@WebMethod
	public DtInscriptionTouristOuting[] listOutingInscription(String outingName) {
		return iTouristOutingAndInscriptionController.listOutingInscription(outingName);
	}

	@WebMethod
	public DtTouristOuting consultTouristOutingData(String outingName) throws TouristOutingDoesNotExistException {
		return iTouristOutingAndInscriptionController.consultTouristOutingData(outingName);
	}

	@WebMethod
	public void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, String userNickname,
			String outingName) throws RepeatedInscriptionToTouristOutingException {
		iTouristOutingAndInscriptionController.inscriptionDataEntry(dtInscriptionOuting, userNickname, outingName);
	}

}

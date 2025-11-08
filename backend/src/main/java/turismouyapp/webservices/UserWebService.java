package turismouyapp.webservices;

import turismouyapp.core.controller.UserController;
import turismouyapp.core.dto.DtSupplier;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;
import turismouyapp.core.interfaces.IUserController;
import turismouyapp.webservices.interfaces.IUserWebService;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;

@WebService(serviceName = "UserService", portName = "UserPort", targetNamespace = "http://ws.turismouyapp/schema", endpointInterface = "turismouyapp.webservices.interfaces.IUserWebService")
public class UserWebService implements IUserWebService {

	private Endpoint endpoint = null;
	private final IUserController iUserController;

	public UserWebService() {
		this.iUserController = new UserController();
	}

	// Operaciones disponibles en el webservice
	@WebMethod(exclude = true)
	public void publicar() {
		endpoint = Endpoint.publish("http://localhost:8007/ws/user", this);
		System.out.println("[UserWebService] "+ endpoint.toString());
	}

	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
		return endpoint;
	}

	public DtUser consultUserData(String nicknameOrEmail) {
		return iUserController.consultUserData(nicknameOrEmail);
	}

	public DtUser consultUserDataByEmail(String nicknameOrEmail) {
		return iUserController.consultUserDataByEmail(nicknameOrEmail);
	}

	public void dataEntryUser(DtUser dtUser) throws RepeatedUserEmailException, RepeatedUserNicknameException {
		iUserController.dataEntry(dtUser);
	}
	
	public void dataEntrySupplier(DtSupplier dtSupplier) throws RepeatedUserEmailException, RepeatedUserNicknameException {
		System.out.println(dtSupplier.getBirthDate().getClass());
		iUserController.dataEntrySupplier(dtSupplier);
	}

	public void confirmRegistration() {
		iUserController.confirmRegistration();
	}

	public void modifyUserData(DtUser dtUser) {
		iUserController.modifyUserData(dtUser);
	}

	public String[] listUsers() {
		return iUserController.listUsers();
	}
}

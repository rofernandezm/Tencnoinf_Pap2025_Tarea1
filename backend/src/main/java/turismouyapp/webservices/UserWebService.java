package turismouyapp.webservices;

import turismouyapp.core.controller.UserController;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;
import turismouyapp.core.interfaces.IUserController;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class UserWebService {

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
}

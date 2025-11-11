package turismouyapp.webservices.interfaces;

import turismouyapp.core.dto.DtSupplier;
import turismouyapp.core.dto.DtTourist;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.bind.annotation.XmlSeeAlso;

/**
 * Interfaz del webservice de usuarios. Define el contrato estable
 * (endpointInterface) que produce WSDL reproducible y controlado para
 * operaciones de gestión de usuarios.
 */
@WebService(targetNamespace = "http://ws.turismouyapp/schema", name = "UserPortType")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.WRAPPED)
@XmlSeeAlso({ DtTourist.class, DtSupplier.class })
public interface IUserWebService {

	@WebMethod
	@WebResult(name = "DtUser")
	DtUser consultUserData(@WebParam(name = "nicknameOrEmail") String nicknameOrEmail);

	@WebMethod
	@WebResult(name = "DtUser")
	DtUser consultUserDataByEmail(@WebParam(name = "email") String nicknameOrEmail);

	@WebMethod
	void dataEntryUser(@WebParam(name = "dtUser") DtUser dtUser)
			throws RepeatedUserEmailException, RepeatedUserNicknameException;

	@WebMethod
	void dataEntryTourist(@WebParam(name = "dtTourist") DtTourist dtTourist)
			throws RepeatedUserEmailException, RepeatedUserNicknameException;

	@WebMethod
	void dataEntrySupplier(@WebParam(name = "dtSupplier") DtSupplier dtSupplier)
			throws RepeatedUserEmailException, RepeatedUserNicknameException;

	@WebMethod
	void confirmRegistration();

	@WebMethod
	void modifyUserData(@WebParam(name = "dtUser") DtUser dtUser);

	@WebMethod
	void modifyTouristData(@WebParam(name = "dtTourist") DtTourist dtTourist);

	@WebMethod
	void modifySupplierData(@WebParam(name = "dtSupplier") DtSupplier dtSupplier);

	@WebMethod
	@WebResult(name = "users")
	String[] listUsers();

	@WebMethod
	public void updateProfileImageUser(@WebParam(name = "nickname") String nickname,
			@WebParam(name = "imageName") String imageName);
}

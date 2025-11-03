package turismouyapp.webservices.interfaces;

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

/**
 * Interfaz del webservice de usuarios. Define el contrato estable (endpointInterface)
 * que produce WSDL reproducible y controlado para operaciones de gestión de usuarios.
 */
@WebService(targetNamespace = "http://ws.turismouyapp/schema", name = "UserPortType")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.WRAPPED)
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
    void confirmRegistration();

    @WebMethod
    void modifyUserData(@WebParam(name = "dtUser") DtUser dtUser);

    @WebMethod
    @WebResult(name = "users")
    String[] listUsers();
}

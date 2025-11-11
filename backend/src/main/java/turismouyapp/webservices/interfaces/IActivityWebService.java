package turismouyapp.webservices.interfaces;

import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtTouristActivity;
import turismouyapp.core.dto.TouristActivityStatus;
import turismouyapp.core.exceptions.ActivityDoesNotExistException;
import turismouyapp.core.exceptions.RepeatedActivityNameException;

import java.util.ArrayList;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;

/**
 * Interfaz del webservice de actividades turísticas. Define el contrato estable
 * para operaciones de consulta, creación y modificación de actividades.
 */
@WebService(targetNamespace = "http://ws.turismouyapp/schema", name = "ActivityPortType")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.WRAPPED)
public interface IActivityWebService {

    @WebMethod
    @WebResult(name = "activities")
    ArrayList<DtActivityWithOutings> listTouristActivityData() throws ActivityDoesNotExistException;

    @WebMethod
    @WebResult(name = "activityNames")
    String[] listTouristActivities() throws ActivityDoesNotExistException;

    @WebMethod
    void activityDataEntry(@WebParam(name = "dtTouristActivity") DtTouristActivity dtTouristActivity)
            throws RepeatedActivityNameException;

    @WebMethod
    void modifyActivity(@WebParam(name = "dtTouristActivity") DtTouristActivity dtTouristActivity);

    @WebMethod
    @WebResult(name = "activityNames")
    String[] listTouristActivitiesByStatus(@WebParam(name = "status") TouristActivityStatus status);

    @WebMethod
    @WebResult(name = "DtActivityWithOutings")
    DtActivityWithOutings consultTouristActivityData(@WebParam(name = "activityName") String activityName)
            throws ActivityDoesNotExistException;

    @WebMethod
    @WebResult(name = "activityNames")
    String[] listTouristActivitiesBySupplierNickname(@WebParam(name = "nickname") String nickname);
    
    @WebMethod
    @WebResult(name = "DtActivityWithOutings")
    public ArrayList<DtActivityWithOutings> arrayListTouristActivitiesBySupplierNickName(@WebParam(name = "nickname") String nickname) 
    		throws ActivityDoesNotExistException;
}

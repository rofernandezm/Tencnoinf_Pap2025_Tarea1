package turismouyapp.webservices.interfaces;

import turismouyapp.core.dto.DtInscriptionTouristOuting;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.exceptions.RepeatedInscriptionToTouristOutingException;
import turismouyapp.core.exceptions.RepeatedTouristOutingException;
import turismouyapp.core.exceptions.TouristOutingDoesNotExistException;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;

/**
 * Interfaz del webservice de salidas turísticas e inscripciones. Define el contrato estable
 * para operaciones de creación, consulta e inscripción a salidas turísticas.
 */
@WebService(targetNamespace = "http://ws.turismouyapp/schema", name = "OutingAndInscriptionPortType")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.WRAPPED)
public interface IOutingAndInscriptionWebService {

    @WebMethod
    void outingDataEntry(@WebParam(name = "dtTouristOuting") DtTouristOuting dtTouristOuting)
            throws RepeatedTouristOutingException;

    @WebMethod
    void updateOutingImageName(@WebParam(name = "outingName") String outingName,
                               @WebParam(name = "imageName") String imageName);

    @WebMethod
    @WebResult(name = "inscriptions")
    DtInscriptionTouristOuting[] listOutingInscription(@WebParam(name = "outingName") String outingName);

    @WebMethod
    @WebResult(name = "DtTouristOuting")
    DtTouristOuting consultTouristOutingData(@WebParam(name = "outingName") String outingName)
            throws TouristOutingDoesNotExistException;

    @WebMethod
    void inscriptionDataEntry(@WebParam(name = "dtInscriptionOuting") DtInscriptionTouristOuting dtInscriptionOuting,
                              @WebParam(name = "userNickname") String userNickname,
                              @WebParam(name = "outingName") String outingName)
            throws RepeatedInscriptionToTouristOutingException;
}

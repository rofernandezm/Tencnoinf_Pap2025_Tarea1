package turismouyapp.webservices;

import turismouyapp.core.controller.TouristOutingAndInscriptionController;
import turismouyapp.core.dto.DtInscriptionTouristOuting;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.exceptions.RepeatedInscriptionToTouristOutingException;
import turismouyapp.core.exceptions.RepeatedTouristOutingException;
import turismouyapp.core.exceptions.TouristOutingDoesNotExistException;
import turismouyapp.core.interfaces.ITouristOutingAndInscriptionController;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class OutingAndInscriptionWebService {

	private Endpoint endpoint = null;
	private final ITouristOutingAndInscriptionController iTouristOutingAndInscriptionController;

	public OutingAndInscriptionWebService() {
		this.iTouristOutingAndInscriptionController = new TouristOutingAndInscriptionController();
	}

	// Operaciones disponibles en el webservice
	@WebMethod(exclude = true)
	public void publicar() {
		endpoint = Endpoint.publish("http://localhost:8007/ws/outingAndInscription", this);
		System.out.println("[OutingAndInscriptionWebService] "+ endpoint.toString());
	}

	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
		return endpoint;
	}

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

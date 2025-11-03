package turismouyapp.webservices;

import turismouyapp.core.controller.TouristOutingAndInscriptionController;
import turismouyapp.core.dto.DtInscriptionTouristOuting;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.exceptions.RepeatedInscriptionToTouristOutingException;
import turismouyapp.core.exceptions.RepeatedTouristOutingException;
import turismouyapp.core.exceptions.TouristOutingDoesNotExistException;
import turismouyapp.core.interfaces.ITouristOutingAndInscriptionController;
import turismouyapp.webservices.interfaces.IOutingAndInscriptionWebService;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;

@WebService(serviceName = "OutingAndInscriptionService", portName = "OutingAndInscriptionPort", targetNamespace = "http://ws.turismouyapp/schema", endpointInterface = "turismouyapp.webservices.interfaces.IOutingAndInscriptionWebService")
public class OutingAndInscriptionWebService implements IOutingAndInscriptionWebService {

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

	public void outingDataEntry(DtTouristOuting dtTouristOuting) throws RepeatedTouristOutingException {
		iTouristOutingAndInscriptionController.outingDataEntry(dtTouristOuting);
	}

	public void updateOutingImageName(String outingName, String imageName) {
		iTouristOutingAndInscriptionController.updateOutingImageName(outingName, imageName);
	}

	public DtInscriptionTouristOuting[] listOutingInscription(String outingName) {
		return iTouristOutingAndInscriptionController.listOutingInscription(outingName);
	}

	public DtTouristOuting consultTouristOutingData(String outingName) throws TouristOutingDoesNotExistException {
		return iTouristOutingAndInscriptionController.consultTouristOutingData(outingName);
	}

	public void inscriptionDataEntry(DtInscriptionTouristOuting dtInscriptionOuting, String userNickname,
			String outingName) throws RepeatedInscriptionToTouristOutingException {
		iTouristOutingAndInscriptionController.inscriptionDataEntry(dtInscriptionOuting, userNickname, outingName);
	}

}

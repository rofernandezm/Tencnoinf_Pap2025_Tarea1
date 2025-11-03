package turismouyapp.core.exceptions.fault;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Fault bean para representar detalles de RepeatedInscriptionToTouristOutingException en SOAP faults.
 * Se serializa en el elemento <detail> del SOAP Fault que recibe el cliente.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RepeatedInscriptionToTouristOutingFault", namespace = "http://ws.turismouyapp/schema")
public class RepeatedInscriptionToTouristOutingFault {

    private String message;

    public RepeatedInscriptionToTouristOutingFault() {
    }

    public RepeatedInscriptionToTouristOutingFault(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

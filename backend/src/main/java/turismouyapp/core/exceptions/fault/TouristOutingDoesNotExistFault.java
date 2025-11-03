package turismouyapp.core.exceptions.fault;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Fault bean para representar detalles de TouristOutingDoesNotExistException en SOAP faults.
 * Se serializa en el elemento <detail> del SOAP Fault que recibe el cliente.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TouristOutingDoesNotExistFault", namespace = "http://ws.turismouyapp/schema")
public class TouristOutingDoesNotExistFault {

    private String message;

    public TouristOutingDoesNotExistFault() {
    }

    public TouristOutingDoesNotExistFault(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

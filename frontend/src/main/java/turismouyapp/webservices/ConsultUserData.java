
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para consultUserData complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="consultUserData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nicknameOrEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultUserData", propOrder = {
    "nicknameOrEmail"
})
public class ConsultUserData {

    @XmlElement(namespace = "")
    protected String nicknameOrEmail;

    /**
     * Obtiene el valor de la propiedad nicknameOrEmail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNicknameOrEmail() {
        return nicknameOrEmail;
    }

    /**
     * Define el valor de la propiedad nicknameOrEmail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNicknameOrEmail(String value) {
        this.nicknameOrEmail = value;
    }

}

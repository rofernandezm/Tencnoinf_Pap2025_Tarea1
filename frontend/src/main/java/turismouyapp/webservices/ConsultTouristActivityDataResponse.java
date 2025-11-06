
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para consultTouristActivityDataResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="consultTouristActivityDataResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DtActivityWithOutings" type="{http://ws.turismouyapp/schema}dtActivityWithOutings" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultTouristActivityDataResponse", propOrder = {
    "dtActivityWithOutings"
})
public class ConsultTouristActivityDataResponse {

    @XmlElement(name = "DtActivityWithOutings", namespace = "")
    protected DtActivityWithOutings dtActivityWithOutings;

    /**
     * Obtiene el valor de la propiedad dtActivityWithOutings.
     * 
     * @return
     *     possible object is
     *     {@link DtActivityWithOutings }
     *     
     */
    public DtActivityWithOutings getDtActivityWithOutings() {
        return dtActivityWithOutings;
    }

    /**
     * Define el valor de la propiedad dtActivityWithOutings.
     * 
     * @param value
     *     allowed object is
     *     {@link DtActivityWithOutings }
     *     
     */
    public void setDtActivityWithOutings(DtActivityWithOutings value) {
        this.dtActivityWithOutings = value;
    }

}

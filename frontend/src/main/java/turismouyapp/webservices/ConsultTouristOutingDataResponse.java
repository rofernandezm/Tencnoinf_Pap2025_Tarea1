
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para consultTouristOutingDataResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="consultTouristOutingDataResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DtTouristOuting" type="{http://ws.turismouyapp/schema}dtTouristOuting" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultTouristOutingDataResponse", propOrder = {
    "dtTouristOuting"
})
public class ConsultTouristOutingDataResponse {

    @XmlElement(name = "DtTouristOuting")
    protected DtTouristOuting dtTouristOuting;

    /**
     * Obtiene el valor de la propiedad dtTouristOuting.
     * 
     * @return
     *     possible object is
     *     {@link DtTouristOuting }
     *     
     */
    public DtTouristOuting getDtTouristOuting() {
        return dtTouristOuting;
    }

    /**
     * Define el valor de la propiedad dtTouristOuting.
     * 
     * @param value
     *     allowed object is
     *     {@link DtTouristOuting }
     *     
     */
    public void setDtTouristOuting(DtTouristOuting value) {
        this.dtTouristOuting = value;
    }

}

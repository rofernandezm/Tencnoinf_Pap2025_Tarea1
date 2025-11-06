
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para modifyActivity complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="modifyActivity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dtTouristActivity" type="{http://ws.turismouyapp/schema}dtTouristActivity" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyActivity", propOrder = {
    "dtTouristActivity"
})
public class ModifyActivity {

    @XmlElement(namespace = "")
    protected DtTouristActivity dtTouristActivity;

    /**
     * Obtiene el valor de la propiedad dtTouristActivity.
     * 
     * @return
     *     possible object is
     *     {@link DtTouristActivity }
     *     
     */
    public DtTouristActivity getDtTouristActivity() {
        return dtTouristActivity;
    }

    /**
     * Define el valor de la propiedad dtTouristActivity.
     * 
     * @param value
     *     allowed object is
     *     {@link DtTouristActivity }
     *     
     */
    public void setDtTouristActivity(DtTouristActivity value) {
        this.dtTouristActivity = value;
    }

}

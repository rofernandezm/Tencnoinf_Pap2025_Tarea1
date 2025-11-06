
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtTouristOuting complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="dtTouristOuting"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="outingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="maxNumTourists" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="departurePoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="departureDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dischargeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="activityName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="imageOutPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtTouristOuting", propOrder = {
    "outingName",
    "maxNumTourists",
    "departurePoint",
    "departureDate",
    "dischargeDate",
    "activityName",
    "imageOutPath"
})
public class DtTouristOuting {

    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected String outingName;
    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected int maxNumTourists;
    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected String departurePoint;
    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected String departureDate;
    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected String dischargeDate;
    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected String activityName;
    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected String imageOutPath;

    /**
     * Obtiene el valor de la propiedad outingName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutingName() {
        return outingName;
    }

    /**
     * Define el valor de la propiedad outingName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutingName(String value) {
        this.outingName = value;
    }

    /**
     * Obtiene el valor de la propiedad maxNumTourists.
     * 
     */
    public int getMaxNumTourists() {
        return maxNumTourists;
    }

    /**
     * Define el valor de la propiedad maxNumTourists.
     * 
     */
    public void setMaxNumTourists(int value) {
        this.maxNumTourists = value;
    }

    /**
     * Obtiene el valor de la propiedad departurePoint.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeparturePoint() {
        return departurePoint;
    }

    /**
     * Define el valor de la propiedad departurePoint.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeparturePoint(String value) {
        this.departurePoint = value;
    }

    /**
     * Obtiene el valor de la propiedad departureDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDate() {
        return departureDate;
    }

    /**
     * Define el valor de la propiedad departureDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDate(String value) {
        this.departureDate = value;
    }

    /**
     * Obtiene el valor de la propiedad dischargeDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDischargeDate() {
        return dischargeDate;
    }

    /**
     * Define el valor de la propiedad dischargeDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDischargeDate(String value) {
        this.dischargeDate = value;
    }

    /**
     * Obtiene el valor de la propiedad activityName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * Define el valor de la propiedad activityName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityName(String value) {
        this.activityName = value;
    }

    /**
     * Obtiene el valor de la propiedad imageOutPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageOutPath() {
        return imageOutPath;
    }

    /**
     * Define el valor de la propiedad imageOutPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageOutPath(String value) {
        this.imageOutPath = value;
    }

}

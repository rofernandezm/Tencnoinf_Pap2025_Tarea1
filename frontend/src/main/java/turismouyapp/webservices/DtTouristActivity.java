
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtTouristActivity complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="dtTouristActivity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="activityName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="costTurist" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="registrationDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="supplierNickname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://ws.turismouyapp/schema}TouristActivityStatus" minOccurs="0"/&gt;
 *         &lt;element name="imageActPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtTouristActivity", propOrder = {
    "activityName",
    "description",
    "duration",
    "costTurist",
    "city",
    "registrationDate",
    "supplierNickname",
    "status",
    "imageActPath"
})
public class DtTouristActivity {

    protected String activityName;
    protected String description;
    protected String duration;
    protected float costTurist;
    protected String city;
    protected String registrationDate;
    protected String supplierNickname;
    @XmlSchemaType(name = "string")
    protected TouristActivityStatus status;
    protected String imageActPath;

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
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad duration.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Define el valor de la propiedad duration.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * Obtiene el valor de la propiedad costTurist.
     * 
     */
    public float getCostTurist() {
        return costTurist;
    }

    /**
     * Define el valor de la propiedad costTurist.
     * 
     */
    public void setCostTurist(float value) {
        this.costTurist = value;
    }

    /**
     * Obtiene el valor de la propiedad city.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Define el valor de la propiedad city.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Obtiene el valor de la propiedad registrationDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Define el valor de la propiedad registrationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrationDate(String value) {
        this.registrationDate = value;
    }

    /**
     * Obtiene el valor de la propiedad supplierNickname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierNickname() {
        return supplierNickname;
    }

    /**
     * Define el valor de la propiedad supplierNickname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierNickname(String value) {
        this.supplierNickname = value;
    }

    /**
     * Obtiene el valor de la propiedad status.
     * 
     * @return
     *     possible object is
     *     {@link TouristActivityStatus }
     *     
     */
    public TouristActivityStatus getStatus() {
        return status;
    }

    /**
     * Define el valor de la propiedad status.
     * 
     * @param value
     *     allowed object is
     *     {@link TouristActivityStatus }
     *     
     */
    public void setStatus(TouristActivityStatus value) {
        this.status = value;
    }

    /**
     * Obtiene el valor de la propiedad imageActPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageActPath() {
        return imageActPath;
    }

    /**
     * Define el valor de la propiedad imageActPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageActPath(String value) {
        this.imageActPath = value;
    }

}

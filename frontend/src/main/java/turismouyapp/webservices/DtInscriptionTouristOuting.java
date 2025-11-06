
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtInscriptionTouristOuting complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="dtInscriptionTouristOuting"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="touristAmount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="totalCost" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="inscriptionDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="turistOuting" type="{http://ws.turismouyapp/schema}dtTouristOuting" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtInscriptionTouristOuting", propOrder = {
    "touristAmount",
    "totalCost",
    "inscriptionDate",
    "turistOuting"
})
public class DtInscriptionTouristOuting {

    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected int touristAmount;
    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected float totalCost;
    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected String inscriptionDate;
    @XmlElement(namespace = "http://ws.turismouyapp/schema")
    protected DtTouristOuting turistOuting;

    /**
     * Obtiene el valor de la propiedad touristAmount.
     * 
     */
    public int getTouristAmount() {
        return touristAmount;
    }

    /**
     * Define el valor de la propiedad touristAmount.
     * 
     */
    public void setTouristAmount(int value) {
        this.touristAmount = value;
    }

    /**
     * Obtiene el valor de la propiedad totalCost.
     * 
     */
    public float getTotalCost() {
        return totalCost;
    }

    /**
     * Define el valor de la propiedad totalCost.
     * 
     */
    public void setTotalCost(float value) {
        this.totalCost = value;
    }

    /**
     * Obtiene el valor de la propiedad inscriptionDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInscriptionDate() {
        return inscriptionDate;
    }

    /**
     * Define el valor de la propiedad inscriptionDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInscriptionDate(String value) {
        this.inscriptionDate = value;
    }

    /**
     * Obtiene el valor de la propiedad turistOuting.
     * 
     * @return
     *     possible object is
     *     {@link DtTouristOuting }
     *     
     */
    public DtTouristOuting getTuristOuting() {
        return turistOuting;
    }

    /**
     * Define el valor de la propiedad turistOuting.
     * 
     * @param value
     *     allowed object is
     *     {@link DtTouristOuting }
     *     
     */
    public void setTuristOuting(DtTouristOuting value) {
        this.turistOuting = value;
    }

}


package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para inscriptionDataEntry complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="inscriptionDataEntry"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dtInscriptionOuting" type="{http://ws.turismouyapp/schema}dtInscriptionTouristOuting" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="userNickname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="outingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inscriptionDataEntry", propOrder = {
    "dtInscriptionOuting",
    "userNickname",
    "outingName"
})
public class InscriptionDataEntry {

    protected DtInscriptionTouristOuting dtInscriptionOuting;
    protected String userNickname;
    protected String outingName;

    /**
     * Obtiene el valor de la propiedad dtInscriptionOuting.
     * 
     * @return
     *     possible object is
     *     {@link DtInscriptionTouristOuting }
     *     
     */
    public DtInscriptionTouristOuting getDtInscriptionOuting() {
        return dtInscriptionOuting;
    }

    /**
     * Define el valor de la propiedad dtInscriptionOuting.
     * 
     * @param value
     *     allowed object is
     *     {@link DtInscriptionTouristOuting }
     *     
     */
    public void setDtInscriptionOuting(DtInscriptionTouristOuting value) {
        this.dtInscriptionOuting = value;
    }

    /**
     * Obtiene el valor de la propiedad userNickname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserNickname() {
        return userNickname;
    }

    /**
     * Define el valor de la propiedad userNickname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserNickname(String value) {
        this.userNickname = value;
    }

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

}

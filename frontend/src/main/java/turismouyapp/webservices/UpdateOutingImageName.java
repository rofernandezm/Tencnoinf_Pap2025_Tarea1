
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para updateOutingImageName complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="updateOutingImageName"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="outingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="imageName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateOutingImageName", propOrder = {
    "outingName",
    "imageName"
})
public class UpdateOutingImageName {

    protected String outingName;
    protected String imageName;

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
     * Obtiene el valor de la propiedad imageName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Define el valor de la propiedad imageName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageName(String value) {
        this.imageName = value;
    }

}

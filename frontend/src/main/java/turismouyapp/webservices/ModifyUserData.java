
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para modifyUserData complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="modifyUserData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dtUser" type="{http://ws.turismouyapp/schema}dtUser" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyUserData", propOrder = {
    "dtUser"
})
public class ModifyUserData {

    @XmlElement(namespace = "")
    protected DtUser dtUser;

    /**
     * Obtiene el valor de la propiedad dtUser.
     * 
     * @return
     *     possible object is
     *     {@link DtUser }
     *     
     */
    public DtUser getDtUser() {
        return dtUser;
    }

    /**
     * Define el valor de la propiedad dtUser.
     * 
     * @param value
     *     allowed object is
     *     {@link DtUser }
     *     
     */
    public void setDtUser(DtUser value) {
        this.dtUser = value;
    }

}


package turismouyapp.webservices;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DtTouristProfile complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DtTouristProfile"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://ws.turismouyapp/schema}dtUserProfile"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="inscriptionTourisOuting" type="{http://ws.turismouyapp/schema}dtInscriptionTouristOuting" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DtTouristProfile", propOrder = {
    "inscriptionTourisOuting"
})
public class DtTouristProfile
    extends DtUserProfile
{

    @XmlElement(nillable = true)
    protected List<DtInscriptionTouristOuting> inscriptionTourisOuting;

    /**
     * Gets the value of the inscriptionTourisOuting property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the inscriptionTourisOuting property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInscriptionTourisOuting().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtInscriptionTouristOuting }
     * 
     * 
     */
    public List<DtInscriptionTouristOuting> getInscriptionTourisOuting() {
        if (inscriptionTourisOuting == null) {
            inscriptionTourisOuting = new ArrayList<DtInscriptionTouristOuting>();
        }
        return this.inscriptionTourisOuting;
    }

}

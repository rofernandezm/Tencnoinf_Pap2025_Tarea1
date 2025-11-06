
package turismouyapp.webservices;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DtSupplierProfile complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DtSupplierProfile"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://ws.turismouyapp/schema}dtUserProfile"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="activities" type="{http://ws.turismouyapp/schema}dtActivityWithOutings" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DtSupplierProfile", propOrder = {
    "activities"
})
public class DtSupplierProfile
    extends DtUserProfile
{

    @XmlElement(nillable = true)
    protected List<DtActivityWithOutings> activities;

    /**
     * Gets the value of the activities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the activities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtActivityWithOutings }
     * 
     * 
     */
    public List<DtActivityWithOutings> getActivities() {
        if (activities == null) {
            activities = new ArrayList<DtActivityWithOutings>();
        }
        return this.activities;
    }

}

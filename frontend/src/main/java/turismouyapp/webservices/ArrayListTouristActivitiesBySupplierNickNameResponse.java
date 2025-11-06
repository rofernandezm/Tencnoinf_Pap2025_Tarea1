
package turismouyapp.webservices;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para arrayListTouristActivitiesBySupplierNickNameResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="arrayListTouristActivitiesBySupplierNickNameResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DtActivityWithOutings" type="{http://ws.turismouyapp/schema}dtActivityWithOutings" maxOccurs="unbounded" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "arrayListTouristActivitiesBySupplierNickNameResponse", propOrder = {
    "dtActivityWithOutings"
})
public class ArrayListTouristActivitiesBySupplierNickNameResponse {

    @XmlElement(name = "DtActivityWithOutings", namespace = "")
    protected List<DtActivityWithOutings> dtActivityWithOutings;

    /**
     * Gets the value of the dtActivityWithOutings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the dtActivityWithOutings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDtActivityWithOutings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DtActivityWithOutings }
     * 
     * 
     */
    public List<DtActivityWithOutings> getDtActivityWithOutings() {
        if (dtActivityWithOutings == null) {
            dtActivityWithOutings = new ArrayList<DtActivityWithOutings>();
        }
        return this.dtActivityWithOutings;
    }

}

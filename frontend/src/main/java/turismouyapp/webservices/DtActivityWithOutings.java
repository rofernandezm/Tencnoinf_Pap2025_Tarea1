
package turismouyapp.webservices;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para dtActivityWithOutings complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="dtActivityWithOutings"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="activity" type="{http://ws.turismouyapp/schema}dtTouristActivity" minOccurs="0"/&gt;
 *         &lt;element name="outings" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="outing" type="{http://ws.turismouyapp/schema}dtTouristOuting" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtActivityWithOutings", propOrder = {
    "activity",
    "outings"
})
public class DtActivityWithOutings {

    protected DtTouristActivity activity;
    protected DtActivityWithOutings.Outings outings;

    /**
     * Obtiene el valor de la propiedad activity.
     * 
     * @return
     *     possible object is
     *     {@link DtTouristActivity }
     *     
     */
    public DtTouristActivity getActivity() {
        return activity;
    }

    /**
     * Define el valor de la propiedad activity.
     * 
     * @param value
     *     allowed object is
     *     {@link DtTouristActivity }
     *     
     */
    public void setActivity(DtTouristActivity value) {
        this.activity = value;
    }

    /**
     * Obtiene el valor de la propiedad outings.
     * 
     * @return
     *     possible object is
     *     {@link DtActivityWithOutings.Outings }
     *     
     */
    public DtActivityWithOutings.Outings getOutings() {
        return outings;
    }

    /**
     * Define el valor de la propiedad outings.
     * 
     * @param value
     *     allowed object is
     *     {@link DtActivityWithOutings.Outings }
     *     
     */
    public void setOutings(DtActivityWithOutings.Outings value) {
        this.outings = value;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="outing" type="{http://ws.turismouyapp/schema}dtTouristOuting" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "outing"
    })
    public static class Outings {

        protected List<DtTouristOuting> outing;

        /**
         * Gets the value of the outing property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a <CODE>set</CODE> method for the outing property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOuting().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DtTouristOuting }
         * 
         * 
         */
        public List<DtTouristOuting> getOuting() {
            if (outing == null) {
                outing = new ArrayList<DtTouristOuting>();
            }
            return this.outing;
        }

    }

}

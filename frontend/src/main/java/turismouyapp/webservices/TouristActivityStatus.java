
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para TouristActivityStatus.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>
 * &lt;simpleType name="TouristActivityStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ADDED"/&gt;
 *     &lt;enumeration value="CONFIRMED"/&gt;
 *     &lt;enumeration value="REJECTED"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TouristActivityStatus")
@XmlEnum
public enum TouristActivityStatus {

    ADDED,
    CONFIRMED,
    REJECTED;

    public String value() {
        return name();
    }

    public static TouristActivityStatus fromValue(String v) {
        return valueOf(v);
    }

}

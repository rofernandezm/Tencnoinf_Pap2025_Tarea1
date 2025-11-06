
package turismouyapp.webservices;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para UserType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <pre>
 * &lt;simpleType name="UserType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SUPPLIER"/&gt;
 *     &lt;enumeration value="TOURIST"/&gt;
 *     &lt;enumeration value="GUEST"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "UserType")
@XmlEnum
public enum UserType {

    SUPPLIER,
    TOURIST,
    GUEST;

    public String value() {
        return name();
    }

    public static UserType fromValue(String v) {
        return valueOf(v);
    }

}

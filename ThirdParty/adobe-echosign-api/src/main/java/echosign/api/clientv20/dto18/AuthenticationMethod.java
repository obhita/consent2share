
package echosign.api.clientv20.dto18;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticationMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AuthenticationMethod">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="INHERITED_FROM_DOCUMENT"/>
 *     &lt;enumeration value="PASSWORD"/>
 *     &lt;enumeration value="WEB_IDENTITY"/>
 *     &lt;enumeration value="KBA"/>
 *     &lt;enumeration value="PHONE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AuthenticationMethod")
@XmlEnum
public enum AuthenticationMethod {

    NONE,
    INHERITED_FROM_DOCUMENT,
    PASSWORD,
    WEB_IDENTITY,
    KBA,
    PHONE;

    public String value() {
        return name();
    }

    public static AuthenticationMethod fromValue(String v) {
        return valueOf(v);
    }

}

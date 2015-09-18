
package echosign.api.clientv20.dto18;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserRole.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UserRole">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="API"/>
 *     &lt;enumeration value="GROUP_ADMIN"/>
 *     &lt;enumeration value="ACCOUNT_ADMIN"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UserRole")
@XmlEnum
public enum UserRole {

    API,
    GROUP_ADMIN,
    ACCOUNT_ADMIN,
    OTHER;

    public String value() {
        return name();
    }

    public static UserRole fromValue(String v) {
        return valueOf(v);
    }

}

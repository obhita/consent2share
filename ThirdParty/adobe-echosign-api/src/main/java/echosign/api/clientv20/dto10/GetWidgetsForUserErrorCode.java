
package echosign.api.clientv20.dto10;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetWidgetsForUserErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetWidgetsForUserErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_USER_CREDENTIALS"/>
 *     &lt;enumeration value="PERMISSION_DENIED"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetWidgetsForUserErrorCode", namespace = "http://dto10.api.echosign")
@XmlEnum
public enum GetWidgetsForUserErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_USER_CREDENTIALS,
    PERMISSION_DENIED,
    EXCEPTION,
    MISC_ERROR;

    public String value() {
        return name();
    }

    public static GetWidgetsForUserErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

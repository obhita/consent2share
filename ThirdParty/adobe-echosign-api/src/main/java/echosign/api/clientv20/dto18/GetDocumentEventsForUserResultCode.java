
package echosign.api.clientv20.dto18;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetDocumentEventsForUserResultCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetDocumentEventsForUserResultCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_USER_CREDENTIALS"/>
 *     &lt;enumeration value="INVALID_DATE_RANGE"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="PERMISSION_DENIED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetDocumentEventsForUserResultCode")
@XmlEnum
public enum GetDocumentEventsForUserResultCode {

    OK,
    INVALID_API_KEY,
    INVALID_USER_CREDENTIALS,
    INVALID_DATE_RANGE,
    EXCEPTION,
    MISC_ERROR,
    PERMISSION_DENIED;

    public String value() {
        return name();
    }

    public static GetDocumentEventsForUserResultCode fromValue(String v) {
        return valueOf(v);
    }

}

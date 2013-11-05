
package echosign.api.clientv15.dto14;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetDocumentsForUserResultCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetDocumentsForUserResultCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_USER_CREDENTIALS"/>
 *     &lt;enumeration value="INVALID_USER_KEY"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="PERMISSION_DENIED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetDocumentsForUserResultCode")
@XmlEnum
public enum GetDocumentsForUserResultCode {

    OK,
    INVALID_API_KEY,
    INVALID_USER_CREDENTIALS,
    INVALID_USER_KEY,
    EXCEPTION,
    MISC_ERROR,
    PERMISSION_DENIED;

    public String value() {
        return name();
    }

    public static GetDocumentsForUserResultCode fromValue(String v) {
        return valueOf(v);
    }

}

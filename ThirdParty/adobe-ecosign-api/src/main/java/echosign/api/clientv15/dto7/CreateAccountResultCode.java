
package echosign.api.clientv15.dto7;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateAccountResultCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CreateAccountResultCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="ACCOUNT_CREATION_NOT_ENABLED"/>
 *     &lt;enumeration value="INVALID_SEAT_COUNT"/>
 *     &lt;enumeration value="USER_CREATION_ERROR"/>
 *     &lt;enumeration value="USER_ALREADY_HAS_ACCOUNT"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CreateAccountResultCode")
@XmlEnum
public enum CreateAccountResultCode {

    OK,
    INVALID_API_KEY,
    ACCOUNT_CREATION_NOT_ENABLED,
    INVALID_SEAT_COUNT,
    USER_CREATION_ERROR,
    USER_ALREADY_HAS_ACCOUNT,
    EXCEPTION,
    MISC_ERROR;

    public String value() {
        return name();
    }

    public static CreateAccountResultCode fromValue(String v) {
        return valueOf(v);
    }

}

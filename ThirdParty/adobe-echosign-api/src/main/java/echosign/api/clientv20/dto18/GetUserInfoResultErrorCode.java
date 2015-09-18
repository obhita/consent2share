
package echosign.api.clientv20.dto18;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetUserInfoResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetUserInfoResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_EMAIL"/>
 *     &lt;enumeration value="NO_PERMISSION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetUserInfoResultErrorCode")
@XmlEnum
public enum GetUserInfoResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_EMAIL,
    NO_PERMISSION,
    MISC_ERROR,
    EXCEPTION;

    public String value() {
        return name();
    }

    public static GetUserInfoResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

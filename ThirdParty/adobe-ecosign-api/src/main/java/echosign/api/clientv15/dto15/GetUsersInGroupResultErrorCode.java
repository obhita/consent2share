
package echosign.api.clientv15.dto15;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetUsersInGroupResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetUsersInGroupResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_GROUP_KEY"/>
 *     &lt;enumeration value="TERMS_NOT_ACCEPTED"/>
 *     &lt;enumeration value="NOT_IN_ACCOUNT"/>
 *     &lt;enumeration value="NO_PERMISSION_TO_EXECUTE_METHOD"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetUsersInGroupResultErrorCode")
@XmlEnum
public enum GetUsersInGroupResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_GROUP_KEY,
    TERMS_NOT_ACCEPTED,
    NOT_IN_ACCOUNT,
    NO_PERMISSION_TO_EXECUTE_METHOD,
    MISC_ERROR,
    EXCEPTION;

    public String value() {
        return name();
    }

    public static GetUsersInGroupResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}


package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetEmbeddedViewResultCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetEmbeddedViewResultCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_ACCESS_TOKEN"/>
 *     &lt;enumeration value="INVALID_ON_BEHALF_OF_USER"/>
 *     &lt;enumeration value="INVALID_TARGET"/>
 *     &lt;enumeration value="INVALID_TARGET_OBJECT"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="PERMISSION_DENIED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetEmbeddedViewResultCode")
@XmlEnum
public enum GetEmbeddedViewResultCode {

    OK,
    INVALID_ACCESS_TOKEN,
    INVALID_ON_BEHALF_OF_USER,
    INVALID_TARGET,
    INVALID_TARGET_OBJECT,
    EXCEPTION,
    MISC_ERROR,
    PERMISSION_DENIED;

    public String value() {
        return name();
    }

    public static GetEmbeddedViewResultCode fromValue(String v) {
        return valueOf(v);
    }

}

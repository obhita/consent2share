
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserVerificationStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UserVerificationStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VALID"/>
 *     &lt;enumeration value="DOES_NOT_EXIST"/>
 *     &lt;enumeration value="INVALID_PASSWORD"/>
 *     &lt;enumeration value="UNVERIFIED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UserVerificationStatus")
@XmlEnum
public enum UserVerificationStatus {

    VALID,
    DOES_NOT_EXIST,
    INVALID_PASSWORD,
    UNVERIFIED;

    public String value() {
        return name();
    }

    public static UserVerificationStatus fromValue(String v) {
        return valueOf(v);
    }

}

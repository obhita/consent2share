
package echosign.api.clientv15.dto13;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParticipantSecurityOption.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParticipantSecurityOption">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PASSWORD"/>
 *     &lt;enumeration value="WEB_IDENTITY"/>
 *     &lt;enumeration value="KBA"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParticipantSecurityOption")
@XmlEnum
public enum ParticipantSecurityOption {

    PASSWORD,
    WEB_IDENTITY,
    KBA,
    OTHER;

    public String value() {
        return name();
    }

    public static ParticipantSecurityOption fromValue(String v) {
        return valueOf(v);
    }

}

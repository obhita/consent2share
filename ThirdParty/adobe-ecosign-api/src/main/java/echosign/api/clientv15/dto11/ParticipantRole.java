
package echosign.api.clientv15.dto11;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParticipantRole.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParticipantRole">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SENDER"/>
 *     &lt;enumeration value="SIGNER"/>
 *     &lt;enumeration value="CC"/>
 *     &lt;enumeration value="DELEGATE"/>
 *     &lt;enumeration value="SHARE"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParticipantRole")
@XmlEnum
public enum ParticipantRole {

    SENDER,
    SIGNER,
    CC,
    DELEGATE,
    SHARE,
    OTHER;

    public String value() {
        return name();
    }

    public static ParticipantRole fromValue(String v) {
        return valueOf(v);
    }

}

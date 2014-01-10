
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignatureFlow.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SignatureFlow">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SENDER_SIGNS_LAST"/>
 *     &lt;enumeration value="SENDER_SIGNS_FIRST"/>
 *     &lt;enumeration value="SENDER_SIGNATURE_NOT_REQUIRED"/>
 *     &lt;enumeration value="SEQUENTIAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SignatureFlow")
@XmlEnum
public enum SignatureFlow {

    SENDER_SIGNS_LAST,
    SENDER_SIGNS_FIRST,
    SENDER_SIGNATURE_NOT_REQUIRED,
    SEQUENTIAL;

    public String value() {
        return name();
    }

    public static SignatureFlow fromValue(String v) {
        return valueOf(v);
    }

}

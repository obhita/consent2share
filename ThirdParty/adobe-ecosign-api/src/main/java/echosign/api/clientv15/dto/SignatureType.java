
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignatureType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SignatureType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ESIGN"/>
 *     &lt;enumeration value="WRITTEN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SignatureType")
@XmlEnum
public enum SignatureType {

    ESIGN,
    WRITTEN;

    public String value() {
        return name();
    }

    public static SignatureType fromValue(String v) {
        return valueOf(v);
    }

}

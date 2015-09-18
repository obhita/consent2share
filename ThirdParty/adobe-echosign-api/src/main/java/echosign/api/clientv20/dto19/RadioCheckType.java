
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RadioCheckType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RadioCheckType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CIRCLE"/>
 *     &lt;enumeration value="CHECK"/>
 *     &lt;enumeration value="CROSS"/>
 *     &lt;enumeration value="DIAMOND"/>
 *     &lt;enumeration value="SQUARE"/>
 *     &lt;enumeration value="STAR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RadioCheckType")
@XmlEnum
public enum RadioCheckType {

    CIRCLE,
    CHECK,
    CROSS,
    DIAMOND,
    SQUARE,
    STAR;

    public String value() {
        return name();
    }

    public static RadioCheckType fromValue(String v) {
        return valueOf(v);
    }

}

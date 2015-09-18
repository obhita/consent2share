
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BorderStyle.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BorderStyle">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SOLID"/>
 *     &lt;enumeration value="DASHED"/>
 *     &lt;enumeration value="BEVELED"/>
 *     &lt;enumeration value="INSET"/>
 *     &lt;enumeration value="UNDERLINE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BorderStyle")
@XmlEnum
public enum BorderStyle {

    SOLID,
    DASHED,
    BEVELED,
    INSET,
    UNDERLINE;

    public String value() {
        return name();
    }

    public static BorderStyle fromValue(String v) {
        return valueOf(v);
    }

}

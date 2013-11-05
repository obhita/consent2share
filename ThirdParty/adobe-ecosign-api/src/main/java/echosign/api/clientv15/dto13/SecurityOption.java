
package echosign.api.clientv15.dto13;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SecurityOption.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SecurityOption">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OPEN_PROTECTED"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SecurityOption")
@XmlEnum
public enum SecurityOption {

    OPEN_PROTECTED,
    OTHER;

    public String value() {
        return name();
    }

    public static SecurityOption fromValue(String v) {
        return valueOf(v);
    }

}

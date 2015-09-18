
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DisplayFormatType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DisplayFormatType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DEFAULT"/>
 *     &lt;enumeration value="DATE"/>
 *     &lt;enumeration value="NUMBER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DisplayFormatType")
@XmlEnum
public enum DisplayFormatType {

    DEFAULT,
    DATE,
    NUMBER;

    public String value() {
        return name();
    }

    public static DisplayFormatType fromValue(String v) {
        return valueOf(v);
    }

}


package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FormType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FormType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NORMAL"/>
 *     &lt;enumeration value="WIDGET"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FormType")
@XmlEnum
public enum FormType {

    NORMAL,
    WIDGET;

    public String value() {
        return name();
    }

    public static FormType fromValue(String v) {
        return valueOf(v);
    }

}

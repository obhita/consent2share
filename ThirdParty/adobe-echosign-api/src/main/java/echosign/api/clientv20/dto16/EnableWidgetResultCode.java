
package echosign.api.clientv20.dto16;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnableWidgetResultCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnableWidgetResultCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="ALREADY_ENABLED"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EnableWidgetResultCode")
@XmlEnum
public enum EnableWidgetResultCode {

    OK,
    INVALID_API_KEY,
    INVALID_DOCUMENT_KEY,
    ALREADY_ENABLED,
    EXCEPTION;

    public String value() {
        return name();
    }

    public static EnableWidgetResultCode fromValue(String v) {
        return valueOf(v);
    }

}

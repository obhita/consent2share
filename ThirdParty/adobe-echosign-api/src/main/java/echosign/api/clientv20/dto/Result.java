
package echosign.api.clientv20.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Result.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Result">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="REMINDER_SENT"/>
 *     &lt;enumeration value="CANCELLED"/>
 *     &lt;enumeration value="ALREADY_SIGNED"/>
 *     &lt;enumeration value="ALREADY_CANCELLED"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Result")
@XmlEnum
public enum Result {

    REMINDER_SENT,
    CANCELLED,
    ALREADY_SIGNED,
    ALREADY_CANCELLED,
    INVALID_DOCUMENT_KEY,
    INVALID_API_KEY;

    public String value() {
        return name();
    }

    public static Result fromValue(String v) {
        return valueOf(v);
    }

}

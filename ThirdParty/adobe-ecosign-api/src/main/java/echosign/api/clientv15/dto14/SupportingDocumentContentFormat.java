
package echosign.api.clientv15.dto14;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupportingDocumentContentFormat.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SupportingDocumentContentFormat">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="ORIGINAL"/>
 *     &lt;enumeration value="CONVERTED_PDF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SupportingDocumentContentFormat")
@XmlEnum
public enum SupportingDocumentContentFormat {

    NONE,
    ORIGINAL,
    CONVERTED_PDF;

    public String value() {
        return name();
    }

    public static SupportingDocumentContentFormat fromValue(String v) {
        return valueOf(v);
    }

}


package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LibraryTemplateType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LibraryTemplateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DOCUMENT"/>
 *     &lt;enumeration value="FORM_FIELD_LAYER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LibraryTemplateType")
@XmlEnum
public enum LibraryTemplateType {

    DOCUMENT,
    FORM_FIELD_LAYER;

    public String value() {
        return name();
    }

    public static LibraryTemplateType fromValue(String v) {
        return valueOf(v);
    }

}

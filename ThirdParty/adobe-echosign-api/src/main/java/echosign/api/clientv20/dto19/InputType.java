
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InputType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InputType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TEXT_FIELD"/>
 *     &lt;enumeration value="MULTILINE"/>
 *     &lt;enumeration value="PASSWORD"/>
 *     &lt;enumeration value="RADIO"/>
 *     &lt;enumeration value="CHECKBOX"/>
 *     &lt;enumeration value="DROP_DOWN"/>
 *     &lt;enumeration value="LISTBOX"/>
 *     &lt;enumeration value="SIGNATURE"/>
 *     &lt;enumeration value="PDF_SIGNATURE"/>
 *     &lt;enumeration value="BUTTON"/>
 *     &lt;enumeration value="BLOCK"/>
 *     &lt;enumeration value="FILE_CHOOSER"/>
 *     &lt;enumeration value="COMB"/>
 *     &lt;enumeration value="UNSUPPORTED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "InputType")
@XmlEnum
public enum InputType {

    TEXT_FIELD,
    MULTILINE,
    PASSWORD,
    RADIO,
    CHECKBOX,
    DROP_DOWN,
    LISTBOX,
    SIGNATURE,
    PDF_SIGNATURE,
    BUTTON,
    BLOCK,
    FILE_CHOOSER,
    COMB,
    UNSUPPORTED;

    public String value() {
        return name();
    }

    public static InputType fromValue(String v) {
        return valueOf(v);
    }

}

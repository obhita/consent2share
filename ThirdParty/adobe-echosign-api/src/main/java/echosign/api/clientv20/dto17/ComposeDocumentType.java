
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComposeDocumentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ComposeDocumentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SIGN_THEN_DELIVER"/>
 *     &lt;enumeration value="DELIVER_ONLY"/>
 *     &lt;enumeration value="SIGN_THEN_SEND"/>
 *     &lt;enumeration value="SEND_ONLY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ComposeDocumentType")
@XmlEnum
public enum ComposeDocumentType {

    SIGN_THEN_DELIVER,
    DELIVER_ONLY,
    SIGN_THEN_SEND,
    SEND_ONLY;

    public String value() {
        return name();
    }

    public static ComposeDocumentType fromValue(String v) {
        return valueOf(v);
    }

}

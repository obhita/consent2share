
package echosign.api.clientv20.dto16;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReusableDocumentStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReusableDocumentStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ENABLED"/>
 *     &lt;enumeration value="DISABLED"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReusableDocumentStatus")
@XmlEnum
public enum ReusableDocumentStatus {

    ENABLED,
    DISABLED,
    OTHER;

    public String value() {
        return name();
    }

    public static ReusableDocumentStatus fromValue(String v) {
        return valueOf(v);
    }

}

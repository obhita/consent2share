
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmbeddedViewTarget.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EmbeddedViewTarget">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AGREEMENT"/>
 *     &lt;enumeration value="AGREEMENT_LIST"/>
 *     &lt;enumeration value="USER_PROFILE"/>
 *     &lt;enumeration value="ACCOUNT_SETTINGS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EmbeddedViewTarget")
@XmlEnum
public enum EmbeddedViewTarget {

    AGREEMENT,
    AGREEMENT_LIST,
    USER_PROFILE,
    ACCOUNT_SETTINGS;

    public String value() {
        return name();
    }

    public static EmbeddedViewTarget fromValue(String v) {
        return valueOf(v);
    }

}

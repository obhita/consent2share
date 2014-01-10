
package echosign.api.clientv15.dto9;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LibrarySharingMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LibrarySharingMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="USER"/>
 *     &lt;enumeration value="GROUP"/>
 *     &lt;enumeration value="ACCOUNT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LibrarySharingMode")
@XmlEnum
public enum LibrarySharingMode {

    USER,
    GROUP,
    ACCOUNT;

    public String value() {
        return name();
    }

    public static LibrarySharingMode fromValue(String v) {
        return valueOf(v);
    }

}

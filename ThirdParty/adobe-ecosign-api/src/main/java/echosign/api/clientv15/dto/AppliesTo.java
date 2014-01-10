
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppliesTo.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AppliesTo">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="EXTERNAL_USERS"/>
 *     &lt;enumeration value="INTERNAL_USERS"/>
 *     &lt;enumeration value="ALL_USERS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AppliesTo")
@XmlEnum
public enum AppliesTo {

    NONE,
    EXTERNAL_USERS,
    INTERNAL_USERS,
    ALL_USERS;

    public String value() {
        return name();
    }

    public static AppliesTo fromValue(String v) {
        return valueOf(v);
    }

}

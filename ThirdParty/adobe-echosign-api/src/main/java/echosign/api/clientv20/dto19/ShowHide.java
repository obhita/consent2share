
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShowHide.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ShowHide">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SHOW"/>
 *     &lt;enumeration value="HIDE"/>
 *     &lt;enumeration value="DISABLE"/>
 *     &lt;enumeration value="ENABLE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ShowHide")
@XmlEnum
public enum ShowHide {

    SHOW,
    HIDE,
    DISABLE,
    ENABLE;

    public String value() {
        return name();
    }

    public static ShowHide fromValue(String v) {
        return valueOf(v);
    }

}

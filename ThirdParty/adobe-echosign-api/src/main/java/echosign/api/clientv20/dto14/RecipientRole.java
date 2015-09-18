
package echosign.api.clientv20.dto14;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecipientRole.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RecipientRole">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SIGNER"/>
 *     &lt;enumeration value="APPROVER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RecipientRole")
@XmlEnum
public enum RecipientRole {

    SIGNER,
    APPROVER;

    public String value() {
        return name();
    }

    public static RecipientRole fromValue(String v) {
        return valueOf(v);
    }

}

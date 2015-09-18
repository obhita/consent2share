
package echosign.api.clientv20.dto18;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserCapabilityFlag.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UserCapabilityFlag">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CAN_SEND"/>
 *     &lt;enumeration value="CAN_SIGN"/>
 *     &lt;enumeration value="CAN_REPLACE_SIGNER"/>
 *     &lt;enumeration value="VAULT_ENABLED"/>
 *     &lt;enumeration value="VAULT_PER_AGREEMENT"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UserCapabilityFlag")
@XmlEnum
public enum UserCapabilityFlag {

    CAN_SEND,
    CAN_SIGN,
    CAN_REPLACE_SIGNER,
    VAULT_ENABLED,
    VAULT_PER_AGREEMENT,
    OTHER;

    public String value() {
        return name();
    }

    public static UserCapabilityFlag fromValue(String v) {
        return valueOf(v);
    }

}

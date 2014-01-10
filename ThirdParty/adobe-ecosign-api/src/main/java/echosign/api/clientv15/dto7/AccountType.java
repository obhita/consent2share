
package echosign.api.clientv15.dto7;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AccountType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FREE"/>
 *     &lt;enumeration value="PRO"/>
 *     &lt;enumeration value="TEAM"/>
 *     &lt;enumeration value="TEAM_TRIAL"/>
 *     &lt;enumeration value="ENTERPRISE"/>
 *     &lt;enumeration value="ENTERPRISE_TRIAL"/>
 *     &lt;enumeration value="GLOBAL"/>
 *     &lt;enumeration value="GLOBAL_TRIAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccountType")
@XmlEnum
public enum AccountType {

    FREE,
    PRO,
    TEAM,
    TEAM_TRIAL,
    ENTERPRISE,
    ENTERPRISE_TRIAL,
    GLOBAL,
    GLOBAL_TRIAL;

    public String value() {
        return name();
    }

    public static AccountType fromValue(String v) {
        return valueOf(v);
    }

}

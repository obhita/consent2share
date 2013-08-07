
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReminderFrequency.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReminderFrequency">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DAILY_UNTIL_SIGNED"/>
 *     &lt;enumeration value="WEEKLY_UNTIL_SIGNED"/>
 *     &lt;enumeration value="NEVER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReminderFrequency")
@XmlEnum
public enum ReminderFrequency {

    DAILY_UNTIL_SIGNED,
    WEEKLY_UNTIL_SIGNED,
    NEVER;

    public String value() {
        return name();
    }

    public static ReminderFrequency fromValue(String v) {
        return valueOf(v);
    }

}


package echosign.api.clientv20.dto16;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DisableWidgetResultCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DisableWidgetResultCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="ALREADY_DISABLED"/>
 *     &lt;enumeration value="TOO_MANY_ACTIONS_SPECIFIED"/>
 *     &lt;enumeration value="NO_ACTION_SPECIFIED"/>
 *     &lt;enumeration value="INVALID_URL"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DisableWidgetResultCode")
@XmlEnum
public enum DisableWidgetResultCode {

    OK,
    INVALID_API_KEY,
    INVALID_DOCUMENT_KEY,
    ALREADY_DISABLED,
    TOO_MANY_ACTIONS_SPECIFIED,
    NO_ACTION_SPECIFIED,
    INVALID_URL,
    EXCEPTION;

    public String value() {
        return name();
    }

    public static DisableWidgetResultCode fromValue(String v) {
        return valueOf(v);
    }

}

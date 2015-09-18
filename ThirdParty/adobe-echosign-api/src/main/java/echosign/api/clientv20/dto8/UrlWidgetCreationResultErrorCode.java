
package echosign.api.clientv20.dto8;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UrlWidgetCreationResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UrlWidgetCreationResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_URL"/>
 *     &lt;enumeration value="INVALID_SIGNATURE_FLOW"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UrlWidgetCreationResultErrorCode")
@XmlEnum
public enum UrlWidgetCreationResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_URL,
    INVALID_SIGNATURE_FLOW,
    EXCEPTION,
    MISC_ERROR;

    public String value() {
        return name();
    }

    public static UrlWidgetCreationResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

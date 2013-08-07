
package echosign.api.clientv15.dto12;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SendDocumentInteractiveResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SendDocumentInteractiveResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_ARGUMENTS"/>
 *     &lt;enumeration value="AUTOLOGIN_DENIED"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SendDocumentInteractiveResultErrorCode")
@XmlEnum
public enum SendDocumentInteractiveResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_ARGUMENTS,
    AUTOLOGIN_DENIED,
    MISC_ERROR,
    EXCEPTION;

    public String value() {
        return name();
    }

    public static SendDocumentInteractiveResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

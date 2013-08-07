
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetMegaSignDocumentResultCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetMegaSignDocumentResultCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="PERMISSION_DENIED"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="NOT_MEGASIGN"/>
 *     &lt;enumeration value="EXPIRED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetMegaSignDocumentResultCode")
@XmlEnum
public enum GetMegaSignDocumentResultCode {

    OK,
    INVALID_API_KEY,
    EXCEPTION,
    MISC_ERROR,
    PERMISSION_DENIED,
    INVALID_DOCUMENT_KEY,
    NOT_MEGASIGN,
    EXPIRED;

    public String value() {
        return name();
    }

    public static GetMegaSignDocumentResultCode fromValue(String v) {
        return valueOf(v);
    }

}

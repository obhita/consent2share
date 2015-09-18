
package echosign.api.clientv20.dto14;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SigningUrlResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SigningUrlResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="DOCUMENT_HAS_BEEN_DELETED"/>
 *     &lt;enumeration value="DOCUMENT_NOT_SIGNABLE"/>
 *     &lt;enumeration value="DOCUMENT_NOT_VISIBLE"/>
 *     &lt;enumeration value="DOCUMENT_NOT_EXPOSED"/>
 *     &lt;enumeration value="DOCUMENT_ALREADY_SIGNED"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SigningUrlResultErrorCode")
@XmlEnum
public enum SigningUrlResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_DOCUMENT_KEY,
    DOCUMENT_HAS_BEEN_DELETED,
    DOCUMENT_NOT_SIGNABLE,
    DOCUMENT_NOT_VISIBLE,
    DOCUMENT_NOT_EXPOSED,
    DOCUMENT_ALREADY_SIGNED,
    EXCEPTION,
    MISC_ERROR;

    public String value() {
        return name();
    }

    public static SigningUrlResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

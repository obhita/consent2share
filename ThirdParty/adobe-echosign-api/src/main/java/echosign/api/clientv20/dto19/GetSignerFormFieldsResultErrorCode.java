
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetSignerFormFieldsResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetSignerFormFieldsResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="DOCUMENT_HAS_BEEN_DELETED"/>
 *     &lt;enumeration value="DOCUMENT_ALREADY_SIGNED"/>
 *     &lt;enumeration value="DOCUMENT_NOT_SIGNABLE"/>
 *     &lt;enumeration value="INVALID_SIGNER_EMAIL"/>
 *     &lt;enumeration value="ALREADY_CANCELLED"/>
 *     &lt;enumeration value="ALREADY_SIGNED"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetSignerFormFieldsResultErrorCode")
@XmlEnum
public enum GetSignerFormFieldsResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_DOCUMENT_KEY,
    DOCUMENT_HAS_BEEN_DELETED,
    DOCUMENT_ALREADY_SIGNED,
    DOCUMENT_NOT_SIGNABLE,
    INVALID_SIGNER_EMAIL,
    ALREADY_CANCELLED,
    ALREADY_SIGNED,
    EXCEPTION,
    MISC_ERROR;

    public String value() {
        return name();
    }

    public static GetSignerFormFieldsResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

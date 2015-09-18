
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReplaceSignerResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReplaceSignerResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_CREDENTIALS"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="INVALID_OPTIONS"/>
 *     &lt;enumeration value="INVALID_ORIGINAL_SIGNER"/>
 *     &lt;enumeration value="INVALID_NEW_SIGNER"/>
 *     &lt;enumeration value="INVALID_MESSAGE"/>
 *     &lt;enumeration value="SIGNER_REPLACEMENT_NOT_ALLOWED"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReplaceSignerResultErrorCode")
@XmlEnum
public enum ReplaceSignerResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_CREDENTIALS,
    INVALID_DOCUMENT_KEY,
    INVALID_OPTIONS,
    INVALID_ORIGINAL_SIGNER,
    INVALID_NEW_SIGNER,
    INVALID_MESSAGE,
    SIGNER_REPLACEMENT_NOT_ALLOWED,
    MISC_ERROR,
    EXCEPTION;

    public String value() {
        return name();
    }

    public static ReplaceSignerResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

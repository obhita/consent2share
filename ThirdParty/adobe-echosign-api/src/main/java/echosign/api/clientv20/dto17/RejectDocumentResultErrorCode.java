
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RejectDocumentResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RejectDocumentResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_CREDENTIALS"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="INVALID_COMMENT"/>
 *     &lt;enumeration value="DOCUMENT_REJECTION_NOT_ALLOWED"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RejectDocumentResultErrorCode")
@XmlEnum
public enum RejectDocumentResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_CREDENTIALS,
    INVALID_DOCUMENT_KEY,
    INVALID_COMMENT,
    DOCUMENT_REJECTION_NOT_ALLOWED,
    MISC_ERROR,
    EXCEPTION;

    public String value() {
        return name();
    }

    public static RejectDocumentResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

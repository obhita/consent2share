
package echosign.api.clientv15.dto14;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetSupportingDocumentsResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetSupportingDocumentsResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="DOCUMENT_HAS_BEEN_DELETED"/>
 *     &lt;enumeration value="DOCUMENT_NOT_AVAILABLE"/>
 *     &lt;enumeration value="DOCUMENT_ORIGINAL_FORMAT_NOT_ALLOWED"/>
 *     &lt;enumeration value="TERMS_OF_USE_NOT_ACCEPTED"/>
 *     &lt;enumeration value="NO_PERMISSION_TO_EXECUTE_METHOD"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetSupportingDocumentsResultErrorCode")
@XmlEnum
public enum GetSupportingDocumentsResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_DOCUMENT_KEY,
    DOCUMENT_HAS_BEEN_DELETED,
    DOCUMENT_NOT_AVAILABLE,
    DOCUMENT_ORIGINAL_FORMAT_NOT_ALLOWED,
    TERMS_OF_USE_NOT_ACCEPTED,
    NO_PERMISSION_TO_EXECUTE_METHOD,
    EXCEPTION,
    MISC_ERROR;

    public String value() {
        return name();
    }

    public static GetSupportingDocumentsResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

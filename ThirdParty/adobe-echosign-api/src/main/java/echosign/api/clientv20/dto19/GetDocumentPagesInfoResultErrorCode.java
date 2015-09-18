
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetDocumentPagesInfoResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetDocumentPagesInfoResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="DOCUMENT_HAS_BEEN_DELETED"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetDocumentPagesInfoResultErrorCode")
@XmlEnum
public enum GetDocumentPagesInfoResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_DOCUMENT_KEY,
    DOCUMENT_HAS_BEEN_DELETED,
    EXCEPTION,
    MISC_ERROR;

    public String value() {
        return name();
    }

    public static GetDocumentPagesInfoResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

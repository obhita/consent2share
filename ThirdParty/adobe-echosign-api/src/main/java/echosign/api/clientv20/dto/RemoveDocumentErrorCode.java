
package echosign.api.clientv20.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RemoveDocumentErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RemoveDocumentErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="DOCUMENT_HAS_BEEN_REMOVED"/>
 *     &lt;enumeration value="DYNAMIC_DOCUMENT_EXPIRATION_NOT_ENABLED"/>
 *     &lt;enumeration value="REMOVE_FAILED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RemoveDocumentErrorCode")
@XmlEnum
public enum RemoveDocumentErrorCode {

    INVALID_API_KEY,
    INVALID_DOCUMENT_KEY,
    DOCUMENT_HAS_BEEN_REMOVED,
    DYNAMIC_DOCUMENT_EXPIRATION_NOT_ENABLED,
    REMOVE_FAILED;

    public String value() {
        return name();
    }

    public static RemoveDocumentErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

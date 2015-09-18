
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NotifyDocumentVaultedResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NotifyDocumentVaultedResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_ACCESS_TOKEN"/>
 *     &lt;enumeration value="NO_PERMISSION_TO_EXECUTE_METHOD"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="INVALID_VAULT_ID"/>
 *     &lt;enumeration value="DOCUMENT_REMOVED"/>
 *     &lt;enumeration value="DOCUMENT_DELETED"/>
 *     &lt;enumeration value="DOCUMENT_NOT_READY_TO_VAULT"/>
 *     &lt;enumeration value="DOCMENT_VAULTED_ALREADY"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NotifyDocumentVaultedResultErrorCode")
@XmlEnum
public enum NotifyDocumentVaultedResultErrorCode {

    OK,
    INVALID_ACCESS_TOKEN,
    NO_PERMISSION_TO_EXECUTE_METHOD,
    INVALID_DOCUMENT_KEY,
    INVALID_VAULT_ID,
    DOCUMENT_REMOVED,
    DOCUMENT_DELETED,
    DOCUMENT_NOT_READY_TO_VAULT,
    DOCMENT_VAULTED_ALREADY,
    MISC_ERROR,
    EXCEPTION;

    public String value() {
        return name();
    }

    public static NotifyDocumentVaultedResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

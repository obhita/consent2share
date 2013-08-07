
package echosign.api.clientv15.dto9;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuditTrailResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AuditTrailResultErrorCode">
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
@XmlType(name = "AuditTrailResultErrorCode")
@XmlEnum
public enum AuditTrailResultErrorCode {

    OK,
    INVALID_API_KEY,
    INVALID_DOCUMENT_KEY,
    DOCUMENT_HAS_BEEN_DELETED,
    EXCEPTION,
    MISC_ERROR;

    public String value() {
        return name();
    }

    public static AuditTrailResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

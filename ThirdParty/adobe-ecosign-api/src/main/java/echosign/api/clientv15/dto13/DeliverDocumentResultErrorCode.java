
package echosign.api.clientv15.dto13;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeliverDocumentResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DeliverDocumentResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="FILE_RETRIEVAL_ERROR"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DeliverDocumentResultErrorCode")
@XmlEnum
public enum DeliverDocumentResultErrorCode {

    OK,
    INVALID_API_KEY,
    FILE_RETRIEVAL_ERROR,
    MISC_ERROR,
    EXCEPTION;

    public String value() {
        return name();
    }

    public static DeliverDocumentResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

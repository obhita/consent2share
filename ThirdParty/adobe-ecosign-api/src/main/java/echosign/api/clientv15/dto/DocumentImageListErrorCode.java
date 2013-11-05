
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentImageListErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DocumentImageListErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="INVALID_VERSION_KEY"/>
 *     &lt;enumeration value="DOCUMENT_HAS_BEEN_DELETED"/>
 *     &lt;enumeration value="IMAGES_NOT_AVAILABLE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DocumentImageListErrorCode")
@XmlEnum
public enum DocumentImageListErrorCode {

    INVALID_API_KEY,
    INVALID_DOCUMENT_KEY,
    INVALID_VERSION_KEY,
    DOCUMENT_HAS_BEEN_DELETED,
    IMAGES_NOT_AVAILABLE;

    public String value() {
        return name();
    }

    public static DocumentImageListErrorCode fromValue(String v) {
        return valueOf(v);
    }

}


package echosign.api.clientv20.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentUrlErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DocumentUrlErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="INVALID_DOCUMENT_KEY"/>
 *     &lt;enumeration value="INVALID_VERSION_KEY"/>
 *     &lt;enumeration value="DOCUMENT_HAS_BEEN_DELETED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DocumentUrlErrorCode")
@XmlEnum
public enum DocumentUrlErrorCode {

    INVALID_API_KEY,
    INVALID_DOCUMENT_KEY,
    INVALID_VERSION_KEY,
    DOCUMENT_HAS_BEEN_DELETED;

    public String value() {
        return name();
    }

    public static DocumentUrlErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

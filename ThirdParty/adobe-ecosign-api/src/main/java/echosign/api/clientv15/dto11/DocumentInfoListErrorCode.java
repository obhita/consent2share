
package echosign.api.clientv15.dto11;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentInfoListErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DocumentInfoListErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="NO_ACCESS_WITHOUT_CREDENTIAL"/>
 *     &lt;enumeration value="INVALID_CREDENTIAL"/>
 *     &lt;enumeration value="INVALID_EXTERNAL_ID"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DocumentInfoListErrorCode")
@XmlEnum
public enum DocumentInfoListErrorCode {

    INVALID_API_KEY,
    NO_ACCESS_WITHOUT_CREDENTIAL,
    INVALID_CREDENTIAL,
    INVALID_EXTERNAL_ID;

    public String value() {
        return name();
    }

    public static DocumentInfoListErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

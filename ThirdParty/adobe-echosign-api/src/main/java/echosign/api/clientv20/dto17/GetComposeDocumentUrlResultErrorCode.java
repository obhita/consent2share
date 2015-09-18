
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetComposeDocumentUrlResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GetComposeDocumentUrlResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_ACCESS_TOKEN"/>
 *     &lt;enumeration value="INVALID_ARGUMENTS"/>
 *     &lt;enumeration value="FILE_RETRIEVAL_ERROR"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GetComposeDocumentUrlResultErrorCode")
@XmlEnum
public enum GetComposeDocumentUrlResultErrorCode {

    OK,
    INVALID_ACCESS_TOKEN,
    INVALID_ARGUMENTS,
    FILE_RETRIEVAL_ERROR,
    MISC_ERROR,
    EXCEPTION;

    public String value() {
        return name();
    }

    public static GetComposeDocumentUrlResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

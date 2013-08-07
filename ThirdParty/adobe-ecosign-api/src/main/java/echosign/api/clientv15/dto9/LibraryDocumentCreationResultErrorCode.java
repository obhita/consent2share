
package echosign.api.clientv15.dto9;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LibraryDocumentCreationResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LibraryDocumentCreationResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INVALID_API_KEY"/>
 *     &lt;enumeration value="EXCEPTION"/>
 *     &lt;enumeration value="MISC_ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LibraryDocumentCreationResultErrorCode")
@XmlEnum
public enum LibraryDocumentCreationResultErrorCode {

    OK,
    INVALID_API_KEY,
    EXCEPTION,
    MISC_ERROR;

    public String value() {
        return name();
    }

    public static LibraryDocumentCreationResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}

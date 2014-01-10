
package echosign.api.clientv15.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SendDocumentMegaSignResultErrorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SendDocumentMegaSignResultErrorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SendDocumentMegaSignResultErrorCode")
@XmlEnum
public enum SendDocumentMegaSignResultErrorCode {

    ERROR;

    public String value() {
        return name();
    }

    public static SendDocumentMegaSignResultErrorCode fromValue(String v) {
        return valueOf(v);
    }

}


package echosign.api.clientv15.dto14;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocumentListItemUserDocumentStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DocumentListItemUserDocumentStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="WAITING_FOR_MY_SIGNATURE"/>
 *     &lt;enumeration value="WAITING_FOR_MY_APPROVAL"/>
 *     &lt;enumeration value="OUT_FOR_SIGNATURE"/>
 *     &lt;enumeration value="OUT_FOR_APPROVAL"/>
 *     &lt;enumeration value="SIGNED"/>
 *     &lt;enumeration value="APPROVED"/>
 *     &lt;enumeration value="RECALLED"/>
 *     &lt;enumeration value="WAITING_FOR_FAXIN"/>
 *     &lt;enumeration value="ARCHIVED"/>
 *     &lt;enumeration value="FORM"/>
 *     &lt;enumeration value="EXPIRED"/>
 *     &lt;enumeration value="WIDGET"/>
 *     &lt;enumeration value="WAITING_FOR_AUTHORING"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DocumentListItemUserDocumentStatus")
@XmlEnum
public enum DocumentListItemUserDocumentStatus {

    WAITING_FOR_MY_SIGNATURE,
    WAITING_FOR_MY_APPROVAL,
    OUT_FOR_SIGNATURE,
    OUT_FOR_APPROVAL,
    SIGNED,
    APPROVED,
    RECALLED,
    WAITING_FOR_FAXIN,
    ARCHIVED,
    FORM,
    EXPIRED,
    WIDGET,
    WAITING_FOR_AUTHORING,
    OTHER;

    public String value() {
        return name();
    }

    public static DocumentListItemUserDocumentStatus fromValue(String v) {
        return valueOf(v);
    }

}

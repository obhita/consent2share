
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AgreementStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AgreementStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OUT_FOR_SIGNATURE"/>
 *     &lt;enumeration value="WAITING_FOR_REVIEW"/>
 *     &lt;enumeration value="SIGNED"/>
 *     &lt;enumeration value="APPROVED"/>
 *     &lt;enumeration value="ABORTED"/>
 *     &lt;enumeration value="DOCUMENT_LIBRARY"/>
 *     &lt;enumeration value="WIDGET"/>
 *     &lt;enumeration value="EXPIRED"/>
 *     &lt;enumeration value="ARCHIVED"/>
 *     &lt;enumeration value="PREFILL"/>
 *     &lt;enumeration value="AUTHORING"/>
 *     &lt;enumeration value="WAITING_FOR_FAXIN"/>
 *     &lt;enumeration value="WAITING_FOR_VERIFICATION"/>
 *     &lt;enumeration value="WIDGET_WAITING_FOR_VERIFICATION"/>
 *     &lt;enumeration value="WAITING_FOR_PAYMENT"/>
 *     &lt;enumeration value="OUT_FOR_APPROVAL"/>
 *     &lt;enumeration value="OTHER"/>
 *     &lt;enumeration value="SIGNED_IN_ADOBE_ACROBAT"/>
 *     &lt;enumeration value="SIGNED_IN_ADOBE_READER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AgreementStatus")
@XmlEnum
public enum AgreementStatus {

    OUT_FOR_SIGNATURE,
    WAITING_FOR_REVIEW,
    SIGNED,
    APPROVED,
    ABORTED,
    DOCUMENT_LIBRARY,
    WIDGET,
    EXPIRED,
    ARCHIVED,
    PREFILL,
    AUTHORING,
    WAITING_FOR_FAXIN,
    WAITING_FOR_VERIFICATION,
    WIDGET_WAITING_FOR_VERIFICATION,
    WAITING_FOR_PAYMENT,
    OUT_FOR_APPROVAL,
    OTHER,
    SIGNED_IN_ADOBE_ACROBAT,
    SIGNED_IN_ADOBE_READER;

    public String value() {
        return name();
    }

    public static AgreementStatus fromValue(String v) {
        return valueOf(v);
    }

}

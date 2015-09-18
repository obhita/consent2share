
package echosign.api.clientv20.dto17;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserAgreementStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UserAgreementStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="WAITING_FOR_MY_SIGNATURE"/>
 *     &lt;enumeration value="WAITING_FOR_MY_APPROVAL"/>
 *     &lt;enumeration value="OUT_FOR_SIGNATURE"/>
 *     &lt;enumeration value="SIGNED"/>
 *     &lt;enumeration value="APPROVED"/>
 *     &lt;enumeration value="RECALLED"/>
 *     &lt;enumeration value="HIDDEN"/>
 *     &lt;enumeration value="NOT_YET_VISIBLE"/>
 *     &lt;enumeration value="WAITING_FOR_FAXIN"/>
 *     &lt;enumeration value="ARCHIVED"/>
 *     &lt;enumeration value="UNKNOWN"/>
 *     &lt;enumeration value="PARTIAL"/>
 *     &lt;enumeration value="FORM"/>
 *     &lt;enumeration value="WAITING_FOR_AUTHORING"/>
 *     &lt;enumeration value="OUT_FOR_APPROVAL"/>
 *     &lt;enumeration value="WIDGET"/>
 *     &lt;enumeration value="EXPIRED"/>
 *     &lt;enumeration value="WAITING_FOR_MY_REVIEW"/>
 *     &lt;enumeration value="IN_REVIEW"/>
 *     &lt;enumeration value="OTHER"/>
 *     &lt;enumeration value="SIGNED_IN_ADOBE_ACROBAT"/>
 *     &lt;enumeration value="SIGNED_IN_ADOBE_READER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UserAgreementStatus")
@XmlEnum
public enum UserAgreementStatus {

    WAITING_FOR_MY_SIGNATURE,
    WAITING_FOR_MY_APPROVAL,
    OUT_FOR_SIGNATURE,
    SIGNED,
    APPROVED,
    RECALLED,
    HIDDEN,
    NOT_YET_VISIBLE,
    WAITING_FOR_FAXIN,
    ARCHIVED,
    UNKNOWN,
    PARTIAL,
    FORM,
    WAITING_FOR_AUTHORING,
    OUT_FOR_APPROVAL,
    WIDGET,
    EXPIRED,
    WAITING_FOR_MY_REVIEW,
    IN_REVIEW,
    OTHER,
    SIGNED_IN_ADOBE_ACROBAT,
    SIGNED_IN_ADOBE_READER;

    public String value() {
        return name();
    }

    public static UserAgreementStatus fromValue(String v) {
        return valueOf(v);
    }

}

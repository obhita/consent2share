
package echosign.api.clientv15.dto11;

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
 *     &lt;enumeration value="OUT_FOR_SIGNATURE"/>
 *     &lt;enumeration value="SIGNED"/>
 *     &lt;enumeration value="RECALLED"/>
 *     &lt;enumeration value="HIDDEN"/>
 *     &lt;enumeration value="NOT_YET_VISIBLE"/>
 *     &lt;enumeration value="WAITING_FOR_FAXIN"/>
 *     &lt;enumeration value="ARCHIVED"/>
 *     &lt;enumeration value="UNKNOWN"/>
 *     &lt;enumeration value="PARTIAL"/>
 *     &lt;enumeration value="FORM"/>
 *     &lt;enumeration value="WAITING_FOR_AUTHORING"/>
 *     &lt;enumeration value="WIDGET"/>
 *     &lt;enumeration value="EXPIRED"/>
 *     &lt;enumeration value="WAITING_FOR_MY_REVIEW"/>
 *     &lt;enumeration value="IN_REVIEW"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UserAgreementStatus")
@XmlEnum
public enum UserAgreementStatus {

    WAITING_FOR_MY_SIGNATURE,
    OUT_FOR_SIGNATURE,
    SIGNED,
    RECALLED,
    HIDDEN,
    NOT_YET_VISIBLE,
    WAITING_FOR_FAXIN,
    ARCHIVED,
    UNKNOWN,
    PARTIAL,
    FORM,
    WAITING_FOR_AUTHORING,
    WIDGET,
    EXPIRED,
    WAITING_FOR_MY_REVIEW,
    IN_REVIEW,
    OTHER;

    public String value() {
        return name();
    }

    public static UserAgreementStatus fromValue(String v) {
        return valueOf(v);
    }

}

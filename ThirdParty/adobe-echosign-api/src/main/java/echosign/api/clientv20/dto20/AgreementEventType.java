
package echosign.api.clientv20.dto20;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AgreementEventType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AgreementEventType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CREATED"/>
 *     &lt;enumeration value="UPLOADED_BY_SENDER"/>
 *     &lt;enumeration value="FAXED_BY_SENDER"/>
 *     &lt;enumeration value="PRESIGNED"/>
 *     &lt;enumeration value="SIGNED"/>
 *     &lt;enumeration value="ESIGNED"/>
 *     &lt;enumeration value="DIGSIGNED"/>
 *     &lt;enumeration value="APPROVED"/>
 *     &lt;enumeration value="OFFLINE_SYNC"/>
 *     &lt;enumeration value="FAXIN_RECEIVED"/>
 *     &lt;enumeration value="SIGNATURE_REQUESTED"/>
 *     &lt;enumeration value="APPROVAL_REQUESTED"/>
 *     &lt;enumeration value="RECALLED"/>
 *     &lt;enumeration value="REJECTED"/>
 *     &lt;enumeration value="EXPIRED"/>
 *     &lt;enumeration value="EXPIRED_AUTOMATICALLY"/>
 *     &lt;enumeration value="SHARED"/>
 *     &lt;enumeration value="EMAIL_VIEWED"/>
 *     &lt;enumeration value="AUTO_CANCELLED_CONVERSION_PROBLEM"/>
 *     &lt;enumeration value="SIGNER_SUGGESTED_CHANGES"/>
 *     &lt;enumeration value="SENDER_CREATED_NEW_REVISION"/>
 *     &lt;enumeration value="PASSWORD_AUTHENTICATION_FAILED"/>
 *     &lt;enumeration value="KBA_AUTHENTICATION_FAILED"/>
 *     &lt;enumeration value="KBA_AUTHENTICATED"/>
 *     &lt;enumeration value="WEB_IDENTITY_AUTHENTICATED"/>
 *     &lt;enumeration value="WEB_IDENTITY_SPECIFIED"/>
 *     &lt;enumeration value="EMAIL_BOUNCED"/>
 *     &lt;enumeration value="WIDGET_ENABLED"/>
 *     &lt;enumeration value="WIDGET_DISABLED"/>
 *     &lt;enumeration value="DELEGATED"/>
 *     &lt;enumeration value="AUTO_DELEGATED"/>
 *     &lt;enumeration value="REPLACED_SIGNER"/>
 *     &lt;enumeration value="VAULTED"/>
 *     &lt;enumeration value="DOCUMENTS_DELETED"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AgreementEventType")
@XmlEnum
public enum AgreementEventType {

    CREATED,
    UPLOADED_BY_SENDER,
    FAXED_BY_SENDER,
    PRESIGNED,
    SIGNED,
    ESIGNED,
    DIGSIGNED,
    APPROVED,
    OFFLINE_SYNC,
    FAXIN_RECEIVED,
    SIGNATURE_REQUESTED,
    APPROVAL_REQUESTED,
    RECALLED,
    REJECTED,
    EXPIRED,
    EXPIRED_AUTOMATICALLY,
    SHARED,
    EMAIL_VIEWED,
    AUTO_CANCELLED_CONVERSION_PROBLEM,
    SIGNER_SUGGESTED_CHANGES,
    SENDER_CREATED_NEW_REVISION,
    PASSWORD_AUTHENTICATION_FAILED,
    KBA_AUTHENTICATION_FAILED,
    KBA_AUTHENTICATED,
    WEB_IDENTITY_AUTHENTICATED,
    WEB_IDENTITY_SPECIFIED,
    EMAIL_BOUNCED,
    WIDGET_ENABLED,
    WIDGET_DISABLED,
    DELEGATED,
    AUTO_DELEGATED,
    REPLACED_SIGNER,
    VAULTED,
    DOCUMENTS_DELETED,
    OTHER;

    public String value() {
        return name();
    }

    public static AgreementEventType fromValue(String v) {
        return valueOf(v);
    }

}

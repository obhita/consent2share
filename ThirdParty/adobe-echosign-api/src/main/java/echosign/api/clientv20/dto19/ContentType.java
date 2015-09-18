
package echosign.api.clientv20.dto19;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DATA"/>
 *     &lt;enumeration value="SIGNATURE_BLOCK"/>
 *     &lt;enumeration value="SIGNATURE"/>
 *     &lt;enumeration value="SIGNER_NAME"/>
 *     &lt;enumeration value="SIGNER_FIRST_NAME"/>
 *     &lt;enumeration value="SIGNER_LAST_NAME"/>
 *     &lt;enumeration value="SIGNER_INITIALS"/>
 *     &lt;enumeration value="SIGNER_EMAIL"/>
 *     &lt;enumeration value="SIGNER_TITLE"/>
 *     &lt;enumeration value="SIGNER_COMPANY"/>
 *     &lt;enumeration value="SIGNATURE_DATE"/>
 *     &lt;enumeration value="AGREEMENT_NAME"/>
 *     &lt;enumeration value="AGREEMENT_MESSAGE"/>
 *     &lt;enumeration value="TRANSACTION_ID"/>
 *     &lt;enumeration value="SIGNATURE_STAMP"/>
 *     &lt;enumeration value="CALC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContentType")
@XmlEnum
public enum ContentType {

    DATA,
    SIGNATURE_BLOCK,
    SIGNATURE,
    SIGNER_NAME,
    SIGNER_FIRST_NAME,
    SIGNER_LAST_NAME,
    SIGNER_INITIALS,
    SIGNER_EMAIL,
    SIGNER_TITLE,
    SIGNER_COMPANY,
    SIGNATURE_DATE,
    AGREEMENT_NAME,
    AGREEMENT_MESSAGE,
    TRANSACTION_ID,
    SIGNATURE_STAMP,
    CALC;

    public String value() {
        return name();
    }

    public static ContentType fromValue(String v) {
        return valueOf(v);
    }

}

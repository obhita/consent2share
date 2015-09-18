
package echosign.api.clientv20.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserVerificationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserVerificationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userVerificationStatus" type="{http://dto.api.echosign}UserVerificationStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserVerificationInfo", propOrder = {
    "userVerificationStatus"
})
public class UserVerificationInfo {

    @XmlElement(nillable = true)
    protected UserVerificationStatus userVerificationStatus;

    /**
     * Gets the value of the userVerificationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link UserVerificationStatus }
     *     
     */
    public UserVerificationStatus getUserVerificationStatus() {
        return userVerificationStatus;
    }

    /**
     * Sets the value of the userVerificationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserVerificationStatus }
     *     
     */
    public void setUserVerificationStatus(UserVerificationStatus value) {
        this.userVerificationStatus = value;
    }

}
